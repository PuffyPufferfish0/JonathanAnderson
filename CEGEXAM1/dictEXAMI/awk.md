# `awk` (Pattern Scanning & Processing)

Unlike `grep` (which finds lines) or `sed` (which edits strings), `awk` is essentially a full programming language designed to parse structured data, columns, and rows.

## The Core Structure

`awk 'pattern { action }' file`

- If no pattern is provided, the action runs on every line.
    
- If no action is provided, the default action is `{print $0}` (print the whole line).
    

## Built-in Variables

- **`$0`**: The entire line/record.
    
- **`$1, $2, $3...`**: The 1st, 2nd, 3rd column/field. (By default, columns are separated by whitespace).
    
- **`FS`**: Field Separator (default is space). Can be changed with `-F` (e.g., `-F':'` for `/etc/passwd`).
    

## Lab 05 Breakdown (Sales Data)

> [!tip] The Match Operator (`~`) In `awk`, `==` means "exactly equals", but `~` means "matches the regular expression".

### 1. Matching Columns to Regex

**Goal:** Find sales from February 2024. **Command:** `awk '$1 ~ /^2024-02/' sales.txt`

- `~`: Tells awk to test the 1st column (`$1`) against the regex `/^2024-02/`.
    

### 2. Math and Conditionals

**Goal:** Print the 2nd column if the 5th column is 100 or greater. **Command:** `awk '$5 >= 100 {print $2}' sales.txt`

- `awk` can perform standard mathematical comparisons (`>=`, `<=`, `==`, `!=`).
    

### 3. Variable Accumulation and the `END` Block

**Goal:** Sum up the total sales (col 6) for "Kitchen" items (col 3). **Command:** `awk '$3 == "Kitchen" {sum += $6} END {print sum}' sales.txt`

- `$3 == "Kitchen"`: The pattern.
    
- `{sum += $6}`: The action. Creates a variable called `sum` and adds the value of column 6 to it every time the pattern matches.
    
- `END {print sum}`: The `END` block only executes _after_ awk has finished reading the entire file.
    

### 4. Built-in Functions (`gsub`)

**Goal:** Replace "Sofa" with "Couch" and output to a new file. **Command:** `awk '{gsub(/Sofa/, "Couch"); print}' sales.txt > updates-sales.txt`

- `gsub(regex, replacement, [target])`: Global substitution function inside `awk`. If no target is specified, it runs on `$0` (the whole line).
    
- Paired with `[[IO Redirection]]` (`>`) to save the output.