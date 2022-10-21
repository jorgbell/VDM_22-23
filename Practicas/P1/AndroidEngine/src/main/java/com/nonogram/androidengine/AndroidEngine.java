package com.nonogram.androidengine;

import androidx.appcompat.app.AppCompatActivity;

import com.nonogram.engine.AbstractEngine;
import com.nonogram.engine.Scene;

public class AndroidEngine extends AbstractEngine {

    public AndroidEngine(AppCompatActivity context, int w, int h, Scene inicial){
        super(new AndroidGraphics(context,w,h), inicial);
    }

    @Override
    public void run() {
        super.run();
        _myGraphics.render();
    }
}
