package main

import (
	"fmt"
	"math"
	"math/rand"
	"time"
)

type Monastery int

const (
	GuanYin Monastery = 0
	GuanYan Monastery = 1
)

type Monk struct {
	qi        int
	monastery Monastery
}

func main() {
	var monks []Monk
	r := rand.New(rand.NewSource(time.Hour.Milliseconds()))
	size := int(math.Pow(2, 10))
	for i := 0; i < size; i++ {
		monks = append(monks, Monk{r.Intn(200) + 100, Monastery(r.Intn(2))})
	}

	ch := make(chan int)
	go getWinnerFromArray(monks, 0, size-1, ch)
	winner := monks[<-ch]

	if winner.monastery == 0 {
		fmt.Println("Winner from Guan-Yin monastery and his qi is", winner.qi)
	} else {
		fmt.Println("Winner from Guan-Yan monastery and his qi is", winner.qi)
	}
}

func getWinnerFromArray(monks []Monk, start, end int, ch chan int) {
	if end-start <= 1 {
		ch <- getWinnerFromTwo(monks, start, end)
		return
	}

	subCh := make(chan int)
	mid := (start + end) / 2
	go getWinnerFromArray(monks, start, mid, subCh)
	go getWinnerFromArray(monks, mid+1, end, subCh)
	w1, w2 := <-subCh, <-subCh
	ch <- getWinnerFromTwo(monks, w1, w2)
	return
}

func getWinnerFromTwo(monks []Monk, index1, index2 int) int {
	if monks[index1].qi > monks[index2].qi {
		return index1
	}
	return index2
}
