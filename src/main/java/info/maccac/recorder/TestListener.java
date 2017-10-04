package info.maccac.recorder;

import com.intellij.execution.testframework.AbstractTestProxy;
import com.intellij.execution.testframework.TestStatusListener;
import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TestListener extends TestStatusListener {

    @Override
    public void testSuiteFinished(@Nullable AbstractTestProxy root, Project project) {
        super.testSuiteFinished(root, project);

        List<TestResult> testNames = root.getChildren().stream()
                .flatMap(TestListener::flatten)
                .filter(p -> p.getChildren().isEmpty())
                .map(p -> new TestResult(p.getParent().getName(), p.getName(), p.getDuration()))
                .collect(Collectors.toList());

        String content = String.format("{project: '%s', test: %s, time: '%s'}", project.getName(), testNames.toString(), root.getDuration());

        Notification notification = new Notification("Test recorder", "Test recorded", content, NotificationType.INFORMATION);
        Notifications.Bus.notify(notification);
    }

    private static Stream<AbstractTestProxy> flatten(AbstractTestProxy a) {
        return Stream.concat(Stream.of(a), a.getChildren().stream().flatMap(TestListener::flatten));
    }

    @Override
    public void testSuiteFinished(@Nullable AbstractTestProxy root) {
    }

    public static class TestResult {


        private String parentName;
        private final String name;
        private final Long duration;

        TestResult(String parentName, String name, Long duration) {
            this.parentName = parentName;
            this.name = name;
            this.duration = duration;
        }

        @Override
        public String toString() {
            return String.format("{parent: '%s', name: '%s', time: %s}", parentName, name, duration);
        }
    }
}
