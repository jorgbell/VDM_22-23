package com.nonogram.logic;

import com.nonogram.engine.Graphics;
import com.nonogram.engine.Input;

public class CasillaButton extends Button{

    public CasillaButton(int x, int y, int w, int h, Tablero.Casilla c) {
        super(x, y, w, h);
        _casilla = c;
    }


    public void render(Graphics g)
    {
        Tablero.State state =  _casilla.getState();

        switch(state)
        {
            case EMPTY:
                g.setColor(0XFFa4a4a4);
                break;
            case CROSS:
                g.setColor(0XFF000000);
                break;
            case PICK:
                g.setColor(0XFF2a35cc);
                break;
            case WRONG:
                g.setColor(0XFFF03434);
                break;
            default:
                g.setColor(0XFFFFFFFF);
                break;
        }

        if(state == Tablero.State.CROSS) //pinta la linea diagonal
        {
            g.drawRect(_rect._x, _rect._y, _rect._w, _rect._h);
            g.drawLine(_rect._x, _rect._y, _rect._x + _rect._w, _rect._y + _rect._h);
        }

        else g.fillRect(_rect._x, _rect._y, _rect._w, _rect._h);
    };

    public void handleEvent(Input.TouchEvent e)
    {
        int s =  (_casilla.getState().getValue() + 1) % 3;
        _casilla.setState(Tablero.State.values()[s]);
    };

    public void update(double deltaTime) {};

    Tablero.Casilla _casilla;
}
