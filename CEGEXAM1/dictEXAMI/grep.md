# `grep` (Global Regular Expression Print)

`grep` is used to search text for patterns. In CEG 2350, it is heavily paired with [[Regex]] and piped to other tools like `wc` for counting logs.

## Essential Flags

- **`-E`**: Extended Regular Expressions. Allows you to use characters like `+`, `?`, `|`, and `()` without escaping them. _Almost always use this instead of standard grep._
    
- **`-P`**: Perl-compatible Regular Expressions. Enables advanced shortcuts like `\d` (digits) or `\s` (whitespace).
    
- **`-i`**: Ignore case (case-insensitive).
    
- **`-v`**: Invert match (returns lines that do _not_ contain the pattern).
    
- **`-r`**: Recursive search through all files in a directory.
    

## Lab 05 Regex Breakdown (Access Logs)

> [!info] The Power of `wc -l` In Lab 05, you piped `grep` output to `wc -l` to count matches. `wc` stands for "word count," and `-l` tells it to only count the **lines**. Example: `grep "pattern" file | wc -l`

### 1. Anchors & Escaping

**Goal:** Find IPs starting with 192. **Command:** `grep -E "^192\." access.log`

- `^`: Anchor. Means the line _must_ start with the following characters.
    
- `192`: The literal string.
    
- `\.`: Escaped period. Without the `\`, a `.` in regex means "any single character". Escaping it forces it to look for a literal dot.
    

### 2. Perl Regex (`-P`) & Wildcards

**Goal:** Find logs with a `1` in the third octet of the IP. **Command:** `grep -P "^\d+\.\d+\.\d*1\d*\." access.log`

- `\d+`: Matches one or more digits (e.g., "192").
    
- `\d*`: Matches zero or more digits. `\d*1\d*` means "any amount of digits, followed by a 1, followed by any amount of digits".
    

### 3. Space Sensitivity

**Goal:** Find exact `/faq` page requests (excluding `/faq/about` etc.). **Command:** `grep -E "/faq " access.log`

- Adding the space at the end of `/faq` ensures you only match the exact directory request before the HTTP protocol version in the log.
    

### 4. Ranges in Regex

**Goal:** Find requests between 1:20 PM and 1:30 PM (13:20 to 13:29). **Command:** `grep -E ":13:2[0-9]:" access.log`

- `[0-9]`: A character class. Matches exactly one digit from 0 to 9. Combined with `2`, it matches `20` through `29`.