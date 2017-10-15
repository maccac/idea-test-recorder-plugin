package com.maccac.recorder.model;

import com.intellij.execution.testframework.AbstractTestProxy;

import java.util.List;
import java.util.stream.Collectors;

public final class TestResultsConverter {

    private TestResultsConverter() {
    }

    public static TestResults breakdown(String projectName, AbstractTestProxy testProxy) {
        return new TestResults(projectName, new TestResult(null, testProxy.getName(), testProxy.getDuration(), getChildren(testProxy)));
    }

    private static List<TestResult> getChildren(AbstractTestProxy testProxy) {
        return testProxy
                .getChildren().stream()
                .map(p -> new TestResult(p.getParent().getName(), p.getName(), p.getDuration(), getChildren(p)))
                .collect(Collectors.toList());
    }
}
