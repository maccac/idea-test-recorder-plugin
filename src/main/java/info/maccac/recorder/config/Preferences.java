package info.maccac.recorder.config;

public interface Preferences {
    String getServerURI();

    void setServerURI(String serverURI);

    void reset();
}
