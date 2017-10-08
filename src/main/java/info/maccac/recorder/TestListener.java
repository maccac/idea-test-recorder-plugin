package info.maccac.recorder;

import com.intellij.execution.testframework.AbstractTestProxy;
import com.intellij.execution.testframework.TestStatusListener;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.project.Project;
import info.maccac.recorder.client.TestResultsClient;
import info.maccac.recorder.model.TestResultsConverter;
import org.jetbrains.annotations.Nullable;

public class TestListener extends TestStatusListener {

    @Override
    public void testSuiteFinished(@Nullable AbstractTestProxy root, Project project) {
        super.testSuiteFinished(root, project);

        TestResultsClient service = ServiceManager.getService(TestResultsClient.class);
        service.postTestResults(TestResultsConverter.breakdown(project.getName(), root));
    }

    @Override
    public void testSuiteFinished(@Nullable AbstractTestProxy root) {
    }

}
