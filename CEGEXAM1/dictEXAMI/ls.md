# `ls` (List)

Lists the files and directories in the current directory (or a specified directory).

### Important Flags

- `-l` : Long format (shows permissions, owner, size, time).
    
- `-t` : Sort by modification time.
    
- `-d` : List information about the directory itself, not its contents.
    

### Examples

- `ls /home/puffy/DirA` : List contents of a specific directory.
    
- `ls -lt ~ | head` : List 10 most recently modified files in the home directory.
    
- `ls -ld share` : View the ownership and permissions of the `share` directory.