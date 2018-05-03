# COMP2322 Project - Resumable HTTP Downloader

## Python version

WiP

## Java version Instruction (JDown)

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

<s>`./JDown "<url>" <path>`</s>

### Use this for testing - downloading node.js package

```
java team1.downloader.AppMain "https://nodejs.org/dist/v8.11.1/node-v8.11.1-linux-x64.tar.xz" /home/darekaze/Downloads
```

#### Known issues:

1. RenameFile does not work in root directory.
2. <s>RenameFile does not rename newly downloaded file in Win 10 (IntelliJ and Linux don't have this issue). However, it can rename and overwrite if the fileName existed.</s> \[***Solved by adding compatability***]

Use Linux tks XD >> <s>still F\**k Windows</s>

#### Things can consider improve:

1. Make the controller key to Key Pressed Listener (if possible)
2. Build it with grandle (instead of .jar)