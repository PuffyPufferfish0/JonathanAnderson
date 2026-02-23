# `regex` (Regular Expressions)

Regular Expressions (regex) are a sequence of characters that specify a search pattern. In CEG 2350, you use regex heavily in conjunction with tools like [[grep]], [[sed]], and [[awk]] to find, filter, and replace text.

## BRE vs. ERE (Basic vs. Extended)

By default, standard commands (like plain `grep` or `sed`) use Basic Regular Expressions (BRE). To use advanced features without heavily escaping characters, you use Extended Regular Expressions (ERE), usually triggered with a flag like `-E` (e.g., `grep -E`).

- **BRE (Basic)**: Requires escaping for groups and quantifiers: `\+`, `\?`, `\|`, `\(`
    
- **ERE (Extended)**: Uses them natively: `+`, `?`, `|`, `()`
    

## 1. Core Syntax & Anchors (Positional)

Anchors do not match characters; they match _positions_ within the text.

- **`^`** (Caret): Matches the **start** of a line.
    
    - _Example:_ `^192` matches lines beginning with "192".
        
- **`$`** (Dollar): Matches the **end** of a line.
    
    - _Example:_ `end$` matches lines ending with "end".
        
- **`.`** (Dot): Wildcard. Matches **any single character** (except a newline).
    
- **`\`** (Escape): Escapes a special character to treat it literally.
    
    - _Example:_ `\.` matches an actual period, rather than "any character".
        
- **`\b`** or **`\< \>`**: Word boundary. Matches the position between a word character and a non-word character.
    
    - _Example:_ `\bcat\b` matches "cat" but not "category".
        
- **`\B`**: Non-word boundary. Matches where `\b` does not.
    
    - _Example:_ `\Bcat\B` matches "cat" inside "locator", but not the standalone word "cat".
        

## 2. Quantifiers (Multipliers)

Quantifiers dictate how many times the preceding character/group should appear. By default, quantifiers are **greedy** (they match as much text as possible).

- **`*`** (Asterisk): Matches **zero or more** times.
    
    - _Example:_ `.*` is the ultimate wildcard (any character, any number of times).
        
- **`+`** (Plus): Matches **one or more** times. (Requires ERE `-E` or Perl `-P`).
    
- **`?`** (Question): Matches **zero or one** time (makes the preceding character optional).
    
- **`{n}`**: Matches exactly _n_ times.
    
- **`{n,}`**: Matches _n_ or more times.
    
- **`{n,m}`**: Matches between _n_ and _m_ times.
    

> [!warning] Greedy vs. Lazy (PCRE / `-P` only) By appending a `?` to a quantifier, you make it **lazy** (it matches as _little_ text as possible). _Example:_ Given the text `<div>Hello</div>`, `<.*>` (greedy) matches the whole string. `<.*?>` (lazy) matches just `<div>`.

## 3. Character Classes & Sets

Brackets match any ONE character contained within them.

- **`[abc]`**: Matches any ONE of the characters inside the brackets (a, b, or c).
    
- **`[0-9]`**: Matches any single digit from 0 to 9.
    
- **`[a-zA-Z]`**: Matches any single upper or lower case letter.
    
- **`[^abc]`**: The caret inside brackets means **NOT**. Matches anything _except_ a, b, or c.
    

### POSIX Bracket Expressions

Standardized named sets, highly useful in `grep` and `sed`. Must be used inside another set of brackets (e.g., `[[:space:]]`).

- **`[[:space:]]`**: Any whitespace (spaces, tabs, newlines).
    
- **`[[:alnum:]]`**: Alphanumeric characters (letters and digits).
    
- **`[[:alpha:]]`**: Alphabetic characters (letters only).
    
- **`[[:digit:]]`**: Digits (equivalent to `[0-9]`).
    
- **`[[:lower:]]`** / **`[[:upper:]]`**: Lowercase / Uppercase letters.
    
- **`[[:punct:]]`**: Punctuation characters (e.g., `! " # $ % & ' ( ) * + , - . / : ; < = > ? @ [ \ ] ^ _ { | } ~`).
    

## 4. Perl-Compatible Shortcuts (Requires `grep -P`)

If you use the `-P` flag in grep, you unlock Perl-compatible regular expressions (PCRE), which include helpful shortcuts and their capitalized negations.

- **`\d`**: Matches any digit (equivalent to `[0-9]`).
    
- **`\D`**: Matches any NON-digit (equivalent to `[^0-9]`).
    
- **`\s`**: Matches any whitespace character.
    
- **`\S`**: Matches any NON-whitespace character.
    
- **`\w`**: Matches any word character (letters, digits, underscores).
    
- **`\W`**: Matches any NON-word character (punctuation, spaces, etc.).
    

## 5. Grouping, Alternation, and Backreferences

- **`(...)`** (Capture Group): Groups multiple tokens together.
    
- **`|`** (Alternation / OR): Matches the pattern on the left OR the right.
    
    - _Example:_ `(cat|dog)` matches either "cat" or "dog".
        

### Backreferences (Crucial for `sed`)

When you use a Capture Group `(...)`, the engine saves what it matched. You can refer back to it later using `\1`, `\2`, etc.

- _Example matching duplicate words:_ `\b(\w+)\s+\1\b` matches "hello hello".
    
- _Example in `sed` (Swapping columns):_ `sed -E 's/([^,]+),([^,]+)/\2,\1/'` swaps text before and after a comma.
    

## 6. Lookarounds (Advanced PCRE / `grep -P`)

Lookarounds let you assert that a string is followed or preceded by another pattern, _without_ actually including that pattern in the final match result.

- **`(?=...)`** (Positive Lookahead): Matches if followed by the pattern.
    
    - _Example:_ `foo(?=bar)` matches "foo" only if followed by "bar".
        
- **`(?!...)`** (Negative Lookahead): Matches if NOT followed by the pattern.
    
    - _Example:_ `foo(?!bar)` matches "foo" only if NOT followed by "bar".
        
- **`(?<=...)`** (Positive Lookbehind): Matches if preceded by the pattern.
    
- **`(?<!...)`** (Negative Lookbehind): Matches if NOT preceded by the pattern.
    

## Lab 05 Breakdown (Regex in Action)

### 1. Anchors and Escaping (grep)

**Goal:** Find IPs starting with 192. **Regex:** `^192\.`

- `^` forces the match to the start of the line. `192` matches the literal numbers. `\.` ensures a literal dot follows the 192.
    

### 2. Complex Perl Matching (grep -P)

**Goal:** Find logs with a `1` in the third octet of the IP. **Regex:** `^\d+\.\d+\.\d*1\d*\.`

- `^\d+\.` : Start of line, one or more digits, followed by a literal dot (1st octet).
    
- `\d+\.` : One or more digits, literal dot (2nd octet).
    
- `\d*1\d*\.` : Any amount of digits, a `1`, any amount of digits, literal dot (3rd octet).
    

### 3. Using Wildcards to Strip HTML (sed)

**Goal:** Remove HTML tags from a document. **Regex:** `<\/[^>]*>` or `<\/.*>`

- `<` and `>` match the literal brackets. `\/` matches the closing slash. `.*` greedily matches everything inside the brackets to strip the tag globally.