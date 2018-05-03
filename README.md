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
cd .\COMP2322-Proj\resumable-java-version\out\production\resumable-java-version\team1\downloader
java team1.downloader.AppMain "<url>" <path>
```

<s>For Build version (will update later), type the following command in a command prompt:</s>  

<s>`./JDown "<url>" <path>`</s>

### Just copy this for testing - downloading node.js .msi package

- Known issues: RenameFile does not work in root directory

```
java team1.downloader.AppMain "https://nodejs.org/dist/v8.11.1/node-v8.11.1-x64.msi" D:\any-path
```