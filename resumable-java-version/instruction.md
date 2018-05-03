# Instruction

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
```
./JDown "<url>" <path>
```

### Below is a short test for downloading node.js .msi package

- Please create the directory "D:\test-path" yourself, since I didn't implement auto directory creation.

```
java team1.downloader.AppMain "https://nodejs.org/dist/v8.11.1/node-v8.11.1-x64.msi" D:\test-path
```