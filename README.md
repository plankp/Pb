# Pb
Like lead. Like peanut butter. 

Back again but this time, lexing and parsing is done with ANTLR4.

This is a programming language that tries to be extremely messed up and is designed to give the user a very sketchy feel when using it.
See in `sample/` folder for examples.

## Dependencies

Requires gradle for building and jdk 8.

## Build Instructions

Open the terminal and type in

```sh
./gradlew build
```

Then open the directory `build/distributions`.
The tar and zip files are the finished productions.

## Potential Useful Information?

### Operator Precedence

| Precedence  | Operator  | Description  |
|-------------|-----------|--------------|
|      1      |    `:`    | Subscripting |
|      2      |`*` `/` `%`| Multiplication, division, and remainder |
|      3      |  `+` `-`  | Addition and subtraction |
|      4      |  `<` `>`  | Relational operators |
|      5      |   `<>`    |  Not equals  |

