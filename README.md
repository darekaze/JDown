# Resumable HTTP Downloader

> COMP2322 Project

## Instructions

### Python version (team1.py)

Simply run the team1.py in Python 3 to execute the program

### Java version (JDown)

Build this project as '.jar' and execute the following command in terminal (where you .jar file is located):
```
java -jar team1-downloader.jar "<url>" <path>
```

#### Testing - download node.js package

With .jar package:
```
java -jar team1-downloader.jar "https://nodejs.org/dist/v8.11.1/node-v8.11.1-linux-x64.tar.xz" ./test-dir
```

#### Notes:

1. RenameFile does not work in root directory, please download the file into a folder.
2. Windows cmd (or PowerShell) does not support ASCII Escape codes for removing the console line (Which is a Terminal standard that Windows does not followed). This is for better visual effect. Therefore, we recommend to use bash terminal to execute the program.


