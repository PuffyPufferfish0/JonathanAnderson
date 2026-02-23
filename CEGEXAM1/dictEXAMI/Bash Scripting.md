# Bash Scripting: Master Dictionary

A complete reference guide for Bash scripting operations, test conditions, special variables, and control flow.

## 1. Special Variables & Parameters

Bash automatically populates these variables when a script runs.

- **`$0`**: The name of the script itself.
    
- **`$1`, `$2`, `$3`...**: Positional arguments passed to the script. (Note: use `${10}` for double digits).
    
- **`$#`**: The total number of arguments passed to the script.
    
- **`$@`**: All positional arguments as a single list/array (useful in `for` loops).
    
- **`$?`**: The exit status of the _last command executed_. (`0` = success, non-zero = error).
    
- **`$$`**: The Process ID (PID) of the current shell/script.
    
- **`$USER`**: The username of the user running the script.
    
- **`$HOME`**: The absolute path to the user's home directory.
    

## 2. Test Operators (`[ ]` or `test`)

Used inside `if` statements or `while` loops to evaluate conditions.

### File Operators

- **`-e file`**: True if the file/directory **exists**.
    
- **`-f file`**: True if it exists and is a **regular file** (not a directory).
    
- **`-d dir`**: True if it exists and is a **directory**.
    
- **`-r file`**: True if the file exists and is **readable**.
    
- **`-w file`**: True if the file exists and is **writable**.
    
- **`-x file`**: True if the file exists and is **executable**.
    
- **`-s file`**: True if the file exists and is **not empty** (size > 0).
    

### String Operators

- **`-z "$str"`**: True if the string is **empty** (Zero length). _Crucial for checking if a user provided an argument._
    
- **`-n "$str"`**: True if the string is **not empty**.
    
- **`"$str1" = "$str2"`**: True if the strings are exactly equal. (Can also use `==`).
    
- **`"$str1" != "$str2"`**: True if the strings are not equal.
    

> [!warning] Always Quote Your Strings! Always wrap variables in double quotes inside a test (e.g., `[ -z "$1" ]`). If `$1` is empty and unquoted, Bash sees `[ -z ]` and crashes.

### Integer (Math) Operators

- **`-eq`**: Equal to (`==`)
    
- **`-ne`**: Not equal to (`!=`)
    
- **`-gt`**: Greater than (`>`)
    
- **`-ge`**: Greater than or equal to (`>=`)
    
- **`-lt`**: Less than (`<`)
    
- **`-le`**: Less than or equal to (`<=`)
    

## 3. Variable Expansion & Math

- **Command Substitution `$(cmd)`**: Executes a command and saves its output to a variable.
    
    - _Example:_ `FILES=$(ls)`
        
- **Arithmetic Expansion `$((math))`**: Evaluates basic math equations.
    
    - _Example:_ `SUM=$((5 + 10))`
        
- **Default Values `${var:-default}`**: If `$var` is empty, use "default" instead.
    
- **String Length `${#var}`**: Returns the number of characters in a string.
    

## 4. Built-in Commands & Flags

### `read` (User Input)

- **`-p "prompt"`**: Displays a prompt on the same line before waiting for input. (e.g., `read -p "Enter name: " name`)
    
- **`-s`**: Silent mode. Does not echo the typed characters to the screen (useful for passwords).
    

### `echo` vs `printf`

- **`echo -e`**: Enables interpretation of backslash escapes (like `\n` for newline or `\t` for tab).
    
- **`echo -n`**: Prevents `echo` from adding a trailing newline at the end.
    
- **`printf`**: Gives much better control over formatting than `echo`.
    
    - _Example:_ `printf "Total: %0.2f\n" $amount`
        

## 5. Control Flow Structures

### `if / elif / else`

```
if [ "$1" = "add" ]; then
    echo "Adding record..."
elif [ "$1" = "remove" ]; then
    echo "Removing record..."
else
    echo "Invalid command."
fi
```

### `case / esac` (Pattern Matching)

Great alternative to massive `if/elif` chains (useful for your Lab 04 `ft` tracker).

```
case "$1" in
    "add")
        echo "Adding record..."
        ;;
    "remove")
        echo "Removing record..."
        ;;
    *)
        echo "Usage: ./ft [add|remove|view|clear]"
        ;;
esac
```

### Loops (`for` and `while`)

```
# For loop (Iterating through arguments)
for arg in "$@"; do
    echo "Processing $arg"
done

# While loop (Reading a file line by line)
while read -r line; do
    echo "Line: $line"
done < input.txt
```

# Essential Concepts (Intro to Scripting)

_The following covers the core basics of writing and executing scripts._

### Setting up a Script

Writing sequences of Bash commands into a file to automate tasks must usually start with a [[Shebang]] (`#!/bin/bash`).

- **Exit Codes**: `exit 0` means success. `exit 1` (or any non-zero) means an error occurred. It's good practice to exit with an error code if the script fails so other programs know it failed.
    

### Basic Conditionals (`if` / `test`)

Used to control logic flow. Often uses `[ ]` (which is an alias for the `test` command).

```
if [ -z "$1" ]; then
    echo "Argument 1 is empty"
elif [ -f "test.txt" ]; then
    echo "test.txt is a file and exists"
else
    echo "Done"
fi
```

### Script Execution (The PATH)

- **Running locally**: `./script.sh` (Requires `chmod 755 script.sh`).
    
- **Running globally**: Create a [[symbolic link]] of your script and place it in a directory listed in your `$PATH`.
    
    - _Example from Lab 04:_ `sudo ln -s ~/lab04/ft /usr/local/bin/ft` allows you to run `ft` from anywhere without typing `./`.