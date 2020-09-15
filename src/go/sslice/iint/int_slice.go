package iint

import (
	"errors"
)

func Max(a, b int) int {
	if a > b {
		return a
	}
	return b
}

func Min(a, b int) int {
	if a > b {
		return b
	}
	return a
}

func MaxInSlice(s []int) (int, error) {
	if len(s) == 0 {
		return 0, errors.New("")
	}
	var max int
	for i, v := range s {
		if i == 0 {
			max = v
		} else {
			max = Max(max, v)
		}
	}
	return max, nil
}

func MinInSlice(s []int) (int, error) {
	if len(s) == 0 {
		return 0, errors.New("")
	}
	var min int
	for i, v := range s {
		if i == 0 {
			min = v
		} else {
			min = Min(min, v)
		}
	}
	return min, nil
}
