package com.nonogram.engine;

public interface JSONManager {
    class BoardData
    {
        public boolean[][] Solucion;
        public int Cols;
        public int Rows;

        public BoardData(boolean[][] sol, int col, int row)
        {
            Solucion = sol;
            Cols = col;
            Rows = row;
        }
    }

    BoardData readBoardFromJSON(String path);
}
