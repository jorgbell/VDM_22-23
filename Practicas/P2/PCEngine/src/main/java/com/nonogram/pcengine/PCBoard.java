package com.nonogram.pcengine;

import com.nonogram.engine.AbstractBoard;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.google.gson.Gson;

public class PCBoard extends AbstractBoard {

    public PCBoard(String p) {
        super(p);

        Gson gson = new Gson();
        Path path = Paths.get(p);

        try {
            Reader reader = Files.newBufferedReader(path);
            _data = gson.fromJson(reader, AbstractBoard.JsonData.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
