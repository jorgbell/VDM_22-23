package com.nanogram.logic;

public class Tablero {

    class Casilla{
        //False es cross, true es negra
        boolean type=false;

    }

    //igual estaria bien tener una clase(struct) linea para guardar en un string los numeros que apareceran en el tablero

    int size;
    public Tablero(){};
    public void  init(int size){
        boolean linea[] = new boolean[size];
        boolean tablero[][] = new boolean[size][size];
        //recorremos las filas y las vamos rellenando
        for (int i =0; i<size;++i){}
        //leemos las columnas
        for (int i =0; i<size;++i){}
    }

    private void rellenaFila(){}
    private void completaColumna(){}

}
