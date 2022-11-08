package com.nonogram.logic;

import com.nonogram.engine.Graphics;
import com.nonogram.engine.Input;
import com.nonogram.engine.Image;

public class RendirseButton extends Button{

    public RendirseButton(int x, int y, int w, int h, GameScene g/*, Image img*/) {
        super(x, y, w, h);
        _g = g;
        //_image = img;
    }

    @Override
    public void render(Graphics g) {
        //g.drawImage(_image, rect._x, rect._y);
        g.setColor(0xFF000000);
        g.drawText("Comprobar", rect._x, rect._y);
        g.drawRect(rect._x, rect._y, rect._w, rect._h);
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
