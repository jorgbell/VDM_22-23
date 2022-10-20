package com.nonogram.logic;

import com.nonogram.engine.Engine;
import com.nonogram.engine.Scene;

public class PruebaScene implements Scene{

    public PruebaScene(){

    }

    @Override
    public void render() {
        _myEngine.getGraphics().clear(0);
    }

    @Override
    public void update(double deltaTime) {
        System.out.print("aaaaa");
    }

    //TODO: AbstractScene
    @Override
    public void setEngine(Engine e) {
        _myEngine = e;
    }
    Engine _myEngine;
}
