package team1.downloader;

import java.io.IOException;

interface Protocol {
    void download(Task task) throws IOException;
}
