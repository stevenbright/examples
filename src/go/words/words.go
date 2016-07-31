package main

import "fmt"

func getDuplicates(sequence string, duplicateSize int) map[string]int {
	entries := make(map[string]int)
	length := len(sequence) - duplicateSize

	// fill out duplicates
	for i:= 0; i < length; i++ {
		seqSlice := sequence[i:i + duplicateSize]
		oldValue, exists := entries[seqSlice]
		if (exists) {
			entries[seqSlice] = oldValue + 1
		} else {
			entries[seqSlice] = 1
		}
	}

	// create resultant map with unique entries
	result := make(map[string]int)
	for k, v := range(entries) {
		if v > 1 {
			result[k] = v
		}
	}

	return result
}

func demo(word string, duplicateSize int) {
	fmt.Println("Word:", word, "; duplicates:", getDuplicates(word, duplicateSize))
}

func main() {
	demo("AAGGTT", 2)
	demo("AAAGTATGAATATGGATT", 3)
	demo("GAATTGAATTAAA", 4)
}

