package com.nonogram.logic;

import com.nonogram.engine.Engine;
import com.nonogram.engine.Graphics;
import com.nonogram.engine.Input;
import com.nonogram.engine.Scene;

public class PushSceneButton extends Button{

    public PushSceneButton(int x, int y, int w, int h, String text, Engine e, Scene ns)
    {
        super(x, y, w, h);
        _text = text;
        _engine = e;
        _newScene = ns;
    }

    @Override
    public void render(Graphics g) {
        g.setColor(0xFF000000);
        g.drawText(_text, rect._x + rect._w / 8, rect._y + rect._h * 3 / 5);
        g.drawRect(rect._x, rect._y, rect._w, rect._h);
    }

    @Override
    public void update(double deltaTime) {

    }

    @Override
    public void handleEvent(Input.TouchEvent e) {
        _engine.getSceneManager().push(_newScene);
    }

    //Image _image;
    String _text;
    Engine _engine;
    Scene _newScene;
}