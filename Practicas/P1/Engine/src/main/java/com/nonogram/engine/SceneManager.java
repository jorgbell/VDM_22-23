package com.nonogram.engine;

import java.util.List;

public interface SceneManager {

    public void render();

    public void update(double deltaTime);

    public void getInput(List<Input.TouchEvent> inputList);

    public void processInput(Input.TouchEvent input);

    public int getGameWidth();

    public int getGameHeight();

    public void setGameSize(int w, int h);

    public boolean push(Scene s, Engine e);

    public boolean pop();

    boolean release();

    boolean empty();

}
