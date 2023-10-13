package main

import (
	"fmt"
	"log"
	"math/rand"
	"os"
	"sync"
	"time"
)

type PlantState bool

const (
	Wilted        PlantState = false
	Good          PlantState = true
	GARDEN_WIDTH             = 100
	GARDEN_HEIGHT            = 20
	GARDEN_PATH              = "Labs/src/Lab4/TaskB/resources/garden.txt"
)

type Garden struct {
	mu                  sync.RWMutex
	width, height       int
	field               []PlantState
	wiltedPlantsIndexes []int
}

func main() {
	garden := createGarden(GARDEN_WIDTH, GARDEN_HEIGHT)

	go garden.wiltPlants()
	go garden.waterPlants()
	go garden.copyToFile()
	go garden.print()

	for {
	}
}

func createGarden(width, height int) *Garden {
	garden := Garden{width: width, height: height}
	for i := 0; i < width*height; i++ {
		if rand.Float32() < 0.8 {
			garden.field = append(garden.field, Good)
		} else {
			garden.field = append(garden.field, Wilted)
			garden.wiltedPlantsIndexes = append(garden.wiltedPlantsIndexes, i)
		}
	}
	return &garden
}

func (garden *Garden) waterPlants() {
	for {
		garden.mu.Lock()
		if len(garden.wiltedPlantsIndexes) > 0 {
			index := garden.wiltedPlantsIndexes[0]
			garden.wiltedPlantsIndexes = garden.wiltedPlantsIndexes[1:]
			garden.field[index] = Good
		}
		garden.mu.Unlock()

		time.Sleep(12 * time.Millisecond)
	}
}

func (garden *Garden) wiltPlants() {
	for {
		garden.mu.Lock()
		index := rand.Intn(garden.height)*garden.width + rand.Intn(garden.width)
		garden.field[index] = Wilted
		garden.wiltedPlantsIndexes = append(garden.wiltedPlantsIndexes, index)
		garden.mu.Unlock()

		time.Sleep(10 * time.Millisecond)
	}
}

func (garden *Garden) copyToFile() {
	for {
		garden.mu.RLock()
		gardenStr := ""

		for i := 0; i < garden.height; i++ {
			for j := 0; j < garden.width; j++ {
				if garden.field[i*garden.width+j] == Good {
					gardenStr += "1"
				} else {
					gardenStr += "0"
				}
			}
			gardenStr += "\n"
		}

		gardenByte := []byte(gardenStr)
		err := os.WriteFile(GARDEN_PATH, gardenByte, 0644)

		if err != nil {
			log.Fatal(err)
		}

		garden.mu.RUnlock()

		time.Sleep(3 * time.Second)
	}
}

func (garden *Garden) print() {
	for {
		garden.mu.RLock()
		content, err := os.ReadFile(GARDEN_PATH)
		garden.mu.RUnlock()

		if err != nil {
			log.Fatal(err)
		}

		fmt.Println(string(content))

		time.Sleep(3 * time.Second)
	}
}
