package info.maccac.recorder.config;

import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.options.SearchableConfigurable;
import info.maccac.recorder.client.TestResultsClient;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class TestRecorderConfigurable implements SearchableConfigurable {
    private boolean isModified;
    private final TestRecorderPreferencesPanel preferencesPanel;
    private final Preferences preferences;

    public TestRecorderConfigurable() {
        preferences = new Preferences();
        preferencesPanel =  new TestRecorderPreferencesPanel(this, preferences);
    }

    @NotNull
    @Override
    public String getId() {
        return "preference.TestRecorderConfigurable";
    }

    @Nls
    @Override
    public String getDisplayName() {
        return "Test Recorder";
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        return preferencesPanel;
    }

    @Override
    public boolean isModified() {
        return isModified;
    }

    void setModified() {
        this.isModified = true;
    }

    @Override
    public void apply() throws ConfigurationException {
        TestResultsClient service = ServiceManager.getService(TestResultsClient.class);
        service.testConnection();

        preferences.setServerURI(preferencesPanel.getServerURL());
    }

    @Override
    public void reset() {
        preferencesPanel.reset();
        preferences.reset();
    }
}
