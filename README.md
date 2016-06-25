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

## Recommended Coding Environment

At the moment, the only "one" I have written is for VIM.
To apply it, simply copy the contents inside `sample/.vim` into `~/.vim` and then save Pb code with the extension `.pb` or `.pbscript`.

## Potential Useful Information?

### Operator Precedence

| Precedence  | Operator  | Description  |
|-------------|-----------|--------------|
|      1      |    `:`    | Subscripting |
|      2      |`*` `/` `%`| Multiplication, division, and remainder |
|      3      |  `+` `-`  | Addition and subtraction |
|      4      |  `<` `>`  | Relational operators |
|      5      |   `<>`    |  Not equals  |
|             |    `?`    |  If identifier is defined |

### Directive Cheatsheet

| Name | Alternative name | Description |
|------|------------------|-------------|
|`!CALL` | `!call`            |Calls a macro|
|`!MACRO` to `!ENDMAC`| `!macro` to `!endmac` |Define a macro|
|`!DEFINE`|`!define`| Defines a constant value |
|`!UNDEF`|`!undef`| Undefines a constant value |
|`!INCLUDE`|`!include`| Expands a file (same interpret unit) |
| `!IF`, `!ELSEIF`, `!ELSE` to `!ENDIF` | `!if`, `!elseif`, `!else` to `!endif` | Conditional interpreting |
