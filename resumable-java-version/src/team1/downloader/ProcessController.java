package team1.downloader;

import team1.downloader.misc.helpers.DeleteLineHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class ProcessController {
    private static final char PAUSE = 'p';
    private static final char RESUME = 'r';

    public void listenForCommands(Task task, Downloader downloader, Runnable onComplete) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            char[] buffer = new char[1];

            while (!(task.getStatus().isComplete() || task.getStatus().hasFailed())) {
                if (reader.ready() && reader.read(buffer) > 0) {
                    char action = buffer[0];
                    if (action == PAUSE && !downloader.isPaused(task)) {
                        downloader.pause(task);
                        System.out.print("\033[2K" + "---Paused--- "); // Not for windows cmd
                    } else if (action == RESUME && downloader.isPaused(task)) {
                        downloader.resume(task);
                        DeleteLineHandler.deleteLine(2); // Not for windows cmd
                    }
                }
            }
            reader.close();
            onComplete.run();
            if(task.getStatus().hasFailed()) {
                System.exit(1);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static char getPause(){
        return PAUSE;
    }

    public static char getResume() {
        return RESUME;
    }
}
