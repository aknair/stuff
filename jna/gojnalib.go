package main

import "C"
import (
	"crypto/aes"
	"crypto/cipher"
	"crypto/rand"
	"fmt"
	"io"
)

//export GetGoString
func GetGoString() *C.char {
	greeting := "Hello, World!, from Go"
	return C.CString(fmt.Sprintf("%s", greeting))

}

//export GetGoInt
func GetGoInt() int {
	return 42
}

//export EncryptMessage
func EncryptMessage(key, message string) *C.char {

	fmt.Printf("Key from Java is %v", key)
	fmt.Println(" & Message length is", len(message))
	block, err := aes.NewCipher([]byte(key))
	if err != nil {
		fmt.Println("NewCipher", err)
	}
	nonce := make([]byte, 12)
	if _, err := io.ReadFull(rand.Reader, nonce); err != nil {
		fmt.Println("Nonce", err)
	}
	gcm, err := cipher.NewGCM(block)
	if err != nil {

		fmt.Println("GCM", err)
	}
	ciphertext := gcm.Seal(nil, nonce, []byte(message), nil)
	// fmt.Printf("Returning %s as %x (base 16 encoded) from Java\n", ciphertext, ciphertext)
	return C.CString(fmt.Sprintf("%x", ciphertext))
}

func main() {

}
