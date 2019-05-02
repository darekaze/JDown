package team1.downloader;

public class TaskStatus {
    private final long totalSize;
    private final long downloadedSize;
    private boolean failed;

    TaskStatus(long totalSize, long downloadedSize) {
        this.totalSize = totalSize;
        this.downloadedSize = downloadedSize;
    }

    public long getTotalSize() {
        return totalSize;
    }

    public long getDownloadedSize() {
        return downloadedSize;
    }

    public boolean isComplete() {
        return getTotalSize() > 0 && getDownloadedSize() >= getTotalSize();
    }

    public boolean hasFailed() {
        return this.failed;
    }

    public void failed() {
        this.failed = true;
    }
}
