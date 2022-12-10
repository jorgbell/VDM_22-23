package com.nonogram.engine;

public interface Scene {
    boolean init();
    void render();
    void update(double deltaTime);
    void processInput(Input.TouchEvent input);
    void setEngine(Engine e);
    int getGameWidth();
    int getGameHeight();
    void setGameWidth(int w);
    void setGameHeight(int h);
    boolean release();
    void handleClosingNotifications();
    void handleOpeningNotifications();
}
