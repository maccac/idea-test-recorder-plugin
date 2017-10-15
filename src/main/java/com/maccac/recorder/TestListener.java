package com.maccac.recorder;

import com.intellij.execution.testframework.AbstractTestProxy;
import com.intellij.execution.testframework.TestStatusListener;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.project.Project;
import com.maccac.recorder.client.TestResultsClient;
import com.maccac.recorder.config.Preferences;
import com.maccac.recorder.model.TestResultsConverter;
import org.jetbrains.annotations.Nullable;

public class TestListener extends TestStatusListener {

    private final Preferences preferences;
    private TestResultsClient client;

    public TestListener() {
        preferences = ServiceManager.getService(Preferences.class);
        client = ServiceManager.getService(TestResultsClient.class);
    }

    @Override
    public void testSuiteFinished(@Nullable AbstractTestProxy root, Project project) {
        super.testSuiteFinished(root, project);
        client.postTestResults(preferences.getServerURI(), TestResultsConverter.breakdown(project.getName(), root));
    }

    @Override
    public void testSuiteFinished(@Nullable AbstractTestProxy root) {
    }

}
