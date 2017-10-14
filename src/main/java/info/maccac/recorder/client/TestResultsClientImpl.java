package info.maccac.recorder.client;

import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.diagnostic.Logger;
import info.maccac.recorder.config.Preferences;
import info.maccac.recorder.model.TestResults;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.stream.Collectors;

public class TestResultsClientImpl implements TestResultsClient {

    private static final Logger logger = Logger.getInstance("TestResultsClientImpl");
    private static final String DEFAULT_HOST = "/write?db=mydb&precision=ms";
    private Preferences preferences;

    public TestResultsClientImpl() {
        this.preferences = new Preferences();
    }

    @Override
    public void postTestResults(TestResults breakdown) {
        try {
            HttpURLConnection connection =  postResults(constructInfluxDbPost(breakdown));
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                logger.warn("Failed to send results to server.");
            }
        } catch (IOException e) {
            logger.warn(e);
            Notification notification = new Notification("Test recorder", "Failed to post results", e.getMessage(), NotificationType.WARNING);
            Notifications.Bus.notify(notification);
        }
    }

    @Override
    public void testConnection() {

    }

    @NotNull
    private HttpURLConnection postResults(byte[] body) throws IOException {
        HttpURLConnection connection = createHttpUrlConnectionTo(preferences.getServerURI() + DEFAULT_HOST);
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
        } catch(UnknownHostException e) {
            hostName = "Unknown";
        }
        return hostName;
    }

    @NotNull
    private HttpURLConnection createHttpUrlConnectionTo(String defaultHost) throws IOException {
        URL url = new URL(defaultHost);
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
