package team1.downloader;

import team1.downloader.misc.parsers.FileNameParser;

import java.io.File;
import java.util.Optional;

class Task {
    private static final String DEFAULT_FILE_NAME = "download";
    private final String url;
    private String location;
    private final File file;
    private TaskStatus status = new TaskStatus(0, 0);

    Task(String url, String location) {
        this.url = url;
        this.location = location;
        if (new File(location).isDirectory()) {
            this.location = location + File.separator + getFileName(url);
        } else if(new File(location).mkdir()){
            this.location = location + File.separator + getFileName(url);
        }
        this.file = new File(this.location + ".tmp");
    }

    private String getFileName(String url) {
        Optional<String> fileName = FileNameParser.getFileName(url);
        return fileName.orElse(DEFAULT_FILE_NAME);
    }

    public String getUrl() {
        return url;
    }

    public File getFile() {
        return file;
    }

    public String getLocation() {
        return location;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void updateStatus(TaskStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Task that = (Task) obj;
        return url.equals(that.url) && location.equals(that.location);
    }

    @Override
    public int hashCode() {
        int result = url.hashCode();
        result = 31 * result + location.hashCode();
        return result;
    }
}
