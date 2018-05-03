package team1.downloader;

public class Entries {
    private final String url;
    private final String location;

    public Entries(String url, String location) {

        this.url = url;
        this.location = location;
    }

    public String getUrl() {
        return url;
    }

    public String getLocation() {
        return location;
    }
}
