package com.maccac.recorder.client;

import com.maccac.recorder.model.TestResults;

public interface TestResultsClient {
    void postTestResults(String serverURI, TestResults breakdown);

    void testConnection(String hostUrl);
}
