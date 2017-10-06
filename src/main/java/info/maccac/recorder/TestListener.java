package info.maccac.recorder;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.intellij.execution.testframework.AbstractTestProxy;
import com.intellij.execution.testframework.TestStatusListener;
import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.project.Project;
import info.maccac.recorder.client.TestResultsClient;
import org.jetbrains.annotations.Nullable;

public class TestListener extends TestStatusListener {

    @Override
    public void testSuiteFinished(@Nullable AbstractTestProxy root, Project project) {
        super.testSuiteFinished(root, project);

        TestResults breakdown = TestResultsConverter.breakdown(project.getName(), root);
        TestResultsClient service = ServiceManager.getService(TestResultsClient.class);
        service.postTestResults(breakdown);

        Notification notification = new Notification("Test recorder", "Test recorded", "", NotificationType.INFORMATION);
        Notifications.Bus.notify(notification);
    }

    @Override
    public void testSuiteFinished(@Nullable AbstractTestProxy root) {
    }

}
