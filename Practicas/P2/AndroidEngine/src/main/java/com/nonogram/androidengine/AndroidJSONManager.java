package com.nonogram.androidengine;

import android.content.Context;
import android.content.res.AssetManager;

import com.nonogram.engine.AbstractEngine;
import com.nonogram.engine.JSONManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public class AndroidJSONManager implements JSONManager {

    public AndroidJSONManager() {
    }

    @Override
    public String readJSON(String path, boolean asset) {
        String json = "";
        if (asset) {
            //reads from asset folder (apk)
            InputStream stream = null;
            try {
                stream = _context.getAssets().open(_myPaths._JSONPath + path);
                int size = stream.available();
                byte[] buffer = new byte[size];
                stream.read(buffer);
                stream.close();
                json = new String(buffer);

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            //reads from internal storage
            try {
                File file = new File(_context.getFilesDir() + "/" + _myPaths._JSONPath, path); //carpeta de archivos/JSON/preferences.json , por ejemplo
                FileReader r = new FileReader(file);
                // Reads the file
                int i;
                while ((i = r.read()) != -1)
                    json += (char) i;
                r.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return json;
    }

    @Override
    public void writeJSON(String path, String json) {
        try {
            File file = null;
            File dir = new File(_context.getFilesDir(), _myPaths._JSONPath);
            if (!isInInternalStorage(path)) {
                if (!dir.exists())
                    dir.mkdir();
                file = new File(dir, path);
            }
            else {
                file = new File(_context.getFilesDir() + "/" + _myPaths._JSONPath, path);
            }
            FileWriter w = new FileWriter(file);
            w.append(json);
            w.flush();
            w.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean isInInternalStorage(String path) {
        File file = new File(_context.getFilesDir() + "/" + _myPaths._JSONPath, path);
        if (file.exists()) {
            return true;
        }
        return false;
    }


    Context _context;
    AbstractEngine.EnginePaths _myPaths;
}