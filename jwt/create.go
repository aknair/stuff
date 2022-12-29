package main

import (
	"fmt"
	"time"

	"github.com/golang-jwt/jwt"
)

func main() {
	token := jwt.NewWithClaims(jwt.SigningMethodHS256, jwt.MapClaims{
		"name":    "aknair",
		"issued":  time.Now(),
		"expires": time.Now().Add(time.Hour * 24 * 365 * 41),
	})
	hmacSampleSecret := []byte("this-is-my-hmac-signing-secret")

	tokenString, err := token.SignedString(hmacSampleSecret)

	fmt.Println(tokenString, err)

}
