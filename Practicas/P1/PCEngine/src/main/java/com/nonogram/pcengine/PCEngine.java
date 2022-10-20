package com.nonogram.pcengine;

import com.nonogram.engine.AbstractEngine;
import com.nonogram.engine.Scene;

public class PCEngine extends AbstractEngine{

    public PCEngine(String windowName, int w, int h, Scene inicial){
        super(new PCGraphics(windowName, w, h), inicial);
        _myScene.setEngine(this);
    }

    @Override
    public void resume() {
        super.resume();
    }
}
