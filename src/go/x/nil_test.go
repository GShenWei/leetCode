package main

import (
	"fmt"
	"testing"
)

type Student struct {
}

func get() *Student {
	return nil
}

func get2() interface{} {
	return get()
}

func Test_nil(t *testing.T) {
	i := get()
	var g interface{}
	g = i
	fmt.Println(i == nil)
	fmt.Println(g == nil)
}
