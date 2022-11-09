package com.nonogram.logic;

import com.nonogram.engine.Graphics;
import com.nonogram.engine.Input;

public class ResuelveButton extends Button{

    public ResuelveButton(int x, int y, int w, int h, GameScene g/*, Image img*/) {
        super(x, y, w, h);
        _g = g;
        //_image = img;
    }

    @Override
    public void render(Graphics g) {
        //g.drawImage(_image, rect._x, rect._y);
        g.setColor(0xFF000000);
        g.drawText("Comprobar", _rect._x + _rect._w / 8, _rect._y + _rect._h * 3 / 5);
        g.drawRect(_rect._x, _rect._y, _rect._w, _rect._h);
    }

    @Override
    public void update(double deltaTime) {

    }

    @Override
    public void handleEvent(Input.TouchEvent e) {
        _g.showSolution();
    }


    //Image _image;
    GameScene _g;

}