package com.nonogram.androidengine;

import androidx.appcompat.app.AppCompatActivity;

import com.nonogram.engine.AbstractEngine;
import com.nonogram.engine.Scene;

public class AndroidEngine extends AbstractEngine {

    public AndroidEngine(AppCompatActivity context, Scene inicial){
        super(new AndroidGraphics(context), inicial);
    }

}
