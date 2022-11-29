package com.nonogram.androidengine;

import android.content.res.AssetManager;

import com.google.gson.Gson;
import com.nonogram.engine.AbstractEngine;
import com.nonogram.engine.JSONManager;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public class AndroidJSONManager implements JSONManager {

    public AndroidJSONManager() {
        _gson = new Gson();
    }
    @Override
    public BoardData readBoardFromJSON(String path) {
        BoardData b = null;
        try {
            InputStream is = _assetManager.open(_myPaths._JSONPath + "Boards/" + path);
            Reader reader = new InputStreamReader(is);

            b = _gson.fromJson(reader, BoardData.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return b;
    }

    @Override
    public PreferencesData readPreferencesFromJSON(String path) {
        PreferencesData p = null;
        try {
            InputStream is = _assetManager.open(_myPaths._JSONPath + path);
            Reader reader = new InputStreamReader(is);

            p = _gson.fromJson(reader, PreferencesData.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return p;
    }


    AssetManager _assetManager;
    AbstractEngine.EnginePaths _myPaths;
    Gson _gson;
}