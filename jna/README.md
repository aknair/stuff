## Rules to be followed when generating a C-shared library from Go

1. Program will be a main package
2. However the main function will be empty for obvious reasons
3. Go source will need to import "C" package
4. Functions should be explicitly using the //export FunctionName directive

Here is how you will generate the C-shared library for programs written in Go

`go build -buildmode=c-shared -o gojnalib.so gojnalib.go`

