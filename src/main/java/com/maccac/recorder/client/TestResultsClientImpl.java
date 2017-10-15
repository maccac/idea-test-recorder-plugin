package com.maccac.recorder.client;

import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.diagnostic.Logger;
import com.maccac.recorder.model.TestResults;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.stream.Collectors;

public class TestResultsClientImpl implements TestResultsClient {

    private static final Logger logger = Logger.getInstance("TestResultsClientImpl");
    private static final String WRITE_METRICS_URL = "/write?db=mydb&precision=ms";

    @Override
    public void postTestResults(String serverURI, TestResults breakdown) {
        try {
            HttpURLConnection connection = postResults(serverURI, constructInfluxDbPost(breakdown));
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                logger.warn("Failed to send results to server.");
            }
        } catch (IOException e) {
            notifyOfIOError(serverURI, e);
        }
    }

    @Override
    public void testConnection(String hostUrl) {
        try {
            HttpURLConnection connection = createHttpUrlConnectionTo(new URL(hostUrl + "/ping"));
            connection.setRequestMethod("GET");
            if (connection.getResponseCode() != HttpURLConnection.HTTP_NO_CONTENT) {
                logger.warn(String.format("Ping request failed to %s.", hostUrl));
            }
        } catch (IOException e) {
            notifyOfIOError(hostUrl, e);
        }
    }

    private void notifyOfIOError(String hostUrl, IOException e) {
        logger.warn(e);
        Notification notification = new Notification("Test recorder", String.format("IOError communicating with %s.", hostUrl), " View logs for more details. \n" + e.getClass().getName(), NotificationType.WARNING);
        Notifications.Bus.notify(notification);
    }

    @NotNull
    private HttpURLConnection postResults(String serverURI, byte[] body) throws IOException {
        HttpURLConnection connection = createHttpUrlConnectionTo(new URL(serverURI + WRITE_METRICS_URL));
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Length", Integer.toString(body.length));

        try (DataOutputStream out = new DataOutputStream(connection.getOutputStream())) {
            out.write(body);
        }

        return connection;
    }

    @NotNull
    private byte[] constructInfluxDbPost(final TestResults result) {
        final String hostName = getHostName();
        String body = result.getTestResult().getChildren().stream().map(y ->
                String.format("testDuration,host=%s,project=%s,testName=%s value=%s %s",
                        hostName,
                        result.getProjectName(),
                        y.getName(),
                        y.getDuration(),
                        Instant.now().toEpochMilli())
        ).collect(Collectors.joining("\n"));
        return body.getBytes(StandardCharsets.US_ASCII);
    }

    @NotNull
    private String getHostName() {
        String hostName;
        try {
            hostName = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            hostName = "Unknown";
        }
        return hostName;
    }

    @NotNull
    private HttpURLConnection createHttpUrlConnectionTo(URL url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.setInstanceFollowRedirects(false);
        connection.setUseCaches(false);
        connection.setConnectTimeout(2000);
        connection.setReadTimeout(2000);
        return connection;
    }
}
