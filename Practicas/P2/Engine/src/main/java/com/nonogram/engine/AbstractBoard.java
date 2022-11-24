package com.nonogram.engine;


public class AbstractBoard implements Board {

    protected class JsonData
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

    protected AbstractBoard(String s){
        //codigo de la carga de tablero
        _path = s;
    }

    protected String _path;

    @Override
    public int getRows() {
        return _data.Rows;
    }

    @Override
    public int getCols() {
        return _data.Cols;
    }

    @Override
    public boolean[][] getSolution() {
        return _data.Solucion;
    }

    JsonData _data;
}
