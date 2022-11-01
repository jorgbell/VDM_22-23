package com.nonogram.engine;

public interface Scene {
    void init();
    void render();
    void update(double deltaTime);
    void setEngine(Engine e);
}
