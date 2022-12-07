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
        public int unlockedPalettes;
        public Category[] cats;
        public EstadoData estado;
        public Palette[] palettes;
        int actualPalette;

        public PreferencesData(int mL, int cl, int ucat, int upal, Category[] cs, EstadoData e, Palette[] p, int aP) {
            maxLifes = mL;
            currentLifes = cl;
            unlockedCats = ucat;
            unlockedPalettes = upal;
            cats = cs;
            palettes = p;
            actualPalette=aP;
            estado = e;
        }
        //constructor vacio que te devuelve un estado inicial de preferencias
        public PreferencesData(){
            maxLifes = 5; //de 1 a maxlifes
            currentLifes = 5; //de 0 a maxLifes-1
            unlockedCats = 1; //de 1 a ncategorias
            unlockedPalettes = 2; //de 1 a npaletas
            actualPalette = 0; //de 0 a npaletas-1
            cats = new Category[4];
            for(int i = 0; i<cats.length; i++){
                cats[i] = new Category();
            }
            cats[0].boardSize = 5;
            cats[1].boardSize = 10;
            cats[2].boardSize = 15;
            cats[3].boardSize = 20;
            estado = new EstadoData();
            palettes = new Palette[5];
            palettes[0] = new Palette("0xFFF0F0F0", "0xFF000000", "0XFFf4d35e", "0XFF2a35cc", "0XFFa4a4a4");
            palettes[1] = new Palette("0xFF343763", "0xFFFFFFFF", "0XFF854b86", "0xFF8e91bf", "0xFF56586e");
            palettes[2] = new Palette("0xFF88cb84", "0xFF000000", "0XFF81b29a", "0xFF4e784c", "0xFF749672");
            palettes[3] = new Palette("0xFFaacbda", "0xFFFFFFFF", "0XFFc65076", "0xFFc65076", "0xFFff758f");
            palettes[4] = new Palette("0xFFf1e3d3", "0xFF000000", "0XFFd88c9a", "0xFF8e7dbe", "0xFFffccd5");

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
            actualLevel = 19;
        }
    }
    static class Palette{
        public long  bgColor;
        public long  textColor;
        public long  hlColor;
        public long  pickColor;
        public long  emptyColor;


        public Palette(String b, String  t, String  h, String p, String e){
            bgColor = Long.decode(b);
            textColor=Long.decode(t);
            hlColor = Long.decode(h);
            pickColor = Long.decode(p);
            emptyColor = Long.decode(e);
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
    static Type PaletteTypeToken = new TypeToken<Palette>() {}.getType();


}
