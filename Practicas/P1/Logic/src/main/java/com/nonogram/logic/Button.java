package com.nonogram.logic;

import java.awt.geom.Rectangle2D;
import com.nonogram.engine.Graphics;
import com.nonogram.engine.Input;

public abstract class Button {

    public class Rect {
        public Rect(int x, int y, int w, int h) {
            _x = x;
            _y = y;
            _w = w;
            _h = h;
        }

        public boolean contains(double x, double y) {
            return (x > _x && x < _x + _w && y > _y && y < _y + _h);
        }

        int _x, _y, _w, _h;

    }

    public Button(int x, int y, int w, int h/*, Action ac?*/) {
        rect = new Rect(x, y, w, h);
        //action = ac;s
    }

    public abstract void render(Graphics g);

    public abstract void handleEvent(Input.TouchEvent e);
    public abstract void update(double deltaTime);

    //Action action;
    Rect rect;
}
