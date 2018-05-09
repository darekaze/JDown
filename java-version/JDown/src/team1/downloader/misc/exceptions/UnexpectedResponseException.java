package team1.downloader.misc.exceptions;

public class UnexpectedResponseException extends Throwable{
    private final String location;
    private final int responseCode;

    public UnexpectedResponseException(int responseCode, String location) {
        super("Cannot connect. Status code : " + responseCode);
        this.responseCode = responseCode;
        this.location = location;
    }

    public int getResponseCode(){
        return this.responseCode;
    }

    public String getLocation() {
        return location;
    }
}
