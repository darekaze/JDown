# Resumable HTTP Downloader

> COMP2322 Project

## Java version (JDown.jar)

This is the real automated resumable downloader written in Java. Blazing fast in downloading files.  
-- Recommended version.

### Instructions

Build this project as '.jar' and execute the following command in terminal (where you .jar file is located):

```
java -jar JDown.jar "<url>" <path>
```

#### Testing - download node.js package

```
java -jar JDown.jar "https://nodejs.org/dist/v8.11.1/node-v8.11.1-linux-x64.tar.xz" ./test-dir
```

#### Notes:

1. RenameFile does not work in Windows root directory, please download the file into a folder.
2. Windows cmd (or PowerShell) does not support ASCII Escape codes for removing the console line (Which is a Terminal standard that Windows does not followed). This is for better visual effect. Therefore, we recommend to use bash terminal to execute the program.

---

## Python version (pyDown.py)

This is the prototype version of the resumable downloader  
-- Not recommended to use (However, you can try it)

### Instructions

Simply run the pyDown.py in Python 3

```
python pyDown.py
```

Then type in the URL to start downloading your file


