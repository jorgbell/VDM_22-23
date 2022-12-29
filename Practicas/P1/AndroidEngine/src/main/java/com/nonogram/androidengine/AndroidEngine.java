package com.nonogram.androidengine;

import androidx.appcompat.app.AppCompatActivity;

import com.nonogram.engine.AbstractEngine;
import com.nonogram.engine.Scene;

public class AndroidEngine extends AbstractEngine {

    AppCompatActivity activity;

    public AndroidEngine(AppCompatActivity context){
        super(new AndroidGraphics(context), new AndroidInput(), new AndroidAudio(), new AbstractEngine.EnginePaths("", "images/", "fonts/", "audio/"));
        activity = context;


        AndroidGraphics aG = (AndroidGraphics) _myGraphics;
        aG.setAudioContext((AndroidAudio)_myAudio);
    }

    @Override
    public boolean stop() {
        super.stop();
        activity.finishAndRemoveTask();
        return true;
    }

}
