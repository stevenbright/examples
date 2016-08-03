package main

import "os"
import "fmt"
import "math"
import "time"

func RunValues() {
	fmt.Println("go" + "lang")
	fmt.Println("1+1 =", 1+1)
	fmt.Println("7.0/3.0 =", 7.0/3.0)
	fmt.Println("255 =", 0xff)
	fmt.Println(true && false)
	fmt.Println(true || false)
	fmt.Println(!true)
}

func RunVariables() {
	var a string = "initial"
	fmt.Println("a=", a)

	var b, c int = 1, 2
	fmt.Println(b, c)

	var d = true
	fmt.Println(d)

	var e int
	fmt.Println(e)

	f := "short"
	fmt.Println(f + "-string")
}

const S_CON string = "constant"

func RunConst() {
	fmt.Println(S_CON)

	const n = 500000000

	const d = 3e20 / n
	fmt.Println(d)

	fmt.Println(int64(d))

	fmt.Println(math.Sin(n))
}

func RunFor() {
	i := 1
	for i <= 3 {
		fmt.Println(i)
		i = i + 1
	}

	for j := 7; j <= 9; j++ {
		fmt.Println(j)
	}

	for {
		fmt.Println("loop!")
		break
	}
}

func RunIfElse() {
	if 7 % 2 == 0 {
		fmt.Println("7 is even???")
	} else {
		fmt.Println("7 is odd")
	}

	if 8 % 4 == 0 {
		fmt.Println("8 is divisible by 4")
	}

	if num := 9; num < 0 {
		fmt.Println(num, " is negative")
	} else if num < 10 {
		fmt.Println(num, " has one digit")
	} else {
		fmt.Println(num, " has multiple digits")
	}
}

func RunSwitch() {
	i := 2
	fmt.Print("write ", i, " as ")
	switch i {
	case 1:
		fmt.Println("one")
	case 2:
		fmt.Println("two")
	case 3:
		fmt.Println("three")
	}

	switch time.Now().Weekday() {
	case time.Saturday, time.Sunday:
		fmt.Println("it's the weekend!")
	default:
		fmt.Println("it's the weekday = ", time.Now().Weekday())
	}

	t := time.Now()
	switch {
	case t.Hour() < 12:
		fmt.Println("it's before noon")
	default:
		fmt.Println("it's after noon")
	}
}

func RunArrays() {
	var a [5]int
	fmt.Println("emp:", a)

	a[4] = 100
	fmt.Println("set:", a, "; get:", a[4], "; len: ", len(a))

	b := [5]int{1, 2, 3, 4, 5}
	fmt.Println("dcl(1):", b)
	b[1] = 1
	fmt.Println("dcl(2):", b)

	var twoD [2][3]int
	for i := 0; i < 2; i++ {
		for j := 0; j < 3; j++ {
			twoD[i][j] = i + j
		}
	}
	fmt.Println("2d: ", twoD)
}

func RunSlices() {
	s := make([]string, 3)
	fmt.Println("emp:", s)

	s[0] = "a"
	s[1] = "b"
	s[2] = "c"
	fmt.Println("set:", s, "; get:", s[2], "; len:", len(s))

	s = append(s, "d")
	s = append(s, "e", "f")
	fmt.Println("apd:", s)

	c := make([]string, len(s))
	copy(c, s)
	fmt.Println("copy:", c)

	fmt.Println("s[2:5]:", s[2:5], "; s[:5]", s[:5], "; s[2:]", s[2:])

	fmt.Println("dclSlice:", []string{"aa", "bb", "cc"})

	twoD := make([][]int, 3)
	for i := 0; i < 3; i++ {
		innerLen := i + 1
		twoD[i] = make([]int, innerLen)
		for j := 0; j < innerLen; j++ {
			twoD[i][j] = i + j
		}
	}
	fmt.Println("twoD:", twoD)
}

func RunMap() {
	m := make(map[string]int)

	m["k1"] = 7
	m["k2"] = 13
	fmt.Println("map:", m, "; m[k1]:", m["k1"], "; len(m):", len(m))

	delete(m, "k2")
	fmt.Println("map:", m, "; m[k2]:", m["k2"], "; len(m):", len(m))
	k2Val, prs := m["k2"]
	fmt.Println("map:", m, "; m[k2]:", k2Val, "; prs:", prs, "; len(m):", len(m))

	fmt.Println("prealloc map:", map[string]int{"foo": 1, "bar": 2})
}

func RunRange() {
	nums := []int{2, 3, 4}
	sum := 0
	for _, num := range nums {
		sum += num
	}
	fmt.Println("sum:", sum)

	for i, num := range nums {
		if num == 3 {
			fmt.Println("index:", i)
		}
	}

	kvs := map[string]string{"a": "apple", "b": "banana"}
	for k, v := range kvs {
		fmt.Printf("%s -> %s\n", k, v)
	}

	for i, c := range "go" {
		fmt.Println(i, c)
	}
}

func main() {
	args := os.Args
	if (len(args) < 2) {
		RunRange()
	} else {
		RunRange()
		RunMap()
		RunSlices()
		RunArrays()
		RunSwitch()
		RunFor()
		RunConst()
		RunVariables()
		RunValues()
	}
}
