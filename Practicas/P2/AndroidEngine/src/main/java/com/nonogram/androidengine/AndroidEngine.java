package com.nonogram.androidengine;

import android.app.Activity;
import android.view.SurfaceView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdView;
import com.nonogram.engine.AbstractEngine;
import com.nonogram.engine.AbstractSensors;

public class AndroidEngine extends AbstractEngine{

    public AndroidEngine(AppCompatActivity context, SurfaceView view){
        super(new AndroidGraphics(context, view), new AndroidInput(), new AndroidAudio(), new AndroidJSONManager(), new AndroidAdManager(context), new AndroidSensors(context), new AndroidNotificationManager(context),  new AndroidIntentManager(), new AbstractEngine.EnginePaths("", "images/", "fonts/", "audio/", "JSON/"));
        AndroidGraphics aG = (AndroidGraphics) _myGraphics;
        ((AndroidAudio)_myAudio)._assetManager = aG._context.getAssets();
        ((AndroidJSONManager)_myJSONManager)._context = aG._context;
        ((AndroidJSONManager)_myJSONManager)._myPaths = _myPaths;
        ((AndroidIntentManager)_myIntentManager)._context = aG._context;
        ((AndroidIntentManager)_myIntentManager)._myPaths = _myPaths;
    }

    public AndroidNotificationManager getAndroidNotificationManager(){
        AndroidNotificationManager anmng = (AndroidNotificationManager) getNotificationManager();
        return anmng;
    }

    @Override
    public boolean stop() {
        super.stop();
        activity.finishAndRemoveTask();
        return true;
    }
}
