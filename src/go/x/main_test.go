package main

import (
	"fmt"
	"strings"
	"testing"
	"time"
	"unicode/utf8"
)

func TestNll(t *testing.T) {
	name := formatName("campus-department")
	fmt.Println(name)
}

func formatName(endpoint string) string {
	target := strings.SplitN(endpoint, "://", 2)
	if len(target) < 2 {
		return "ccrs" + ":///" + endpoint
	}
	return endpoint
}

func TestLm(t *testing.T) {
	var ints []int
	ints = append(ints, 1, 2, 3, 4)
	fmt.Println(ints)
}

func TestLmv(t *testing.T) {
	s := "hello,世界"
	utf8.RuneCountInString(s) //返回字符个数
	for i := 0; i < len(s); {
		r, size := utf8.DecodeRuneInString(s[i:]) //函数返回指定位置的字符
		fmt.Printf("%d\t%c\t%d\n", i, r, size)
		i += size
	}
}

func TestSql(t *testing.T) {
	tbName := "haha"
	from, _ := time.ParseInLocation("2006-01-02", "2018-11-12", time.Local)
	to, _ := time.ParseInLocation("2006-01-02", "2018-11-15", time.Local)
	for t := from; t.Before(to) || t.Equal(to); t = t.AddDate(0, 0, 1) {
		fmt.Println("DROP TABLE IF EXISTS " + tbName + t.Format("2006_01_02") + ";")
	}
}

type Income func(a string)

func TestType(t *testing.T) {
	func(s hey) {
		if h, ok := s.(*haha); ok {
			s.Say(h.Name)
			h.Gsay(func(s string) {
				fmt.Print("\n" + s + "kkkkkkkkkkkkkk")
			})
		}
	}(&haha{Name: "小王"})
}

type hey interface {
	Say(a string) string
	Fly(a string) string
	Gsay(Income)
}

type haha struct {
	Name string
}

func (h *haha) Fly(a string) string {
	fmt.Print(a + ":Fly")
	return a + "f"
}

func (h *haha) Say(a string) string {
	fmt.Print(a)
	return a
}
func (h *haha) Gsay(f Income) {
	f(h.Name)
}

func TestTypefdfd(t *testing.T) {
	fmt.Println(`你好吗
降低佛山接地佛圣诞节`)
}

// 生产者: 生成 factor 整数倍的序列
func Producer(factor int, out chan<- int) {
	for i := 0; ; i++ {
		out <- factor
	}
}

// 消费者
func Consumer(in <-chan int) {
	for v := range in {
		fmt.Println(v)
	}
}
func TestM(t *testing.T) {
	ch := make(chan int, 64) // 成果队列

	go Producer(3, ch) // 生成 3 的倍数的序列
	go Producer(5, ch) // 生成 5 的倍数的序列
	go Consumer(ch)    // 消费 生成的队列

	// 运行一定时间后退出
	time.Sleep(5 * time.Second)
}
