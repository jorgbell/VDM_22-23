package com.nonogram.engine;

public interface JSONManager {
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

    BoardData readBoardFromJSON(String path);
    PreferencesData readPreferencesFromJSON(String path);
}
