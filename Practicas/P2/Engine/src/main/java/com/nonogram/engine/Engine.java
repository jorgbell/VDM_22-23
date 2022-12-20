package com.nonogram.engine;

import java.util.Stack;

public interface Engine {
    Graphics getGraphics();
    Input getInput();
    Audio getAudio();
    JSONManager getJSONManager();
    NotificationMngr getNotificationManager();
    AbstractSensors getSensors();
    IntentManager getIntentManager();
    AdManager getAdManager();
    double getDeltaTime();
    boolean stop();
    void close();
    void resume();
    void pause();
    boolean release();
    boolean init();
    public SceneManager getSceneManager();
    AbstractEngine.EnginePaths getEnginePaths();
    void addClosingNotification(NotificationData data);
    Stack<NotificationData> getClosingNotifications();
}
