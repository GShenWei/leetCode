package tt

import (
	"fmt"
	"strings"
	"sync"
	"testing"
	"time"
)

type subscriber chan interface{}
type topicFilterFun func(v interface{}) bool

type Publisher struct {
	m           sync.RWMutex
	buffer      int
	timeout     time.Duration
	subscribers map[subscriber]topicFilterFun
}

func (p *Publisher) SubscribeAll() chan interface{} {
	return p.Subscribe(nil)
}

func (p *Publisher) Subscribe(topicFilterF topicFilterFun) chan interface{} {
	ch := make(chan interface{}, p.buffer)
	p.m.Lock()
	defer p.m.Unlock()

	p.subscribers[ch] = topicFilterF
	return ch
}

func (p *Publisher) Publish(v interface{}) {
	p.m.RLock()
	defer p.m.RUnlock()
	var wg sync.WaitGroup
	for sub, filterFun := range p.subscribers {
		f := filterFun
		s := sub
		if f != nil && !f(v) {
			continue
		}
		wg.Add(1)
		go func() {
			defer wg.Done()
			select {
			case s <- v:
			case <-time.After(p.timeout):
			}
		}()
	}
	wg.Wait()
}

func TestCh(t *testing.T) {
	p := &Publisher{
		buffer:      10,
		timeout:     2 * time.Second,
		subscribers: make(map[subscriber]topicFilterFun),
	}
	subForGolang := p.Subscribe(func(v interface{}) bool {
		if s, ok := v.(string); ok {
			return strings.Contains(s, "golang")
		}
		return false
	})
	subForAll := p.SubscribeAll()
	p.Publish("hello, golang!")
	p.Publish("hello,world")
	/*go func() {
		for msg := range subForGolang {
			fmt.Println("golang:", msg)
		}
	}()
	go func() {
		for msg := range subForAll {
			fmt.Println("all:", msg)
		}
	}()*/
	go func() {
		for {
			select {
			case v := <-subForAll:
				fmt.Println("all:", v)
			case v := <-subForGolang:
				fmt.Println("golang:", v)
			}
		}
	}()
	time.Sleep(1 * time.Second)
}
