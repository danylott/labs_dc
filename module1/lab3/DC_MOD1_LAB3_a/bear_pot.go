package main

import (
	"fmt"
	"sync"
	"time"
)

const BEE_AMOUNT = 5
const POT_VOLUME = 20
var currentPot = 0
var mutex sync.Mutex

func Bee(isPotFull chan bool, currentPot* int) {
	for true {
		if *currentPot < POT_VOLUME {
			time.Sleep(100 * time.Millisecond)
			mutex.Lock()
			*currentPot += 1
			fmt.Printf("In pot: %d\n", *currentPot)
			if *currentPot >= POT_VOLUME {
				isPotFull <- true
			}
			mutex.Unlock()
		}
	}
}

func Bear(isPotFull chan bool, currentPot* int) {
	// wait for pot to be fulfilled
	for true {
		<-isPotFull
		mutex.Lock()
		*currentPot -= POT_VOLUME
		print("Bear eats all honey!\n")
		mutex.Unlock()
		time.Sleep(100 * time.Millisecond)
	}
}

func main() {
	isPotFull := make(chan bool)
	for i := 0; i < BEE_AMOUNT; i += 1 {
		go Bee(isPotFull, &currentPot)
	}
	go Bear(isPotFull, &currentPot)
	time.Sleep(1000 * time.Second)
}
