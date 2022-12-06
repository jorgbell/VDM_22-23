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
import java.util.ArrayList;
import java.util.List;

public class LogicJSON {
    static class BoardData {
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

    static class EstadoData{
        public int size;
        public int level;
        public int[][] Estado;
        public EstadoData(int s, int l, int[][] e){
            size = s;
            level = l;
            Estado = e;
        }
        //constructor vacio que te devuelve un estado inicial de preferencias
        public EstadoData(){
            size = -1;
            level = -1;
            Estado = new int[0][0];
        }
    }

    static class PreferencesData {
        public int maxLifes;
        public int currentLifes;
        public int unlockedCats;
        public Category[] cats;
        public EstadoData estado;
        public Palette[] palettes;

        public PreferencesData(int mL, int cl, int ucat, Category[] cs, EstadoData e, Palette[] p) {
            maxLifes = mL;
            currentLifes = cl;
            unlockedCats = ucat;
            cats = cs;
            palettes = p;
            estado = e;
        }
        //constructor vacio que te devuelve un estado inicial de preferencias
        public PreferencesData(){
            maxLifes = 5;
            currentLifes = 5;
            unlockedCats = 0;
            cats = new Category[4];
            for(int i = 0; i<cats.length; i++){
                cats[i] = new Category();
            }
            cats[0].boardSize = 5;
            cats[1].boardSize = 10;
            cats[2].boardSize = 15;
            cats[3].boardSize = 20;
            estado = new EstadoData();
        }
    }

    static class Category {
        public int boardSize;
        public int numLevels;
        public int actualLevel;

        public Category(int b, int nl, int al) {
            boardSize = b;
            numLevels = nl;
            actualLevel = al;
        }
        //constructor vacio que te devuelve un estado inicial de categoria
        public Category(){
            numLevels = 20;
            actualLevel = 0;
        }
    }
    class Palette{
        public String bgColor;
        public String textColor;
        public String hlColor;
        public String pickColor;
        public String emptyColor;


        public Palette(String b, String  t, String  h, String p, String e){
            bgColor = b;
            textColor= t;
            pickColor = p;
            hlColor = h;
            emptyColor = e;
        }
    }

    
    public static BoardData readBoardFromJSON(String path){
        String json = _myEngine.getJSONManager().readJSON("Boards/" + path, true);
        BoardData data = _gson.fromJson(json, boardTypeToken);
        return data;
    }

    public static PreferencesData readPreferencesFromJSON(String path) {
        PreferencesData data = null;
        //si el archivo existe, lo lee
        if(_myEngine.getJSONManager().isInInternalStorage(path)){
            String json = _myEngine.getJSONManager().readJSON(path, false);
            data = _gson.fromJson(json, preferencesTypeToken);
        }
        //si no, crea un inicial que se guardará en el internal storage posteriormente, cuando se produzca el guardado de datos
        else{
            data = new PreferencesData();
        }
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
