package com.maccac.recorder.config;

import javax.swing.*;
import java.awt.*;

class TestRecorderPreferencesPanel extends JPanel {

    private final JTextField serverURLInput;
    private final Preferences preferences;

    TestRecorderPreferencesPanel(TestRecorderConfigurable testRecorderConfigurable, Preferences preferences) {
        super();
        this.preferences = preferences;

        FlowLayout layoutManager = new FlowLayout(FlowLayout.LEFT);
        this.setLayout(layoutManager);

        JLabel label = new JLabel("Influx URL: ", JLabel.LEADING);
        serverURLInput = new JTextField(25);

        this.add(label);
        this.add(serverURLInput);

        serverURLInput.setMinimumSize(serverURLInput.getPreferredSize());
        serverURLInput.setMaximumSize(serverURLInput.getPreferredSize());
        serverURLInput.setText(preferences.getServerURI());

        addModificationListeners(testRecorderConfigurable);
    }

    private void addModificationListeners(TestRecorderConfigurable testRecorderConfigurable) {
        serverURLInput.getAccessibleContext().addPropertyChangeListener(evt -> testRecorderConfigurable.setModified());
    }

    String getServerURL() {
        return serverURLInput.getText();
    }

    void reset() {
        serverURLInput.setText(preferences.getServerURI());
    }
}
