

> [!info] This is a comprehensive dictionary I made from Labs 01-05 covering [[Command Line Navigation]], [[File Permissions]], [[SSH]], [[Git]], [[Bash Scripting]], and [[Text Processing]].

## 1. [[Command Line Navigation]]

Basic file and directory manipulation commands in Linux. 

- **[[man]]**: Shows a list of available commands. (Equivalent to `help` in Windows).
    
- **[[pwd]]**: Shows the directory you are currently in.
    
- **[[ls]]**: Lists whatever is in the directory you are in.
    
- **[[cd]]**: Changes the directory you are in. (`cd ~` returns to home).
    
- **[[mkdir]]**: Makes a new directory. (Use quotes for names with spaces, e.g., `mkdir "Dir B"`).
    
- **[[touch]]**: Makes a new blank file, or updates the timestamp/opens the file if it exists.
    
- **[[cp]]**: Copies a source to a directory, or multiple sources.
    
- **[[mv]]**: Moves an item to a new directory (also used to rename files).
    
- **[[rm]]**: Removes a file. Use `rm -r` to recursively remove directories and their contents.
    
- **[[vim]]**: Opens a CLI text editor directly into the terminal. (Press `Esc` then `:wq` to save and quit).
    

## 2. [[File Permissions]], Users, and Groups

Managing who has access to what, using symbolic and octal notation. 
### Permissions (`chmod`)

- **`chmod u+r [file]`**: Sets the user (owner) to read permission. Quick way to give yourself access.
    
- **`chmod u=rw,g-w,o-x [file]`**: Sets user to read/write, removes write from the group, and removes execute from others.
    
- **`chmod a=w [file]`**: Gives everyone (all) write access.
    
- **`chmod 751 [file]`**: Uses **Octal Notation**. Sets Owner to `rwx` (7), Group to `r-x` (5), and Others to `--x` (1).
    
- **`chmod 755 [file]`**: Standard executable permission for scripts (rwx for owner, rx for everyone else).
see [[octals]]

> [!warning] Recursive Permissions `chmod -R ug+w [dir]` recursively gives users and groups write access to a directory and everything inside it. As noted in Lab 02, doing this on a shared server gives anyone recursive write access, which is a security risk.

### User & Group Management

- **`sudo adduser [user]`**: Creates a new user.
    
- **`sudo addgroup [group]`**: Creates a new group.
    
- **`sudo usermod -aG [group] [user]`**: Appends (`-a`) a user to a group (`-G`). _Crucial for giving a user `sudo` privileges (e.g., `sudo usermod -aG sudo puffy`)._
    
- **`sudo chown :[group] [dir]`**: Modifies a directory to have group ownership.
    
- **`sudo su - [user]`**: Switches the current terminal session to another user. Use `exit` to return.
    

## 3. [[SSH]] & [[Git]] Version Control

Connecting to remote servers and managing code repositories. 

### SSH (Secure Shell)

- **`ssh-keygen -t ed25519 -C "[email]"`**: Generates a new SSH key using ed25519 encryption.
    
- **`cat ~/.ssh/id_ed25519.pub`**: Reads the text of the **public key**, which is what you copy and paste into GitHub settings.
    
- **`ssh -i [key_path] [user]@[ip]`**: Connects to an AWS instance or remote server using a specific private key (e.g., `labsuser.pem`).
    

### Git Workflow

- **`git clone [URI]`**: Downloads a repository to your PC via SSH or HTTPS.
    
- **`git status`**: Checks what files have been modified in your working directory.
    
- **`git add [filename]`**: Adds a new file to the repository's staging area.
    
- **`git commit -m "[msg]"`**: Saves changes locally with a descriptive message.
    
- **`git push`**: Saves local commits to the cloud remote repository.
    
- **`git pull`**: Downloads the remote repository and merges it with your local version.
    

## 4. [[Bash Scripting]], Links & [[IO Redirection]]

Automating tasks and managing data streams. 
### IO Redirection & Pipes

- **`>`**: Overwrites file with standard output (`stdout`). (e.g., `printenv HOME > thishouse`)
    
- **`>>`**: Appends output to the end of a file. (e.g., `cat nums.txt | sort -n >> all_nums.txt`)
    
- **`2>>`**: Appends error output (`stderr`) to a file. (e.g., `cat doesnotexist 2>> hush.txt`)
    
- **`|` (Pipe)**: Takes the output of one command and sends it to the input of another. (e.g., `history | grep ".md"`)
    
- **`<< "DONE"`**: A "Here Document". Allows you to type multiple lines of commands until the delimiter (`DONE`) is reached.
    

### Links & Script Execution

- **`ln -s [target] [destination]`**: Creates a **symbolic link**. If you update the original file, the link updates too.
    
- **[[Shebang]]**: `#!/bin/bash` at the top of a script tells the OS which interpreter to use.
    

> [!tip] The `$PATH` Variable Placing a symbolic link of an executable script in a `$PATH` directory (like `~/ubuntu/.bin/` or `/usr/local/bin/`) allows you to run your script (like your `ft` finance tracker) from anywhere in the filesystem.

## 5. [[Text Processing]] (grep, sed, awk)

Searching, replacing, and parsing data from text files. 
### `grep` (Search)

- **`grep -E`**: Uses extended regular expressions. (e.g., `grep -E "^192\." access.log`)
    
- **`grep -P`**: Uses Perl-compatible regular expressions (e.g., allows `\d` for digits).
    
- **Regex Anchors**: `^` matches the start of a line. `\d+` matches one or more digits.
    
- **`wc -l`**: Word count (lines). Often piped after `grep` to count the number of matched logs.
    
[[grep]]

### `sed` (Stream Editor - Replace)

- **`sed 's/[old]/[new]/g' [file]`**: Global substitution (`g`) to replace strings.
    
- **`sed 's/<[^>]*>//g' [file]`**: Removes HTML tags using regex.
    
- **`sed -e`**: Allows you to string multiple operations together in one pass. (e.g., `sed -e 's/<ul>//g' -e 's/<html>//g'`)
    
[[sed]]

### `awk` (Pattern Scanning & Columns)

- **`awk '$1 ~ /^2024/'`**: Matches lines where the 1st column (`$1`) starts with "2024".
    
- **`awk '$5 >= 100 {print $2}'`**: Conditionals. Prints the 2nd column if the 5th column is >= 100.
    
- **`awk '$3 == "Kitchen" {sum += $6} END {print sum}'`**: Variables and Math. Finds lines where column 3 is "Kitchen", adds column 6 to a `sum` variable, and prints the total at the end.
    
- **`gsub(/old/, "new")`**: Used inside `awk` to globally substitute text before printing. (e.g., `awk '{gsub(/Sofa/, "Couch"); print}'`)
[[awk]]
