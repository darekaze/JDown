package team1.downloader;

public class ProcessBar implements Processor {
    private StringBuilder messages = new StringBuilder();

    @Override
    public void onProgress(Task task) {
        if (task.getStatus().isComplete()) {
            printProgress(task);
            System.out.println("\nFile downloaded successfully! Saved at: " + task.getLocation() + "\n");
        } else {
            printProgress(task);
        }
    }

    private void printProgress(Task task) {
        TaskStatus status = task.getStatus();
        for (int i = 0; i < messages.length() + 10; i++) {
            System.out.print("\b");
        }
        messages = new StringBuilder();
        messages.append("[");
        final int processBarWidth = 20;
        for (int i = 1; i <= processBarWidth; i++) {
            if (status.getDownloadedSize() * processBarWidth / status.getTotalSize() >= i)
                messages.append("#");
            else
                messages.append(".");
        }
        messages.append("] ");
        messages.append(Math.round(status.getDownloadedSize() / 1024)).append("/").append(Math.round(status.getTotalSize() / 1024)).append(" KB ");
        System.out.print(messages.toString());
    }
}
