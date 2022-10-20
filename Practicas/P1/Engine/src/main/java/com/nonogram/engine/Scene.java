package com.nonogram.engine;

public interface Scene {
    void render();
    void update(double deltaTime);
    void setEngine(Engine e);
}
