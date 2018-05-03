package team1.downloader.misc.parsers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class RenameParser {
    private File file;

    public void renameFile() {
        String formattedName = this.file.getName().replace(".tmp", "").trim();
        Path source = this.file.toPath();
        File nf = new File(this.file.getParent() + File.separator + formattedName);
        try {
            nf.createNewFile(); // For the sake of windows
            Files.move(source, source.resolveSibling(formattedName), REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setFileLocation(File f) {
        this.file = f;
    }
}
