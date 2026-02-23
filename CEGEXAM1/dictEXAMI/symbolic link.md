# Symbolic Link (Symlink)

A symbolic link is a special type of file that serves as a reference or "shortcut" to another file or directory.

Unlike a **hard link** (which points directly to the data on the hard drive), a symbolic link points to the _path_ of the original file. As you noted in Lab 04: _"If I use a symbolic, then I will be able to update it from the original file,"_ meaning changes made to the original script automatically reflect when you execute the link.

### Essential Command (`ln`)

To create a symbolic link, use the `ln` command with the `-s` flag.

- **Syntax:** `ln -s [absolute_path_to_target] [absolute_path_to_link]`
    

> [!warning] Always Use Absolute Paths When creating symlinks, it is highly recommended to use absolute paths (e.g., `/home/puffy/...`) rather than relative paths (`./...`). If you move a relative symlink, it will break because the path it points to is no longer correct from its new location.

### Lab 04 Example: Adding a Script to `$PATH`

In Lab 04, you created a symbolic link for your Finance Tracker (`ft`) script and placed it in `/usr/local/bin/` so that it could be run globally from any directory without needing `./`.

**1. Make the original script executable:**

```
chmod 755 ~/ceg2350-s26-PuffyPufferfish0/lab04/ft
```

**2. Create the symbolic link in the PATH directory:**

```
sudo ln -s ~/ceg2350-s26-PuffyPufferfish0/lab04/ft /usr/local/bin/ft
```

_(Note: `sudo` is required here because standard users do not have write access to `/usr/local/bin/`)_

### How to Identify a Symlink

When you use `ls -l` (long list format), a symbolic link will show up with an `l` at the very beginning of the permission string, and an arrow `->` pointing to its target: `lrwxrwxrwx 1 root root 42 Feb 22 12:00 ft -> /home/puffy/ceg2350-s26/lab04/ft`