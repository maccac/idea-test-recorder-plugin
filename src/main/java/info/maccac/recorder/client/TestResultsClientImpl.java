package info.maccac.recorder.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.diagnostic.Logger;
import info.maccac.recorder.TestResults;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class TestResultsClientImpl implements TestResultsClient {

    private static final Logger logger = Logger.getInstance("TestResultsClientImpl");
    private static final String DEFAULT_HOST = "http://localhost:8080/results";

    private Gson gson;

    public TestResultsClientImpl() {
         this.gson = new GsonBuilder().create();
    }

    @Override
    public void postTestResults(TestResults breakdown) {
        try {
            String jsonBody = gson.toJson(breakdown);
            HttpURLConnection connection = postJson(jsonBody);
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                logger.error("Failed to send results to server.");
            }
        } catch (IOException e) {
            logger.warn(e);
            Notification notification = new Notification("Test recorder", "Failed to post results", e.getMessage(), NotificationType.INFORMATION);
            Notifications.Bus.notify(notification);
        }
    }

    @NotNull
    private HttpURLConnection postJson(String jsonBody) throws IOException {
        HttpURLConnection connection = createHttpUrlConnectionTo(DEFAULT_HOST);
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("charset", "utf-8");
        byte[] jsonAsBytes = jsonBody.getBytes(StandardCharsets.UTF_8);
        connection.setRequestProperty("Content-Length", Integer.toString(jsonAsBytes.length));

        try (DataOutputStream out = new DataOutputStream(connection.getOutputStream())) {
            out.write(jsonAsBytes);
        }

        return connection;
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
