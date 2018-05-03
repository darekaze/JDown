package team1.downloader.misc.exceptions;

public class InvalidCommandException extends Throwable{
    public InvalidCommandException(String message) {
        super("Cannot parse arguments: " + message);
    }
}
