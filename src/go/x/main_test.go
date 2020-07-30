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
