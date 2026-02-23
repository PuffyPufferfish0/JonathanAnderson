### Shebang (`#!`)

The character sequence `#!` at the absolute beginning of a script.

It tells the operating system which interpreter should be used to parse and execute the rest of the file. Without it, the system might not know how to run your code, especially if it relies on a specific shell's features.

### Common Examples

- `#!/bin/bash` : Standard Bash script (Recommended for CEG 2350 labs).
    
- `#!/bin/sh` : Standard shell (often linked to `dash` or a lighter shell, might lack some advanced `bash` features).
    
- `#!/usr/bin/env python3` : Python script.
    

### Usage

- Must be line 1, character 1.
    
- Requires the file to be executable (`chmod +x` or `chmod 755`) if you want to run it directly as `./script.sh`.