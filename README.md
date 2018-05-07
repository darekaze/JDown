# COMP2322 Project - Resumable HTTP Downloader

## Python version

WiP

## Java version Instruction (team1.downloader)

In IDE (IntelliJ), You need to add the 2 arguments in the setting config.

```
"<url>" <path> 
```

You can also do this if possible:
```
cd ./COMP2322-Proj/resumable-java-version/out/production/resumable-java-version
java team1.downloader.AppMain "<url>" <path>
```

<s>For Build version (will update later), type the following command in a command prompt:</s>  

<s>`./JDown "<url>" <path>`</s> TBC with Grandle

### Use this for testing - downloading node.js package

```
java team1.downloader.AppMain "https://nodejs.org/dist/v8.11.1/node-v8.11.1-linux-x64.tar.xz" /home/darekaze/Downloads
```

#### Notes:

1. RenameFile does not work in root directory, please download the file into a folder.
2. Windows cmd (or PowerShell) does not support ASCII Escape codes for removing the console line (Which is a Terminal standard that Windows does not followed). This is for better visual effect. Therefore, we recommend to use bash terminal to execute the program.


