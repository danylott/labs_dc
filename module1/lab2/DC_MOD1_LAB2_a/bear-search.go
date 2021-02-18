package main

import (
	"fmt"
	"sync"
	"time"
)

const size = 1000
const numThreads = 5

var forest [size][size]bool
type IsFound struct {
	found bool
}

type Bag struct {
	works []func()
}
var mutex sync.Mutex

func SearchBear(row int, isFound *IsFound) func() {
	return func() {
		if !isFound.found {
			fmt.Printf("Row %d started \n", row)
			for i := 0; i < size; i += 1 {
				if forest[row][i] {
					isFound.found = true
					fmt.Printf("Bear found at (%d, %d)\n", row, i)
					break
				}
			}
			fmt.Printf("Row %d done\n", row)
		}
	}
}

func TaskBag(bag *Bag, found *IsFound) {
	for true {
		var work func()
		mutex.Lock()
		if len(bag.works) != 0 {
			work = bag.works[0]
			bag.works = bag.works[1:]
		}
		mutex.Unlock()
		if work != nil && !found.found {
			go work()
		} else {
			break
		}
	}
}

func main() {
	isFound := IsFound{false}
	bag := Bag{make([]func(), 0, 0)}
	forest[99][69] = true
	for i := 0; i < size; i += 1 {
		bag.works = append(bag.works, SearchBear(i, &isFound))
	}
	for i := 0; i < numThreads; i += 1 {
		go TaskBag(&bag, &isFound)
	}
	time.Sleep(2 * time.Second)
}
