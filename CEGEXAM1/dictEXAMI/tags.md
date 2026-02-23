title: CEG 2350 - Index and Flags Reference course: CEG 2350 type: reference tags:

- #ceg2350
    
- #index
    
- #flags
    

# Master Link Index

_Click any link below to jump to the corresponding section or file in your vault._

### Core Concepts

- [[Command Line Navigation]]
    
- [[File Permissions]]
    
- [[SSH]]
    
- [[Git]]
    
- [[Bash Scripting]]
    
- [[IO Redirection]]
    
- [[Text Processing]]
    
- [[Shebang]]
    

### Commands

- [[man]]
    
- [[pwd]]
    
- [[ls]]
    
- [[cd]]
    
- [[mkdir]]
    
- [[touch]]
    
- [[cp]]
    
- [[mv]]
    
- [[rm]]
    
- [[vim]]
    

# Application Flags Dictionary

_A comprehensive list of command line flags used throughout the course labs, sorted by application._

### `chmod` (Change Mode)

- **`-R`**: Recursive. Applies the permission change to the directory and everything inside it. _(Note: use with caution on shared directories!)_
    

### `grep` (Global Regular Expression Print)

- **`-E`**: Extended regular expressions. Allows the use of advanced regex syntax without escaping everything (e.g., `grep -E "^192\."`).
    
- **`-P`**: Perl-compatible regular expressions. Allows the use of shortcuts like `\d` for digits.
    
- **`-i`**: Ignore case (case-insensitive search).
    
- **`-v`**: Invert match (shows lines that _do not_ match the pattern).
    
- **`-r`**: Recursive search through directories.
    

### `ls` (List)

- **`-l`**: Long format. Shows permissions, ownership, size, and modification time.
    
- **`-t`**: Sort by time. Puts the most recently modified files at the top.
    
- **`-d`**: Directory. Lists information about the directory itself, rather than its contents (e.g., `ls -ld share`).
    

### `rm` (Remove)

- **`-r`**: Recursive. Required to delete directories and the files inside them.
    

### `sed` (Stream Editor)

- **`-e`**: Expression. Allows you to chain multiple sed operations in a single command (e.g., `sed -e 's/<ul>//g' -e 's/<html>//g'`).
    
- **`-i`**: In-place. Saves the changes directly to the file rather than just outputting them to the terminal.
    

### `sort`

- **`-n`**: Numeric sort. Sorts data by numerical value rather than alphabetical order.
    

### `ssh` (Secure Shell)

- **`-i`**: Identity file. Specifies the path to the private key used for authentication (e.g., `ssh -i /home/puffy/labsuser.pem ubuntu@...`).
    

### `ssh-keygen`

- **`-t`**: Type. Specifies the type of encryption to use when generating the key (e.g., `-t ed25519`).
    
- **`-C`**: Comment. Appends a comment to the end of the key, usually an email address to identify who owns it.
    

### `usermod` (User Modification)

- **`-a`**: Append. Used to add a user to a supplementary group without removing them from their other groups.
    
- **`-G`**: Group. Specifies the group to append the user to (usually combined as `-aG`).
    

### `wc` (Word Count)

- **`-l`**: Lines. Counts the number of lines instead of words or characters (often used by piping `grep` output into `wc -l`).