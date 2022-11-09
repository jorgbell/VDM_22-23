package com.nonogram.engine;

public interface Engine {
    Graphics getGraphics();
    Input getInput();
    Audio getAudio();
    double getDeltaTime();
    boolean stop();
    void resume();
    void pause();
    boolean release();
    boolean init();
    public SceneManager getSceneManager();
}
