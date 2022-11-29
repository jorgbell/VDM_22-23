package com.nonogram.logic;

import com.nonogram.engine.JSONManager;

public class TableroCargado extends Tablero{

    public TableroCargado(JSONManager.BoardData b)
    {
        super(b.Rows, b.Cols);
        _board = b;
    }

    @Override
    public void init(){

        super.init();
        _solucion = _board.Solucion;

        for(int i = 0; i < _rowNumber; i++) completaFila(i);
        for(int i = 0; i < _columnNumber; i++) completaColumna(i);
        //cargaEstadoTablero();
    }

//    private void cargaEstadoTablero() {
//        for(int i = 0; i < _rowNumber; i++) {
//            for(int j = 0; j < _columnNumber; j++){
//                _tablero[i][j].setState((State)_board.Solucion[i][j]);
//            }
//        }
//    }
//
//    public void setEstadoTablero()
//    {
//        for(int i = 0; i < _rowNumber; i++) {
//            for(int j = 0; j < _columnNumber; j++){
//                _board.Solucion _tablero[i][j].setState((State)_board.Solucion[i][j]);
//            }
//        }
//    }

    JSONManager.BoardData _board;
}