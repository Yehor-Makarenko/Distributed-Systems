package main

import (
	"fmt"
	"math/rand"
	"sync"
)

const (
	LINES = 4
)

type Line struct {
	a, b, c, d int
	wg         *sync.WaitGroup
	ch         chan bool
}

func main() {
	var wg sync.WaitGroup
	wg.Add(LINES)
	ch := make(chan bool)
	chEnd := make(chan bool)
	var lines [LINES]*Line

	for i := 0; i < LINES; i++ {
		lines[i] = &Line{rand.Intn(20), rand.Intn(20), rand.Intn(20), rand.Intn(20), &wg, ch}
	}

	go checkEquality(&lines, &wg, ch, chEnd)
	for i := 0; i < LINES; i++ {
		go changeLine(lines[i])
	}

	<-chEnd
	fmt.Println("\nFinished")
}

func changeLine(line *Line) {
	isDone := false
	for !isDone {
		t := rand.Int31n(5)
		if t == 0 && line.a > 0 {
			line.c++
			line.a--
		} else if t == 1 && line.c > 0 {
			line.a++
			line.c--
		} else if t == 2 && line.b > 0 {
			line.d++
			line.b--
		} else if line.d > 0 {
			line.b++
			line.d--
		}
		line.wg.Done()
		isDone = <-line.ch
	}
}

func checkEquality(lines *[LINES]*Line, wg *sync.WaitGroup, ch chan bool, chEnd chan bool) {
	for {
		wg.Wait()
		wg.Add(LINES)
		printLines(lines)

		sumCounts := make(map[int]int)
		for _, line := range lines {
			sum := line.a + line.b
			sumCounts[sum]++
			if sumCounts[sum] >= 3 {
				printLines(lines)
				for i := 0; i < LINES; i++ {
					ch <- true
				}
				chEnd <- true
				return
			}
		}

		for i := 0; i < LINES; i++ {
			ch <- false
		}
	}
}

func printLines(lines *[LINES]*Line) {
	fmt.Print("\n")
	for i := 0; i < LINES; i++ {
		fmt.Printf("\nA: %d, B: %d, C: %d, D: %d", lines[i].a, lines[i].b, lines[i].c, lines[i].d)
	}
}
