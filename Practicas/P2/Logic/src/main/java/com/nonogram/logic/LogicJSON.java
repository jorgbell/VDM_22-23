package com.nonogram.logic;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nonogram.engine.Engine;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;

public class LogicJSON {
    class BoardData {
        public boolean[][] Solucion;
        public int[][] Estado;
        public int Cols;
        public int Rows;

        public BoardData(boolean[][] sol, int[][] e, int col, int row) {
            Solucion = sol;
            Estado = e;
            Cols = col;
            Rows = row;
        }
    }

    class PreferencesData {
        public int maxLifes;
        public int currentLifes;
        public int unlockedCats;
        public Category[] cats;

        public PreferencesData(int mL, int cl, int ucat, Category[] cs) {
            maxLifes = mL;
            currentLifes = cl;
            unlockedCats = ucat;
            cats = cs;
        }
    }

    class Category {
        public int boardSize;
        public int numLevels;
        public int actualLevel;

        public Category(int b, int nl, int al) {
            boardSize = b;
            numLevels = nl;
            actualLevel = al;
        }
    }

    public static BoardData readBoardFromJSON(String path) {
        String json = _myEngine.getJSONManager().readJSON("Boards/" + path);
        BoardData data = _gson.fromJson(json, boardTypeToken);
        return data;
    }

    public static PreferencesData readPreferencesFromJSON(String path) {
        String json = _myEngine.getJSONManager().readJSON(path);
        PreferencesData data = _gson.fromJson(json, preferencesTypeToken);
        return data;
    }

    public static void writeBoardToJson(String path, BoardData d) {
        String json = _gson.toJson(d, boardTypeToken);
        _myEngine.getJSONManager().writeJSON("Boards/" + path, json);
    }

    public static void writePreferencesToJson(String path, PreferencesData d) {
        String json = _gson.toJson(d, preferencesTypeToken);
        _myEngine.getJSONManager().writeJSON(path, json);
    }

    public static void set_myEngine(Engine engine) {
        _myEngine = engine;
    }

    static Engine _myEngine;
    static Gson _gson = new Gson();
    static Type boardTypeToken = new TypeToken<BoardData>() {}.getType();
    static Type preferencesTypeToken = new TypeToken<PreferencesData>() {}.getType();
    static Type categoryTypeToken = new TypeToken<Category>() {}.getType();


}
