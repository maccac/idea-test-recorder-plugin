package info.maccac.recorder.config;

import com.intellij.ide.util.PropertiesComponent;

public class PreferencesImpl implements Preferences {

    private static final String DEFAULT_SERVER_URI = "http://localhost:8086";
    private final PropertiesComponent properties;

    public PreferencesImpl() {
        properties = PropertiesComponent.getInstance();
        properties.getValue("test.recorder.server.uri", DEFAULT_SERVER_URI);

    }

    @Override
    public String getServerURI() {
        return properties.getValue("test.recorder.server.uri", DEFAULT_SERVER_URI);
    }

    @Override
    public void setServerURI(String serverURI) {
        properties.setValue("test.recorder.server.uri", serverURI);
    }

    @Override
    public void reset() {
        properties.setValue("test.recorder.server.uri", DEFAULT_SERVER_URI);
    }
}
