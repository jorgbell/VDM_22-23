package com.nonogram.androidengine;

import android.view.SurfaceView;

import androidx.appcompat.app.AppCompatActivity;

import com.nonogram.engine.AbstractEngine;
import com.nonogram.engine.Scene;

public class AndroidEngine extends AbstractEngine {

    AppCompatActivity activity;

    public AndroidEngine(AppCompatActivity context){
        super(new AndroidGraphics(), new AndroidInput(), new AndroidAudio(), new AbstractEngine.EnginePaths("", "images/", "fonts/", "audio/"));
        activity = context;

    }

    @Override
    public boolean init() {
        if (!super.init())
            return false;
        AndroidGraphics aG = (AndroidGraphics) _myGraphics;
        aG.setAudioContext((AndroidAudio)_myAudio);
        return true;
    }

    @Override
    public boolean stop() {
        super.stop();
        activity.finishAndRemoveTask();
        return true;
    }

}
