package info.maccac.recorder.model;

public class TestResults {

    private String projectName;
    private TestResult testResult;

    public TestResults(String projectName, TestResult testResult) {
        this.projectName = projectName;
        this.testResult = testResult;
    }
}
