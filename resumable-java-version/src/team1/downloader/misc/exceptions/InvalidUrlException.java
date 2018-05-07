package team1.downloader.misc.exceptions;

public class InvalidUrlException extends RuntimeException {
    public InvalidUrlException(String url){
        super("Unable to parse url: " + url);
    }
}
