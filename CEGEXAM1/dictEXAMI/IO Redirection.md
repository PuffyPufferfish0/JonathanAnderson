### IO Redirection

Linux handles input and output as "streams": Standard Input (`stdin` = 0), Standard Output (`stdout` = 1), and Standard Error (`stderr` = 2). You can redirect these streams.

### Redirection Operators

- `>` : Overwrite output to a file.
    
    - _Example:_ `printenv HOME > thishouse`
        
- `>>` : Append output to the end of a file.
    
    - _Example:_ `cat nums.txt | sort -n >> all_nums.txt`
        
- `2>>` : Redirect and append Standard Error (`stderr`).
    
    - _Example:_ `cat doesnotexist 2>> hush.txt` (Hides the error from the screen and puts it in the file).
        
- `<<` (Here Document): Pass a multi-line block of text to a command until a delimiter is reached.
    
    - _Example:_ `cat << "DONE" > here.txt`
        

### Pipes (`|`)

Takes the `stdout` of the command on the left and passes it as `stdin` to the command on the right.

- _Example:_ `ls -lt ~ | head` (Lists files, sorts by time, passes the output to `head` to only show the top 10).
    
- _Example:_ `history | grep ".md"` (Takes full command history and filters for lines containing ".md").