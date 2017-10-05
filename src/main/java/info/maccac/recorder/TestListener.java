package info.maccac.recorder;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.intellij.execution.testframework.AbstractTestProxy;
import com.intellij.execution.testframework.TestStatusListener;
import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.Nullable;

public class TestListener extends TestStatusListener {

    private Gson gson;

    public TestListener() {
        this.gson = new GsonBuilder().create();
    }

    @Override
    public void testSuiteFinished(@Nullable AbstractTestProxy root, Project project) {
        super.testSuiteFinished(root, project);

        TestResults breakdown = TestResultsConverter.breakdown(project.getName(), root);
        String json = gson.toJson(breakdown);
        Notification notification = new Notification("Test recorder", "Test recorded", json, NotificationType.INFORMATION);
        Notifications.Bus.notify(notification);
    }

    @Override
    public void testSuiteFinished(@Nullable AbstractTestProxy root) {
    }

}
