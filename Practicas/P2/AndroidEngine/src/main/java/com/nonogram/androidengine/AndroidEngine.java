package com.nonogram.androidengine;

import android.view.SurfaceView;

import androidx.appcompat.app.AppCompatActivity;

import com.nonogram.engine.AbstractEngine;

public class AndroidEngine extends AbstractEngine {

    public AndroidEngine(AppCompatActivity context, SurfaceView view){
        super(new AndroidGraphics(context, view), new AndroidInput(), new AndroidAudio(), new AndroidJSONManager(), new AndroidAdManager(context), new AbstractEngine.EnginePaths("", "images/", "fonts/", "audio/", "JSON/"));
        AndroidGraphics aG = (AndroidGraphics) _myGraphics;
        ((AndroidAudio)_myAudio)._assetManager = aG._context.getAssets();
        ((AndroidJSONManager)_myJSONManager)._assetManager = aG._context.getAssets();
        ((AndroidJSONManager)_myJSONManager)._myPaths = _myPaths;
    }
}
