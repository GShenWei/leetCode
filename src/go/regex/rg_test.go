package regex

import (
	"fmt"
	"regexp"
	"strings"
	"testing"
)

func Test_rrrr(t *testing.T) {
	// a := regexp.MustCompile(`model\.`)
	mm := regexp.QuoteMeta(`\\\?`)
	fmt.Println(strings.ReplaceAll(mm, `\`, `\\`))
}
