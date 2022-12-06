package com.nonogram.logic;

import com.nonogram.engine.JSONManager;

public class TableroCargado extends Tablero{

    public TableroCargado(LogicJSON.BoardData b)
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
    }

    public void cargaEstadoTablero(int[][] estadoActual) {
        for(int i = 0; i < _rowNumber; i++) {
            for(int j = 0; j < _columnNumber; j++){
                int x = estadoActual[i][j];
                State s = State.fromInt(x);
                _tablero[i][j].setState(s);
            }
        }
    }

    public int[][] guardaEstadoTablero(){
        int[][]nuevoEstado = new int[_tablero.length][_tablero[0].length];
        for(int i = 0; i < _rowNumber; i++) {
            for(int j = 0; j < _columnNumber; j++){
                nuevoEstado[i][j] = State.fromState(_tablero[i][j].getState());
            }
        }
        return nuevoEstado;
    }

    LogicJSON.BoardData _board;
}