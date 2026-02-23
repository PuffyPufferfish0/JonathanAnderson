# `sed` (Stream Editor)

`sed` is primarily used for finding and replacing text on the fly. It reads line by line, applies a rule, and outputs the result.

## The Substitution Syntax

The core command for replacing text is `s/old/new/flags`.

- `s`: Substitute command.
    
- `/`: Delimiter (you can actually use other characters like `|` or `#` if your text has a lot of slashes in it).
    
- `g`: Global flag. Replaces _all_ occurrences on a line, not just the first one.
    

## Advanced Flags

- **`-e`**: Expression. Allows you to chain multiple commands together.
    
- **`-i`**: In-place. Modifies the file directly instead of printing to the terminal. _(Warning: Test without `-i` first!)_
    

## Lab 05 Breakdown (HTML to Markdown)

### 1. Stripping Tags with Wildcards

**Command:** `sed 's/<\/.*>//g' sedfile.md`

- `<\/`: Matches the literal string `</` (the `/` must be escaped with `\` so `sed` doesn't think it's the end of the substitution).
    
- `.*`: The ultimate wildcard. `.` means any character, `*` means zero or more times.
    
- `>`: Matches the closing bracket.
    
- _Result: Strips closing HTML tags._
    

### 2. Handling POSIX Character Classes (Whitespace)

**Command:** `sed 's/[[:space:]]*<li>/- /g' sedfile.md`

- `[[:space:]]*`: A special POSIX class that matches any whitespace (spaces, tabs) zero or more times.
    
- _Result:_ Finds `<li>` tags, even if they are indented, and replaces them with a Markdown list hyphen `-` .
    

### 3. Basic Replacements

- **H1 to Markdown:** `sed 's/<h1>/# /g' sedfile.md`
    
- **H2 to Markdown:** `sed 's/<h2>/## /g' sedfile.md`
    
- **Word Swap:** `sed 's/Batches/Matches/g' sedfile.md`
    

### 4. Chaining Commands (`-e`)

To run a massive cleanup in one pass, chain them with `-e`:

```
sed -e 's/<\/.*>//g' -e 's/[[:space:]]*<li>/- /g' -e 's/<h1>/# /g' sedfile.md
```