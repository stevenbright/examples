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

var STUB_USER_COUNTER int

// NOTE: passing pointer IS IMPORTANT!
func (m *StubUserService) PersistUser(name string, age int) string {
	STUB_USER_COUNTER = STUB_USER_COUNTER + 1 // NOTE: DO NOT use ':=' instead of '=' here as it will result in creating local variable
	id := strings.Join([]string{"A", strconv.Itoa(STUB_USER_COUNTER)}, "")

	u := new (User)
	u.Id = id
	u.Name = name
	u.Age = age

	m.Users = append(m.Users, *u)

	return id
}

func (m *StubUserService) GetUsers() []User {
	return m.Users
}

func main() {
	fmt.Println("DI+OOP+SimpleAspect Sample")

	u := new (StubUserService)
	u.PersistUser("alex", 20)
	u.PersistUser("bob", 30)
	u.PersistUser("jane", 18)
	u.PersistUser("cavin", 25)

	fmt.Println("Users:", u.GetUsers())
}
