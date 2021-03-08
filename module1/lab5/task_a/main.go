package main

import (
	"fmt"
	"math/rand"
	"sync"
	"time"
)

func printLine(line *[60]string) string {
	answer := ""
	for i := 0; i < 60; i++ {
		answer = answer + line[i] + " "
	}
	return answer
}

func PartOne(waitGroup *sync.WaitGroup, line *[60]string) {
	defer waitGroup.Done()
	for i := 58; i >= 0; i-- {
		time.Sleep(time.Second / 2)
		if line[i+1] == "L" && line[i] == "R" {
			line[i] = "L"
		}
		fmt.Println("PART ONE:\n" + printLine(line))
	}

	fmt.Println("PART ONE IS DONE")
}

func PartTwo(waitGroup *sync.WaitGroup, line *[60]string) {
	defer waitGroup.Done()
	for i := 1; i < 60; i++ {
		time.Sleep(time.Second / 2)
		if line[i-1] == "R" && line[i] == "L" {
			line[i] = "R"
		}
		fmt.Println("PART TWO:\n" + printLine(line))
	}

	time.Sleep(10 * time.Second)
	fmt.Println("PART TWO IS DONE")
}

func createLine() [60]string {
	var res [60]string
	for i := 0; i < 60; i++ {
		pos := rand.Intn(2)
		if pos == 1 {
			res[i] = "L"
		} else if pos == 0 {
			res[i] = "R"
		}
	}
	return res
}

func main() {
	lineOne := createLine()
	lineTwo := createLine()
	//lineOne[59] = "Two"
	//lineTwo[0] = "One"
	for {
		fmt.Println("New Attempt")
		var waitGroup sync.WaitGroup

		waitGroup.Add(1)
		go PartOne(&waitGroup, &lineOne)
		waitGroup.Add(1)
		go PartTwo(&waitGroup, &lineTwo)

		waitGroup.Wait()

		if lineOne[59] == "R" && lineTwo[0] == "L" {
			lineOne[59] = "L"
			lineTwo[0] = "R"
		} else {
			break
		}
	}
}
