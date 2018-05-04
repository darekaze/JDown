package team1.downloader;

import team1.downloader.misc.exceptions.InvalidCommandException;
import team1.downloader.misc.parsers.CommandParser;
import team1.downloader.misc.parsers.RenameParser;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class AppMain {
    public static void main(String[] args) {
        ProtocolHttp protocol = new ProtocolHttp();
        ProcessController controller = new ProcessController();
        RenameParser re = new RenameParser();

        protocol.subscribe(new ProcessBar());
        try {
            Entries entries = new CommandParser().parse(args);
            System.out.println("\nDownloading from " + entries.getUrl());
            System.out.println("\n[" + ProcessController.getPause() + "]+[Enter]-> Pause; "
                            + "[" + ProcessController.getResume()+ "]+[Enter]-> Resume;");

            ExecutorService executor = Executors.newCachedThreadPool();
            Downloader downloader = new Downloader(protocol, executor);
            Task task = new Task(entries.getUrl(), entries.getLocation());

            re.setFileLocation(task.getFile());
            downloader.download(task);
            controller.listenForCommands(task, downloader, () -> {
                if (!executor.isShutdown()) executor.shutdownNow();
            });
            re.renameFile();
        } catch (InvalidCommandException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
