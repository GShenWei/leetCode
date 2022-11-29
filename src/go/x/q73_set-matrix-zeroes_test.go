package main

import (
	"fmt"
	"testing"
)

func TestGo2(t *testing.T) {
	//x := [][]int{{1, 1, 1}, {1, 0, 1}, {1, 1, 1}}
	x := [][]int{{0, 1, 2, 0}, {3, 4, 5, 2}, {1, 3, 1, 5}}
	//setZeroes(x)
	gl(setZeroes, x)
	fmt.Println(x)
}

func setZeroes(matrix [][]int) {
	spe := -2 << 31
	for i := range matrix {
		for j := range matrix[i] {
			if matrix[i][j] == 0 {
				for k := 0; k < len(matrix[i]); k++ {
					if matrix[i][k] != 0 {
						matrix[i][k] = spe
					}
				}
				for k := 0; k < len(matrix); k++ {
					if matrix[k][j] != 0 {
						matrix[k][j] = spe
					}
				}
			}
		}
	}
	for i := range matrix {
		for j := range matrix[i] {
			if matrix[i][j] == spe {
				matrix[i][j] = 0
			}
		}
	}
}

func TestPlo(t *testing.T) {
	var x []int
	ints := append(x, 90)
	fmt.Println(ints)

	xx := make([]int, 20)
	for i := range xx {
		xx[i] = i
	}
	fmt.Println(xx)

	gg := xx[5:6]
	fmt.Println(gg)

	var nn [][]int
	var c = append(nn, xx[5:9])
	c = append(c, xx[6:9])
	fmt.Println(c)
}

type Pi interface {
}

type Po interface {
}

func TestPl(t *testing.T) {
	pis := make([]Pi, 2)
	var xx = append(pis, new(Po))
	fmt.Println(xx)
}

func gl(f func([][]int), matrix [][]int) {
	f(matrix)
}

func Test_io(t *testing.T) {
	var x = []int{0, 0, 0, 0, 0, 0}
	gg(x)
	fmt.Println(x)
}

func gg(x []int) {
	x[0] = 2
	x[1] = 2
	x[2] = 2
}
