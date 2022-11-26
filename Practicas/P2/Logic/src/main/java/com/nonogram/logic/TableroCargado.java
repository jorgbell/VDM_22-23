package com.nonogram.logic;

import com.nonogram.engine.Board;

public class TableroCargado extends Tablero{



    public TableroCargado(Board b)
    {
        super(b.getRows(), b.getCols());
        _board = b;
    }

    @Override
    public void init(){

        super.init();
        _solucion = _board.getSolution();

        for(int i = 0; i < _rowNumber; i++) completaFila(i);
        for(int i = 0; i < _columnNumber; i++) completaColumna(i);
    }

    Board _board;
}