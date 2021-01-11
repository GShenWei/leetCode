package int_set

type IntSet struct {
	m map[int]*int
}

func NewSet(s []int) IntSet {
	var res = IntSet{m: map[int]*int{}}
	for _, v := range s {
		res.m[v] = nil
	}
	return res
}

func (s *IntSet) Size() int {
	return len(s.m)
}

func (s *IntSet) ToSlice() []int {
	var res []int
	for v := range s.m {
		res = append(res, v)
	}
	return res
}

func (s *IntSet) Contains(item int) bool {
	_, ex := s.m[item]
	return ex
}
