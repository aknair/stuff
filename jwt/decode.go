package main

import (
	"fmt"

	"github.com/golang-jwt/jwt"
)

func main() {

	tokenString := "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHBpcmVzIjoiMjA2My0xMi0xOVQxMjozNDozMC44OTUyOTItMDc6MDAiLCJpc3N1ZWQiOiIyMDIyLTEyLTI5VDEyOjM0OjMwLjg5NTI5MS0wNzowMCIsIm5hbWUiOiJha25haXIifQ.hp7DplYjGw3e0RHcqfOm-kJJ78uRyvKAsA2lHgf3XlI"

	token, _ := jwt.Parse(tokenString, func(token *jwt.Token) (interface{}, error) {
		if _, ok := token.Method.(*jwt.SigningMethodHMAC); !ok {
			return nil, fmt.Errorf("Unexpected signing method: %v", token.Header["alg"])
		}
		return []byte("this-is-my-hmac-signing-secret"), nil
	})
	fmt.Println(token.Claims, token.Valid)
}
