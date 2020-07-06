package main

import (
	"fmt"
	"testing"
)

func TestGo(t *testing.T) {
	x := []int{3, -2, 5}
	fmt.Println(maxSubarraySumCircular(x))
}

func maxSubarraySumCircular(A []int) int {
	max, maxNow := -2<<31, -2<<31
	min, minNow := 2<<31, 2<<31
	maxVal := -2 << 31
	total := 0
	for i := 0; i < len(A); i++ {
		total += A[i]
		maxVal = Max(maxVal, A[i])

		maxNow = Max(A[i], maxNow+A[i])
		max = Max(max, maxNow)

		minNow = Min(A[i], minNow+A[i])
		min = Min(min, minNow)
	}
	if maxVal <= 0 {
		return maxVal
	}
	return Max(max, total-min)
}

func Max(a int, b int) int {
	if a > b {
		return a
	} else {
		return b
	}

}

func Min(a int, b int) int {
	if a < b {
		return a
	} else {
		return b
	}
}
