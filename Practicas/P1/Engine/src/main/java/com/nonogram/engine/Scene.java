package com.nonogram.engine;

public interface Scene {
    void init();
    void render();
    void update(double deltaTime);
    void getInput();
    void processInput(Input.TouchEvent input);
    void setEngine(Engine e);
}
