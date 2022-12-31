package com.nonogram.logic;

import com.nonogram.engine.Engine;
import com.nonogram.engine.Graphics;
import com.nonogram.engine.Image;
import com.nonogram.engine.Input;
import com.nonogram.engine.Scene;

public class ChangeSceneButton extends Button{
    //PUSH BUTTON
    public ChangeSceneButton(int x, int y, int w, int h, String text, Engine e, Scene ns, Image img )
    {
        super(x, y, w, h);
        _text = text;
        _engine = e;
        _newScene = ns;
        _image = img;
        _push = true;
    }
    //POP BUTTON
    public ChangeSceneButton(int x, int y, int w, int h, String text, Engine e, Image img)
    {
        super(x, y, w, h);
        _text = text;
        _engine = e;
        _image = img;
        _push = false;
    }
    @Override
    public void render(Graphics g) {
        g.setColor(0xFFF0F0F0);
        g.fillRect(_rect._x, _rect._y, _rect._w, _rect._h);

        g.setColor(0xFF000000);
        g.drawText(_text, _rect._x + _rect._w * 2 / 9, _rect._y + _rect._h * 3 / 5);
        if(_image != null) g.drawImage(_image, _rect._x + _rect._w / 20, _rect._y + _rect._w / 9, 0.04, 0.04);
        g.drawRect(_rect._x, _rect._y, _rect._w, _rect._h);
    }

    @Override
    public void update(double deltaTime) {

    }

    @Override
    public void handleEvent(Input.TouchEvent e) {
        if(_push) _engine.getSceneManager().push(_newScene, _engine);
        else _engine.getSceneManager().pop();
    }

    private Image _image;
    private String _text;
    private Engine _engine;
    private Scene _newScene;
    private Boolean _push = false;
}
