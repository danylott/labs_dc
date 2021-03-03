package main

import(
	"bufio"
	"fmt"
	"log"
	"os"
	"sync"
	"time"
)

func searchName(String string) string {
	name:= ""
	for i:= 0;i < len(String);i++{
		if string(String[i]) == " " {
			break
		} else {
			name += string(String[i])
		}
	}
	return name
}
func searchByName(mutex * sync.RWMutex, waitGroup * sync.WaitGroup, name string) {
	defer waitGroup.Done()
	mutex.RLock()
	fmt.Println("Locked for looking for " + name)
	time.Sleep(2 * time.Second)
	answer:= "There are no such contact"
	file, err:= os.Open("database.txt")
	if err != nil {
		log.Fatal(err)
	}
	defer file.Close()

	scanner:= bufio.NewScanner(file)

	for scanner.Scan() {
		if searchName(scanner.Text()) == name {
			answer = scanner.Text()
			break
		}
	}

	if err:= scanner.Err();
		err != nil {
		log.Fatal(err)
	}
	fmt.Println("Result of looking for " + name + ": " + answer)
	time.Sleep(2 * time.Second)
	fmt.Println("Unlocked Looking for " + name)
	mutex.RUnlock()
}
func searchNumber(String string) string {
number:= ""
start:= false
	for i:= 0;i < len(String);i++{
		if start {
			number += string(String[i])
		}
		if string(String[i]) == " " {
			start = true
		}
	}
	return number
}


func searchByNumber(mutex * sync.RWMutex, waitGroup * sync.WaitGroup, number string) {
	defer waitGroup.Done()
	mutex.RLock()
	fmt.Println("Locked for looking for " + number)
	time.Sleep(2 * time.Second)
answer:= "There are no such contact"
	file, err:= os.Open("database.txt")
	if err != nil {
		log.Fatal(err)
	}
	defer file.Close()

scanner:= bufio.NewScanner(file)

	for scanner.Scan() {
		if searchNumber(scanner.Text()) == number {
			answer = scanner.Text()
			break
		}
	}

	if err:= scanner.Err();
	err != nil {
		log.Fatal(err)
	}
	fmt.Println("Result of looking for " + number + ": " + answer)
	time.Sleep(2 * time.Second)
	fmt.Println("Unlocked Looking for " + number)
	mutex.RUnlock()
}
func DeleteAdd(mutex * sync.RWMutex, waitGroup * sync.WaitGroup, name string, number string, delete bool) {
	defer waitGroup.Done()
	mutex.Lock()
	fmt.Println("Locked for DeleteAdd " + name + " " + number)
	time.Sleep(2 * time.Second)
	fileR, errR:= os.Open("database.txt")
	if errR != nil {
		log.Fatal(errR)
	}
scanner:= bufio.NewScanner(fileR)

	fileW, errW:= os.Create("newFile.txt")
	if errW != nil {
		fmt.Println("Unable to create file:", errW)
		os.Exit(1)
	}

	if delete {
		for scanner.Scan() {
		text:= scanner.Text()
			if searchName(text) == name && searchNumber(text) == number {
				fmt.Println("Skip " + text)
			} else {
				fileW.WriteString(text + "\n")
			}
		}

		if errR:= scanner.Err();
		errR != nil {
			log.Fatal(errR)
		}
	} else {
		for scanner.Scan() {
			fileW.WriteString(scanner.Text() + "\n")
		}
		fileW.WriteString(name + " " + number + "\n")

		if errR:= scanner.Err();
		errR != nil {
			log.Fatal(errR)
		}
	}

	fileR.Close()
	fileW.Close()

errD:= os.Remove("database.txt")
	if errD != nil {
		fmt.Println(errD)
		return
	}

errC:= os.Rename("newFile.txt", "database.txt")
	if errC != nil {
		fmt.Println(errC)
		return
	}
	fmt.Println("Deleted or Added " + name + " " + number)
	time.Sleep(2 * time.Second)
	fmt.Println("Unlocked DeleteAdd " + name + " " + number)
	mutex.Unlock()
}

func main() {
	var waitGroup sync.WaitGroup

mutex:= sync.RWMutex {}

	waitGroup.Add(1)
	go searchByName( & mutex, & waitGroup, "L.P.ChemUKogotoZanimat")
	time.Sleep(time.Second)

	waitGroup.Add(1)
	go searchByNumber( & mutex, & waitGroup, "+88005553535")
	time.Sleep(time.Second)
	waitGroup.Add(1)

	go DeleteAdd( & mutex, & waitGroup, "Fedor", "+2901341234", true)
	time.Sleep(time.Second)
	waitGroup.Add(1)

	go DeleteAdd( & mutex, & waitGroup, "Ponchiki", "+34075010350", false)
	time.Sleep(time.Second)
	waitGroup.Add(1)

	go searchByName( & mutex, & waitGroup, "MyHero")

	waitGroup.Wait()
}
