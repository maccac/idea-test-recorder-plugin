package info.maccac.recorder.config;

import com.intellij.ide.util.PropertiesComponent;

public class Preferences {

    private static final String DEFAULT_SERVER_URI = "http://localhost:8086";
    private final PropertiesComponent properties;

    public Preferences() {
        properties = PropertiesComponent.getInstance();
        properties.getValue("test.recorder.server.uri", DEFAULT_SERVER_URI);

    }

    public String getServerURI() {
        return properties.getValue("test.recorder.server.uri", DEFAULT_SERVER_URI);
    }

    void setServerURI(String serverURI) {
        properties.setValue("test.recorder.server.uri", serverURI);
    }

    public void reset() {
        properties.setValue("test.recorder.server.uri", DEFAULT_SERVER_URI);
    }
}
