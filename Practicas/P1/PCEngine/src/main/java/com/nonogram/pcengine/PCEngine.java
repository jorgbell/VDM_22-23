package com.nonogram.pcengine;

import com.nonogram.engine.AbstractEngine;
import com.nonogram.engine.Scene;

public class PCEngine extends AbstractEngine{

    public PCEngine(String windowName, int w, int h, Scene inicial){
        super(new PCGraphics(windowName, w, h), new PCInput(), inicial, new EnginePaths("./data/", "./data/images/", "./data/fonts/"));
    }
}
