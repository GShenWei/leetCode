package tool

import "github.com/cheekybits/genny/generic"

//genny -in=src/go/tool/map.go -out=src/go/tool/map-gen2.go gen "XkItem=string,int XvItem=string,int"
//genny -in map.go -out map-gen2.go gen "XkItem=string XvItem=int"
type XkItem generic.Type
type XvItem generic.Type

type XkItemSet struct {
	m map[XkItem]XvItem
}

func NewXkItemSet(s []XkItem) XkItemSet {
	var res = XkItemSet{m: map[XkItem]XvItem{}}
	for _, v := range s {
		res.m[v] = nil
	}
	return res
}

func (s *XkItemSet) Size() int {
	return len(s.m)
}

func (s *XkItemSet) ToSlice() []XkItem {
	var res []XkItem
	for v := range s.m {
		res = append(res, v)
	}
	return res
}

func (s *XkItemSet) Contains(item XkItem) bool {
	_, ex := s.m[item]
	return ex
}

func GetXkItemKeySet(m map[XkItem]XvItem) *XkItemSet {
	if m == nil {
		return &XkItemSet{m: map[XkItem]XvItem{}}
	}
	return &XkItemSet{m: m}
}
