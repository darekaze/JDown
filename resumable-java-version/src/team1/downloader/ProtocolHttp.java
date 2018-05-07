package team1.downloader;

import team1.downloader.misc.exceptions.InvalidUrlException;
import team1.downloader.misc.exceptions.UnexpectedResponseException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ProtocolHttp implements Protocol {
    private static final int BUFFER_SIZE = 4096;
    private final List<Processor> processors = new ArrayList<>();

    @Override
    public void download(Task task) throws IOException {
        try {
            downloadFromUrl(task, task.getUrl());
        } catch (UnexpectedResponseException e) {
            if (e.getResponseCode() == 302 || e.getResponseCode() == 301) {
                try {
                    downloadFromUrl(task, e.getLocation());
                } catch (UnexpectedResponseException e1) {
                    onError(task, "Unable to download the file from: " + e.getLocation());
                }
            } else {
                onError(task, "Error in downloading the file. Response code: " + e.getResponseCode());
            }
        }
    }

    private void onError(Task task, String message) {
        System.out.println(message);
        task.getStatus().failed();
    }

    private void downloadFromUrl(Task task, String url) throws UnexpectedResponseException, IOException {
        HttpURLConnection connection = null;
        FileOutputStream outputStream = null;

        try {
            connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setReadTimeout(10000); // 10 sec
            long totalBytesRead = task.getFile().length();
            connection.setRequestProperty("Range", getRangeHeader(totalBytesRead));
            connection.setRequestProperty("Group", "1");
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_PARTIAL) {
                long totalSize = totalBytesRead + connection.getContentLengthLong();
                InputStream inputStream = connection.getInputStream();
                outputStream = new FileOutputStream(task.getFile(), true);
                byte[] buffer = new byte[BUFFER_SIZE];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) > -1 && !Thread.currentThread().isInterrupted()) {
                    outputStream.write(buffer, 0, bytesRead);
                    outputStream.flush();
                    totalBytesRead += bytesRead;
                    this.notifyProgress(task, totalBytesRead, totalSize);
                }
            } else {
                throw new UnexpectedResponseException(responseCode, connection.getHeaderField("Location"));
            }
        } catch (MalformedURLException e) {
            throw new InvalidUrlException(task.getUrl());
        } finally {
            if (connection != null)
                connection.disconnect();
            if (outputStream != null) try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String getRangeHeader(long offset) {
        return "bytes=" + offset + "-";
    }

    private void notifyProgress(Task task, long sizeDownloaded, long totalSize) {
        task.updateStatus(new TaskStatus(totalSize, sizeDownloaded));
        this.processors.forEach(processor -> processor.onProgress(task));
    }

    public void subscribe(Processor processor) {
        this.processors.add(processor);
    }
}
