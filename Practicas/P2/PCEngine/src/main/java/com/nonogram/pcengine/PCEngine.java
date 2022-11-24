package com.nonogram.pcengine;

import com.nonogram.engine.AbstractEngine;
import com.nonogram.engine.Scene;

public class PCEngine extends AbstractEngine{

    public PCEngine(String windowName, int w, int h, boolean fullScreen){
        super(new PCGraphics(windowName, w, h, fullScreen), new PCInput(), new PCAudio(), new EnginePaths("./data/", "./data/images/", "./data/fonts/", "./data/audio/", "./data/Boards/"));
    }
}
