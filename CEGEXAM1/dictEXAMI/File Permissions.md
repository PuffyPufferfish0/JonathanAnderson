Linux uses a robust permissions system dividing access into **Owner (User)**, **Group**, and **Others (All)**. Access types are **Read (r=4)**, **Write (w=2)**, and **Execute (x=1)**.

### Modifying Permissions (`chmod`)

- **Symbolic Method**: `chmod u+r file` (adds read to user), `chmod a=w file` (gives all write access).
    
- **Octal Method**: `chmod 751 file`
    
    - Owner: 7 (4+2+1 = rwx)
        
    - Group: 5 (4+1 = rx)
        
    - Others: 1 (1 = x)
        
- **Recursive**: `chmod -R ug+w share` (Applies to directory and all contents - _use with caution!_)
    

### Ownership & Groups (`chown`, `usermod`)

- `sudo chown :squad share` : Changes the group ownership of the `share` directory to the group `squad`.
    
- `sudo usermod -aG squad puffy` : Appends (`-a`) the user `puffy` to the group (`-G`) `squad`.
    
- `sudo adduser puffy` : Creates a new user.
    

### Important Notes from Labs

- Only the root user (or sudoer) can change file ownership.
    
- For executable scripts (like `ft` or `lookup.sh`), you must grant execute permissions (`chmod 755 script.sh`).