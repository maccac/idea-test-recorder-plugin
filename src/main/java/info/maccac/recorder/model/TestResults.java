package info.maccac.recorder.model;

public class TestResults {

    private String projectName;
    private TestResult testResult;
    private String type;

    public TestResults(String projectName, TestResult testResult) {
        this.projectName = projectName;
        this.testResult = testResult;
        this.type = "IDE_TEST";
    }

    public String getProjectName() {
        return projectName;
    }

    public TestResult getTestResult() {
        return testResult;
    }

    public String getType() {
        return type;
    }
}
