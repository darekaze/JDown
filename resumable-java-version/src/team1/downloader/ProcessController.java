package team1.downloader;

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
                        System.out.print("\033[2K" + "---Paused--- "); // Not for cmd
                    } else if (action == RESUME && downloader.isPaused(task)) {
                        downloader.resume(task);
                        deleteLine(2); // Not for cmd
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

    /* Delete console line does not work with windows cmd or PowerShell.
       Recommend to use bash or zsh for better visual effect.
       If use cmd, please comment the 'Not for cmd' lines in listenForCommands() to avoid bad visual outputs. */
    private static void deleteLine(int count) {
        System.out.print("\033[2K");
        for (int i = 0; i < count; i++) {
            System.out.print(String.format("\033[%dA", 1));
            System.out.print("\033[2K");
        }
    }

    public static char getPause(){
        return PAUSE;
    }

    public static char getResume() {
        return RESUME;
    }
}
