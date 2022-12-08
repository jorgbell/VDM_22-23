package com.nonogram.engine;

public interface Scene {
    boolean init();
    void render();
    void update(double deltaTime);
    void processInput(Input.TouchEvent input);
    void setEngine(Engine e);
    int getGameWidth();
    int getGameHeight();
    boolean release();
    void handleNotifications(String key);
}
