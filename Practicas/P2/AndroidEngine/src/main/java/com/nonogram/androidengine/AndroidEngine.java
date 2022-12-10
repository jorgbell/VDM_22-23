package com.nonogram.androidengine;

import androidx.appcompat.app.AppCompatActivity;

import com.nonogram.engine.AbstractEngine;
import com.nonogram.engine.AbstractSensors;

public class AndroidEngine extends AbstractEngine{

    public AndroidEngine(AppCompatActivity context){
        super(new AndroidGraphics(context, context.getResources()), new AndroidInput(), new AndroidAudio(), new AndroidJSONManager(), new AndroidSensors(context), new AndroidNotificationManager(context), new AbstractEngine.EnginePaths("", "images/", "fonts/", "audio/", "JSON/"));
        AndroidGraphics aG = (AndroidGraphics) _myGraphics;
        ((AndroidAudio)_myAudio)._assetManager = aG._context.getAssets();
        ((AndroidJSONManager)_myJSONManager)._context = aG._context;
        ((AndroidJSONManager)_myJSONManager)._myPaths = _myPaths;
    }

    public AndroidNotificationManager getAndroidNotificationManager(){
        AndroidNotificationManager anmng = (AndroidNotificationManager) getNotificationManager();
        return anmng;
    }
}
