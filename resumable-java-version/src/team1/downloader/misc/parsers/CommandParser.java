package team1.downloader.misc.parsers;

import team1.downloader.Entries;
import team1.downloader.misc.exceptions.InvalidCommandException;

public class CommandParser {
    private static final String DEFAULT_LOCATION = ".";

    public Entries parse(String[] args) throws InvalidCommandException {
        if (args.length == 0) {
            throw new InvalidCommandException("URL Missing from arguments");
        }

        String url = args[0].trim();
        String location = DEFAULT_LOCATION;
        if (args.length > 1) {
            location = args[1].trim();
        }
        return new Entries(url, location);
    }
}
