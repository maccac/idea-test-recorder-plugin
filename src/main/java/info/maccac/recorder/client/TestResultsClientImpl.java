package info.maccac.recorder.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.intellij.openapi.diagnostic.Logger;
import info.maccac.recorder.TestResults;

public class TestResultsClientImpl implements TestResultsClient {

    private static final Logger logger = Logger.getInstance("TestResultsClientImpl");

    private Gson gson;

    public TestResultsClientImpl() {
        this.gson = new GsonBuilder().create();
    }

    @Override
    public void postTestResults(TestResults breakdown) {
        logger.info("Posting: " + gson.toJson(breakdown));
    }
}
