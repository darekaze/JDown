package team1.downloader.misc.helpers;

import team1.downloader.misc.exceptions.InvalidUrlException;

import java.net.URI;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileNameParser {
    private static final String URL_SEPARATOR = "/";

    public static Optional<String> getFileName(String urlString) {
        try {
            String path = new URI(urlString).getPath();
            int liSeparator = path.lastIndexOf(URL_SEPARATOR);
            String fileName = liSeparator >= 0
                    ? path.substring(liSeparator).replace(URL_SEPARATOR, "").trim()
                    : "";

            if (fileName.length() == 0)
                return Optional.empty();
            return Optional.of(fileName);
        } catch (Exception e) {
            Logger.getLogger(FileNameParser.class.getName()).log(Level.SEVERE, e.getMessage());
            throw new InvalidUrlException(urlString);
        }
    }
}
