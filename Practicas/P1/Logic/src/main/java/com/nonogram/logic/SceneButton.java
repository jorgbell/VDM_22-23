package com.nonogram.logic;

import com.nonogram.engine.Graphics;
import com.nonogram.engine.Input;
import com.nonogram.engine.Scene;

public class SceneButton extends Button{

    public SceneButton(int x, int y, int w, int h, Scene cs, Scene ns)
    {
        super(x, y, w, h);
        _currentScene = cs;
        _newScene = ns;
    }

    @Override
    public void render(Graphics g) {

    }

    @Override
    public void update(double deltaTime) {

    }

    @Override
    public void handleEvent(Input.TouchEvent e) {
        //_currentScene.changeScene(_newScene);
    }

    //Image _image;
    Scene _currentScene;
    Scene _newScene;
}
