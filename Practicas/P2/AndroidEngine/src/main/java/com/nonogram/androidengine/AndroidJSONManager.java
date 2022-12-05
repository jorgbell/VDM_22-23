package com.nonogram.androidengine;

import android.content.Context;
import android.content.res.AssetManager;

import com.nonogram.engine.AbstractEngine;
import com.nonogram.engine.JSONManager;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public class AndroidJSONManager implements JSONManager {

    public AndroidJSONManager() {
    }
    @Override
    public String readJSON(String path) {
        FileInputStream reader = null;
        String json = "";
        try {
            String fullpath = _context.getFilesDir().getPath() + path;
            reader = _context.openFileInput(_myPaths._JSONPath + path);//carpeta de archivos/JSON/preferences.json , por ejemplo
            // Reads the file
            json = reader.toString();
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json;
    }

    @Override
    public void writeJSON(String path, String json) {
        FileOutputStream writer = null;
        try{
            writer = _context.openFileOutput(_myPaths._JSONPath + path, Context.MODE_PRIVATE);//carpeta de archivos/JSON/preferences.json , por ejemplo
            writer.write(json.getBytes());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    Context _context;
    AbstractEngine.EnginePaths _myPaths;
}