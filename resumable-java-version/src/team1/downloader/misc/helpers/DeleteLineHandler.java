package team1.downloader.misc.helpers;

public class DeleteLineHandler {

    /* Delete console line does not work with windows cmd or PowerShell.
       Recommend to use bash or zsh for better visual effect.
       If use cmd, please comment the 'Not for cmd' lines in listenForCommands() to avoid bad visual outputs. */
    public static void deleteLine(int num) {
        System.out.print("\033[2K");
        for (int i = 0; i < num; i++) {
            System.out.print(String.format("\033[%dA", 1));
            System.out.print("\033[2K");
        }
    }
}
