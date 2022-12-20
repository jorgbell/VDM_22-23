package com.nonogram.engine;

public interface Scene {
    boolean init();
    void render();
    void update(double deltaTime);
    void processInput(Input.TouchEvent input);
    void setEngine(Engine e);
    int getGameWidth();
    int getGameHeight();
    void rotate();
    boolean release();
    boolean persist();
    void handleClosingNotifications();
    void handleOpeningNotifications();
    void handleAdd();
}
