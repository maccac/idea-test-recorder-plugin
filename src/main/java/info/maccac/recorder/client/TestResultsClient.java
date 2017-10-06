package info.maccac.recorder.client;

import info.maccac.recorder.TestResults;

public interface TestResultsClient {
    void postTestResults(TestResults breakdown);
}
