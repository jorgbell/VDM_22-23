package com.nonogram.logic;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TableroCargado extends Tablero{



    public TableroCargado(String file)
    {
        super(0, 0);
        _file = file;
    }

    @Override
    public void init(){



        for(int i = 0; i < _rowNumber; i++) completaFila(i);
        for(int i = 0; i < _columnNumber; i++) completaColumna(i);
    }

    Gson gson;
    String _file;
}