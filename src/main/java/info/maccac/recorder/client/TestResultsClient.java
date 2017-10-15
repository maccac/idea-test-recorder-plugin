package info.maccac.recorder.client;

import info.maccac.recorder.model.TestResults;

public interface TestResultsClient {
    void postTestResults(String serverURI, TestResults breakdown);

    void testConnection(String hostUrl);
}
