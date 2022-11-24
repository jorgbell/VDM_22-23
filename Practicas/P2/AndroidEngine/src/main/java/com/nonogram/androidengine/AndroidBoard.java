package com.nonogram.androidengine;

import android.content.res.AssetManager;

import com.nonogram.engine.AbstractBoard;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class AndroidBoard extends AbstractBoard {

    public AndroidBoard(String p, AssetManager ass) {
        super(p);

        Gson gson = new Gson();
        Path path = Paths.get(_file);

        try {
            Reader reader = Files.newBufferedReader(path);
            JsonData d = gson.fromJson(reader, JsonData.class);
            super.setSizes(d.Rows, d.Cols);
            super.init();
            _solucion = d.Solucion;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}