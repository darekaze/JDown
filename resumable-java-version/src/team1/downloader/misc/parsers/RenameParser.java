package team1.downloader.misc.parsers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class RenameParser {
    private File file;

    public void renameFile() {
        String formattedName;
        formattedName = this.file.getName().replace(".tmp", "").trim();
        Path source = this.file.toPath();
        try {
            Files.move(source, source.resolveSibling(formattedName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setFileLocation(File f) {
        this.file = f;
    }
}
