### Text Processing

### `grep` (Search)

Searches for text patterns using Regular Expressions.

- `grep -E "^192\." access.log` : Uses Extended Regex to find lines starting (`^`) with `192.`.
    
- `grep -P "^\d+\.\d+\.\d*1\d*\." access.log` : Uses Perl regex (`\d` for digits) to find IPs with a 1 in the 3rd octet.
    
- _Common pipe:_ `| wc -l` (Counts the number of matched lines).
    

### `sed` (Stream Editor)

Used primarily for finding and replacing strings inline. Syntax: `s/find/replace/flags`.

- `sed 's/<[^>]*>//g' sedfile.md` : Removes HTML tags globally (`g`).
    
- `sed -e 's/<ul>//g' -e 's/<html>//g'` : Chains multiple (`-e`) replacements in one command.
    

### `awk` (Data Extraction)

Used for scanning and processing tabular data/columns (columns are represented as `$1`, `$2`, etc.).

- `awk '$1 ~ /^2024-02/' sales.txt` : Prints lines where column 1 matches the regex starting with "2024-02".
    
- `awk '$5 >= 100 {print $2}' sales.txt` : Conditional matching.
    
- `awk '$3 == "Kitchen" {sum += $6} END {print sum}'` : Advanced math; adds up column 6 if column 3 is "Kitchen".