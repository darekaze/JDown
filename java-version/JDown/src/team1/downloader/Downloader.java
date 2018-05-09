package team1.downloader;

import team1.downloader.misc.helpers.DeleteLineHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

class Downloader {
    private final ExecutorService executor;
    private final HashMap<Task, Future> downloads = new HashMap<>();
    private final Protocol protocol;
    private static final int WAITING_TIME = 5000;

    Downloader(Protocol protocol, ExecutorService executor) {
        this.protocol = protocol;
        this.executor = executor;
    }

    public void download(Task task) {
        if (!downloads.containsKey(task))
            downloads.put(task, executor.submit(() -> {
                try {
                    protocol.download(task);
                } catch (IOException e) {
                    onError(task, e.getMessage());
                }
            }));
    }

    public void pause(Task task) {
        if (this.downloads.containsKey(task))
            this.downloads.get(task).cancel(true);
    }

    public void resume(Task task) {
        if (downloads.containsKey(task))
            downloads.put(task, executor.submit(() -> {
                try {
                    protocol.download(task);
                } catch (IOException e) {
                    onError(task, e.getMessage());
                }
            }));
    }

    public boolean isPaused(Task task) {
        return this.downloads.containsKey(task) && this.downloads.get(task).isCancelled();
    }

    private void onError(Task task, String message){
        pause(task);
        System.out.println("Download aborted: " + message);
        System.out.println("-- No internet connection: Attempt to reconnect...");
        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        DeleteLineHandler.deleteLine(2); // Not for windows cmd
                        resume(task);
                    }
                },
                WAITING_TIME
        );
    }
}
