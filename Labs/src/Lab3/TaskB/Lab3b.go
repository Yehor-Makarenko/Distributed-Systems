package main

import (
	"fmt"
	"math/rand"
	"sync"
	"time"
)

func main() {
	var wg sync.WaitGroup
	i := 0
	ch := make(chan int, 1)
	go startBarberWork(ch, &wg)

	for j := 0; j < 10; j++ {
		wg.Add(1)
		go addClient(ch, i)
		i++
		time.Sleep(time.Duration((rand.Intn(1000) + 500)) * time.Millisecond)
	}

	wg.Wait()
}

func startBarberWork(ch chan int, wg *sync.WaitGroup) {
	for {
		clientId := <-ch
		time.Sleep(time.Second)
		fmt.Println("Client number", clientId, "got a haircut")
		wg.Done()
	}
}

func addClient(ch chan int, id int) {
	ch <- id
}
