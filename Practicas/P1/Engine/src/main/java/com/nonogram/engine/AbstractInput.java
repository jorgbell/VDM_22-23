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
    public void newEvent(int x, int y, int ID, TouchEvent.InputType tipo) {
        TouchEvent tE = new TouchEvent(tipo,x, y, ID);
        synchronized (this){
            _myTouchEvents.add(tE);
        }
    }

    List<TouchEvent> _myTouchEvents;
}
