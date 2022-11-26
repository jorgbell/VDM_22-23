package com.nonogram.androidengine;

import android.content.res.AssetManager;

import com.nonogram.engine.AbstractBoard;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class AndroidBoard extends AbstractBoard {

    public AndroidBoard(String p, AssetManager ass) {
        super(p);
        _assetManager = ass;

        try {
            InputStream is = _assetManager.open(_path);
            Reader reader = new InputStreamReader(is);
            Gson gson = new Gson();

            _data = gson.fromJson(reader, JsonData.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    AssetManager _assetManager;
}