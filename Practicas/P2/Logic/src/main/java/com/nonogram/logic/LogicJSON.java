package com.nonogram.logic;

import com.google.gson.Gson;
import com.nonogram.engine.Engine;

import java.io.Reader;

public class LogicJSON {
    class BoardData
    {
        public boolean[][] Solucion;
        public int[][] Estado;
        public int Cols;
        public int Rows;

        public BoardData(boolean[][] sol, int[][] e, int col, int row)
        {
            Solucion = sol;
            Estado = e;
            Cols = col;
            Rows = row;
        }
    }

    class PreferencesData{
        public int maxLifes;
        public int currentLifes;
        public int unlockedCats;
        public Category[] cats;

        public PreferencesData(int mL, int cl, int ucat, Category[] cs){
            maxLifes = mL;
            currentLifes = cl;
            unlockedCats = ucat;
            cats = cs;
        }
    }

    class Category{
        public int boardSize;
        public int numLevels;
        public int actualLevel;

        public Category(int b, int nl, int al){
            boardSize = b;
            numLevels = nl;
            actualLevel = al;
        }
    }

    public static BoardData readBoardFromJSON(String path){
        Reader r = _myEngine.getJSONManager().readJSON("Board/"+path);
        BoardData data = _gson.fromJson(r,BoardData.class);
        return data;
    }
    public static PreferencesData readPreferencesFromJSON(String path){
        Reader r = _myEngine.getJSONManager().readJSON(path);
        PreferencesData data = _gson.fromJson(r,PreferencesData.class);
        return data;
    }

    public static void set_myEngine(Engine engine){_myEngine = engine;}

    static Engine _myEngine;
    static Gson _gson = new Gson();

}
