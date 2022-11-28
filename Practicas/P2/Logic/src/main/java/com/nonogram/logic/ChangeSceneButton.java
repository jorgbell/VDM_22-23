package com.nonogram.logic;

import com.nonogram.engine.Engine;
import com.nonogram.engine.Graphics;
import com.nonogram.engine.Image;
import com.nonogram.engine.Input;
import com.nonogram.engine.Scene;

public class ChangeSceneButton extends Button{
    //PUSH BUTTON
    public ChangeSceneButton(int x, int y, int w, int h, String text, Engine e, Scene ns, Image img , double scale)
    {
        super(x, y, w, h);
        _text = text;
        _engine = e;
        _newScene = ns;
        _image = img;
        _push = true;
        _scale= scale;
    }
    //POP BUTTON
    public ChangeSceneButton(int x, int y, int w, int h, String text, Engine e, Image img, double scale)
    {
        super(x, y, w, h);
        _text = text;
        _engine = e;
        _image = img;
        _push = false;
        _scale = scale;
    }
    @Override
    public void render(Graphics g) {
        g.setColor(0xFFF0F0F0);
        g.fillRect(_rect._x, _rect._y, _rect._w, _rect._h);

        g.setColor(0xFF000000);
        g.drawText(_text, _rect._x + _rect._w/2, _rect._y + _rect._h/2);
        if(_image != null) g.drawImage(_image, _rect._x , _rect._y , _scale, _scale);
        g.drawRect(_rect._x, _rect._y, _rect._w, _rect._h);
    }

    @Override
    public void update(double deltaTime) {

    }

    @Override
    public void handleEvent(Input.TouchEvent e) {
        if(_push) _engine.getSceneManager().push(_newScene);
        else _engine.getSceneManager().pop();
    }

    Image _image;
    String _text;
    Engine _engine;
    Scene _newScene;
    Boolean _push = false;
    double _scale;
}
