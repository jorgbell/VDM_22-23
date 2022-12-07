package com.nonogram.engine;

public interface Engine {
    Graphics getGraphics();
    Input getInput();
    Audio getAudio();
    JSONManager getJSONManager();
    NotificationMngr getNotificationManager();
    Sensors getSensors();
    double getDeltaTime();
    boolean stop();
    void resume();
    void pause();
    boolean release();
    boolean init();
    public SceneManager getSceneManager();
    AbstractEngine.EnginePaths getEnginePaths();
}
