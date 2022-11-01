package com.nonogram.engine;

public abstract class AbstractScene implements Scene{
    @Override
    public void setEngine(Engine e) {
        _myEngine = e;
    }

    protected Engine _myEngine;
}
