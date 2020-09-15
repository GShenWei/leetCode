package tt

import (
	"fmt"
	"regexp"
	"testing"
)

func TestPo(t *testing.T) {
	a := [2]int{1, 2}
	b := [2]int{1, 2}
	fmt.Print(a == b)
}

var reg, _ = regexp.Compile(`^\d{11}$`)

func TestGo(t *testing.T) {
	fmt.Printf("%p\n", reg)
	k := reg.MatchString("1661100110011")
	fmt.Println(k)

}
