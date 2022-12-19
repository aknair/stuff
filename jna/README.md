## Here are some benefits of generating a C-shared library


1. Code reuse: You can write code once and reuse it in multiple programs and libraries.

2. Improved performance: Since C-shared library can be compiled specifically for the target platform, it can result in improved performance compared to interpreting the code at runtime.

3. Easier maintenance: Fix the code in a C-shared library, you only have to do it once and the changes will be reflected in all programs and libraries that use the library.

4. Flexibility: You can create a library that can be used by programs and libraries written in a variety of languages.


Rules to be followed when generating a C-shared library from Go

1. Program will be a main package
2. However the main function will be empty for obvious reasons
3. Go source will need to import "C" package
4. Functions should be explicitly using the //export FunctionName directive

Here is how you will generate the C-shared library for programs written in Go

`go build -buildmode=c-shared -o gojnalib.so gojnalib.go`

