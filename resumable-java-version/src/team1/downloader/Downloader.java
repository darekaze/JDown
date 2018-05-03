package team1.downloader;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

class Downloader {

    private final ExecutorService executor;

    private final HashMap<Task, Future> downloads = new HashMap<>();
    private final Protocol protocol;


    Downloader(Protocol protocol, ExecutorService executor) {
        this.protocol = protocol;
        this.executor = executor;
    }

    public void download(Task entry) {
        if (!downloads.containsKey(entry))
            downloads.put(entry, executor.submit(() -> {
                try {
                    protocol.download(entry);
                } catch (IOException e) {
                    onError(entry, e.getMessage());
                }
            }));
    }

    public void pause(Task entry) {
        if (this.downloads.containsKey(entry))
            this.downloads.get(entry).cancel(true);
    }

    public void resume(Task entry) {
        if (downloads.containsKey(entry))
            downloads.put(entry, executor.submit(() -> {
                try {
                    protocol.download(entry);
                } catch (IOException e) {
                    onError(entry, e.getMessage());
                }
            }));
    }

    public boolean isPaused(Task entry) {
        return this.downloads.containsKey(entry) && this.downloads.get(entry).isCancelled();
    }

    private void onError(Task entry, String message){
        System.out.println("Failed to download file. " + message);
        entry.getStatus().failed();
    }
}
