package main

import (
	"fmt"
	"strings"
	"strconv"
)

type User struct {
	Id 		string
	Name	string
	Age 	int
}

type UserService interface {
	GetUsers() []User
	PersistUser(name string, age int) string
}

type StubUserService struct {
	Users []User
}

var COUNTER int

func (m StubUserService) PersistUser(name string, age int) string {
	COUNTER := COUNTER + 1
	id := strings.Join([]string{"A", strconv.Itoa(COUNTER)}, "")

	u := new (User)
	u.Id = id
	u.Name = name
	u.Age = age

	m.Users = append(m.Users, *u)

	return id
}

func (m StubUserService) GetUsers() []User {
	return m.Users
}

func main() {
	fmt.Println("DI+OOP+SimpleAspect Sample")

	u := new (StubUserService)
	u.PersistUser("alex", 20)

	fmt.Println("Users:", u.GetUsers())
}
