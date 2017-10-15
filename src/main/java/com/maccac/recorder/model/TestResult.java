package com.maccac.recorder.model;

import java.util.List;

public class TestResult {

    private final String parentName;
    private final String name;
    private final Long duration;
    private final List<TestResult> children;

    public TestResult(String parentName, String name, Long duration, List<TestResult> children) {
        this.parentName = parentName;
        this.name = name;
        this.duration = duration;
        this.children = children;
    }

    public String getName() {
        return name;
    }

    public Long getDuration() {
        return duration;
    }

    public List<TestResult> getChildren() {
        return children;
    }

    @Override
    public String toString() {
        return String.format("{parent: '%s', name: '%s', time: %s}", parentName, name, duration);
    }
}
