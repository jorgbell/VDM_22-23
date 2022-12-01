package com.nonogram.androidengine;

import android.content.res.AssetManager;

import com.nonogram.engine.AbstractEngine;
import com.nonogram.engine.JSONManager;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public class AndroidJSONManager implements JSONManager {

    public AndroidJSONManager() {
    }
    @Override
    public Reader readJSON(String path) {
        Reader reader = null;
        try {
            InputStream is = _assetManager.open(_myPaths._JSONPath + path);
            reader = new InputStreamReader(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return reader;
    }


    AssetManager _assetManager;
    AbstractEngine.EnginePaths _myPaths;
}