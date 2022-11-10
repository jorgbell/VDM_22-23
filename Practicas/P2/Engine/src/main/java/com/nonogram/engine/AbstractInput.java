package com.nonogram.engine;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractInput implements Input{
    protected AbstractInput(){
        _myTouchEvents = new ArrayList<TouchEvent>();
    }

    @Override
    public List<TouchEvent> getTouchEvents() {
        return _myTouchEvents;
    }

    @Override
    public void newEvent(float x, float y, int ID, TouchEvent.InputType tipo) {
        //transformamos las posiciones que se consiguen de la pantalla a posiciones de juego
        float gamex = _myGraphics.worldToGameX(x);
        float gamey = _myGraphics.worldToGameY(y);
        TouchEvent tE = new TouchEvent(tipo,gamex, gamey, ID);
        synchronized (this){
            _myTouchEvents.add(tE);
        }
    }

    @Override
    public void setGraphics(Graphics g){_myGraphics = g;}

    List<TouchEvent> _myTouchEvents;
    Graphics _myGraphics;
}
