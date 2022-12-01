package com.nonogram.logic;

import com.nonogram.engine.Engine;
import com.nonogram.engine.Graphics;
import com.nonogram.engine.Image;
import com.nonogram.engine.Input;
import com.nonogram.engine.Scene;

public class ChangeSceneButton extends Button{
    public ChangeSceneButton(int x, int y, int w, int h, Engine e, Scene ns)
    {
        super(x, y, w, h);
        _engine = e;
        _newScene = ns;
        if(ns!=null){_push=true;} //PUSH
    }


    @Override
    public void handleEvent(Input.TouchEvent e) {
        if(_push) _engine.getSceneManager().push(_newScene);
        else _engine.getSceneManager().pop();
    }

    @Override
    public void update(double deltaTime) {
    }

    Engine _engine;
    Scene _newScene;
    Boolean _push = false; //POP

}
