# Octal Notation

In Linux, **Octal Notation** is a base-8 number system used as a quick, numeric shorthand for setting file permissions with the `chmod` command. It represents the 3-bit binary states of Read, Write, and Execute.

### The Value System

Each permission type is assigned a specific numeric value:

- **Read (`r`)** = 4
    
- **Write (`w`)** = 2
    
- **Execute (`x`)** = 1
    
- **None (`-`)** = 0
    

### How to Calculate

To get the octal number, you simply add the values together for each class of users: **Owner (User)**, **Group**, and **Others**.

**Example 1: The Standard Script (`chmod 755`)**

- **Owner (7)**: Read (4) + Write (2) + Execute (1) = **7** (`rwx`)
    
- **Group (5)**: Read (4) + Execute (1) = **5** (`r-x`)
    
- **Others (5)**: Read (4) + Execute (1) = **5** (`r-x`)
    
- _Result: `rwxr-xr-x`_
    

**Example 2: Lab 02 Assessment (`chmod 751`)**

- **Owner (7)**: 4 + 2 + 1 = **7** (`rwx`)
    
- **Group (5)**: 4 + 1 = **5** (`r-x`)
    
- **Others (1)**: 1 = **1** (`--x`)
    
- _Result: `rwxr-x--x`_
    
- _Use Case (from Lab 02):_ Others can execute the program but cannot read its contents, which is good for security but might make debugging harder for them.
    

**Example 3: Full Open Access (`chmod 777`)**

- **Owner (7)**: 4+2+1 = **7** (`rwx`)
    
- **Group (7)**: 4+2+1 = **7** (`rwx`)
    
- **Others (7)**: 4+2+1 = **7** (`rwx`)
    
- _Result: `rwxrwxrwx`_ (Generally considered dangerous on a shared server).
    

### Octals in `umask` Math

Octals are also used in `umask`, which subtracts permissions from the default system maximums when a new file or directory is created.

- **Default Directory**: 777
    
- **Default File**: 666 (files don't get execute by default)
    
- **Example**: If `umask` is `022`:
    
    - New directories will be `777 - 022 = 755`
        
    - New files will be `666 - 022 = 644`