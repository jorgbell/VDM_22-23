package com.nonogram.androidengine;

import androidx.appcompat.app.AppCompatActivity;

import com.nonogram.engine.AbstractEngine;
import com.nonogram.engine.Sensors;

public class AndroidEngine extends AbstractEngine {

    public AndroidEngine(AppCompatActivity context){
        super(new AndroidGraphics(context), new AndroidInput(), new AndroidAudio(), new AndroidJSONManager(), new Sensors(), new AndroidIntentManager(), new AbstractEngine.EnginePaths("", "images/", "fonts/", "audio/", "JSON/"));
        AndroidGraphics aG = (AndroidGraphics) _myGraphics;
        ((AndroidAudio)_myAudio)._assetManager = aG._context.getAssets();
        ((AndroidJSONManager)_myJSONManager)._context = aG._context;
        ((AndroidJSONManager)_myJSONManager)._myPaths = _myPaths;
        ((AndroidIntentManager)_myIntentManager)._context = aG._context;
        ((AndroidIntentManager)_myIntentManager)._myPaths = _myPaths;
    }
}
