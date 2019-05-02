# Resumable HTTP Downloader

> COMP2322 Project

**NOTE: Since I no longer write any Java code, this repo will NO longer be maintained.**

An automated resumable downloader written in Java. Blazing fast in downloading files.

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

> This is the improved mirror from [here](https://github.com/CrabAss/COMP2322-Proj)
> Also give thanks to [@TCtower](https://github.com/TCtower) and [@CrabAss](https://github.com/CrabAss)
