package com.nonogram.logic;

import com.nonogram.engine.Graphics;
import com.nonogram.engine.Input;

public class CasillaButton extends Button{

    public CasillaButton(int x, int y, int w, int h, Tablero.Casilla c, LogicJSON.Palette aP) {
        super(x, y, w, h, aP);
        _casilla = c;
    }


    public void render(Graphics g)
    {
        Tablero.State state =  _casilla.getState();

        switch(state)
        {
            case EMPTY:
                g.setColor((int)_actualPalette.emptyColor);
                break;
            case CROSS:
                g.setColor(0XFF000000);
                break;
            case PICK:
                g.setColor((int)_actualPalette.pickColor);
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
        if(e.get_type() == Input.TouchEvent.InputType.CLICK_CORTO){
            _casilla.setState(Tablero.State.values()[1]);
        }
        else{
            if(_casilla.getState()== Tablero.State.CROSS){
                _casilla.setState(Tablero.State.values()[0]);
            }
            else{
                _casilla.setState(Tablero.State.values()[2]);
            }
        }
    };

    public void update(double deltaTime) {};
    Tablero.Casilla _casilla;
}
