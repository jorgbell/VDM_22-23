package com.nonogram.logic;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TableroCargado extends Tablero{

    class JsonData
    {
        boolean[][] Solucion;
        int Cols;
        int Rows;

        public JsonData(boolean[][] sol, int col, int row)
        {
            Solucion = sol;
            Cols = col;
            Rows = row;
        }
    }

    public TableroCargado(String file)
    {
        super(0, 0);
        _file = file;
    }

    @Override
    public void init(){

        gson = new Gson();
        Path p = Paths.get(_file);
        JsonData d;

        try {
            Reader reader = Files.newBufferedReader(p);
            d = gson.fromJson(reader, JsonData.class);
            super.setSizes(d.Rows, d.Cols);
            super.init();
            _solucion = d.Solucion;
        } catch (IOException e) {
            e.printStackTrace();
        }

        for(int i = 0; i < _rowNumber; i++) completaFila(i);
        for(int i = 0; i < _columnNumber; i++) completaColumna(i);
    }

    Gson gson;
    String _file;
}