package info.maccac.recorder.client;

import info.maccac.recorder.model.TestResults;

public interface TestResultsClient {
    void postTestResults(TestResults breakdown);
}
