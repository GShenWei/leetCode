package tool

import (
	"fmt"
	"github.com/cheekybits/genny/generic"
)

//genny -in=src/go/tool/map.go -out=src/go/tool/map-gen2.go gen "XkItem=string,int XvItem=string,int"
//genny -in range.go -out range_tool.go gen "RangeItem=int,int32,int64,uint,uint32,uint64,float32,float64"
type RangeItem generic.Type

type RangeItemClosed struct {
	Least RangeItem
	Most  RangeItem
}
type RangeItemRange interface {
	Contains(x RangeItem) bool
	ToString() string
}

func (r RangeItemClosed) Contains(x RangeItem) bool {
	return x >= r.Least && x <= r.Most
}
func (r RangeItemClosed) ToString() string {
	return fmt.Sprintf("[%v,%v]", r.Least, r.Most)
}

type RangeItemOpen struct {
	Least RangeItem
	Most  RangeItem
}

func (r RangeItemOpen) Contains(x RangeItem) bool {
	return x > r.Least && x < r.Most
}
func (r RangeItemOpen) ToString() string {
	return fmt.Sprintf("(%v,%v)", r.Least, r.Most)
}

type RangeItemOpenClosed struct {
	Least RangeItem
	Most  RangeItem
}

func (r RangeItemOpenClosed) Contains(x RangeItem) bool {
	return x > r.Least && x <= r.Most
}
func (r RangeItemOpenClosed) ToString() string {
	return fmt.Sprintf("(%v,%v]", r.Least, r.Most)
}

type RangeItemClosedOpen struct {
	Least RangeItem
	Most  RangeItem
}

func (r RangeItemClosedOpen) Contains(x RangeItem) bool {
	return x >= r.Least && x < r.Most
}
func (r RangeItemClosedOpen) ToString() string {
	return fmt.Sprintf("[%v,%v)", r.Least, r.Most)
}

type RangeItemGreaterThan struct {
	Least RangeItem
}

func (r RangeItemGreaterThan) Contains(x RangeItem) bool {
	return x > r.Least
}
func (r RangeItemGreaterThan) ToString() string {
	return fmt.Sprintf("(%v,+∞)", r.Least)
}

type RangeItemAtLeast struct {
	Least RangeItem
}

func (r RangeItemAtLeast) Contains(x RangeItem) bool {
	return x >= r.Least
}
func (r RangeItemAtLeast) ToString() string {
	return fmt.Sprintf("[%v,+∞)", r.Least)
}

type RangeItemLessThan struct {
	Most RangeItem
}

func (r RangeItemLessThan) Contains(x RangeItem) bool {
	return x < r.Most
}
func (r RangeItemLessThan) ToString() string {
	return fmt.Sprintf("(+∞,%v)", r.Most)
}

type RangeItemAtMost struct {
	Most RangeItem
}

func (r RangeItemAtMost) Contains(x RangeItem) bool {
	return x <= r.Most
}
func (r RangeItemAtMost) ToString() string {
	return fmt.Sprintf("(+∞,%v]", r.Most)
}
