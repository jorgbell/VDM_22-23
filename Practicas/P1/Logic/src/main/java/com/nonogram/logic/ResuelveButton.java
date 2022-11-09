package com.nonogram.logic;

import com.nonogram.engine.Graphics;
import com.nonogram.engine.Image;
import com.nonogram.engine.Input;

public class ResuelveButton extends Button{

    public ResuelveButton(int x, int y, int w, int h, GameScene g, Image img) {
        super(x, y, w, h);
        _g = g;
        _image = img;
    }

    @Override
    public void render(Graphics g) {
        g.setColor(0x0FF0F0F0);
        g.fillRect(_rect._x, _rect._y, _rect._w, _rect._h);

        g.setColor(0xFF000000);
        g.drawText("Comprobar", _rect._x + _rect._w / 20, _rect._y + _rect._h * 3 / 5);
        g.drawImage(_image, _rect._x + _rect._w * 13 / 16, _rect._y + _rect._h / 3, 0.04, 0.04);
        g.drawRect(_rect._x, _rect._y, _rect._w, _rect._h);
    }

    @Override
    public void update(double deltaTime) {

    }

    @Override
    public void handleEvent(Input.TouchEvent e) {
        _g.showSolution();
    }


    Image _image;
    GameScene _g;

}