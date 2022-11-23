package com.nonogram.logic;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;
import java.util.Vector;

public class TableroGenerado extends Tablero{

    public TableroGenerado(int rowNumber, int columnNumber, int percentage)
    {
        super(rowNumber, columnNumber);
        _lineasResolubles = (int)(_rowNumber * (float)percentage / 100.0);
    }

    @Override
    public void  init(){
        super.init();



        generaSolucion();
    }

    // --- Metodos de generacion de la solucion ---
    private void generaSolucion(){
        _r = new Random();

        this.eligeFilas(_rowNumber); //Elegimos las filas basicas

        //Recorremos las filas y las vamos rellenando
        for (int i = 0; i < _rowNumber; ++i) if(!_filas[i].generated) _filas[i].numbers = rellenaFila(i, 1, _columnNumber / 2, 0);

        //Leemos las columnas
        for (int i = 0; i < _columnNumber; ++i) completaColumna(i);

        //Sacams el tablero por consola
        //leeTablero();
    }

    //Metodo que genera una linea resoluble por si sola
    private void generaFilaBasica(int fila, int size)
    {
        int tipoFila;
        if(size > 5) tipoFila = _r.nextInt(5);
        else  tipoFila = _r.nextInt(3);

        String n = "";
        System.out.print("TipoFila: " + tipoFila + "\n");

        switch (tipoFila)
        {
            case 0:
                n = "0";
                break;
            case 1:
                n = String.valueOf(size);
                for(int i = 0; i < size; i++) _solucion[fila][i] = true;
                break;
            case 2:
                n = rellenaFila(fila, 1, size - 2, 1);
                break;
            case 3:
                n = rellenaFila(fila, (size / 2) - 2, size - 1, (size / 2) + 1);
                break;
            case 4:
                n = rellenaFilaMaxNum(fila, (size / 2) - 2);
                break;
            default:
                break;
        }

        _filas[fila].generated = true;
        _filas[fila].numbers = n;
        //filas[fila].numbers = "Case: " + tipoFila + "| " + n;
    }

    private String rellenaFila(int fila, int space, int maxSize, int minSize)
    {
        String n = "";
        int counter = _r.nextInt(space);
        boolean mode = true;

        while(counter < _columnNumber)
        {

            if(minSize == 0)
            {
                if(_r.nextInt(3) == 0) mode = false;
                else mode = true;
            }

            int maxBlockSize = minSize + _r.nextInt(maxSize - minSize) + 1;
            int blockSize = Math.min(maxBlockSize, (_columnNumber - counter));

            for(int i = 0; i < blockSize; i++) _solucion[fila][counter + i] = mode;

            if(mode)
            {
                if(n != "") n += ".";
                n += String.valueOf(blockSize);
            }
            counter += blockSize;

            if(counter + 1 <= _columnNumber) counter++;
        }
        if(n == "") n = "0";

        return n;
    }
    //Se asegura de que la suma de los numeros es mayor que la mitad del tamaño y al menos uno es igual al tamaño menos la suma
    private String rellenaFilaMaxNum(int fila, int space)
    {
        String n = "";
        int counter = _r.nextInt(space);

        int totalCount = (_columnNumber / 2) + 1;
        int minNum = _columnNumber - totalCount;

        boolean minNumPlaced = false;


        while(totalCount > 0 && counter < _columnNumber)
        {
            if(n != "") n += ".";

            int maxBlockSize = Math.min(minNum, _columnNumber - counter);
            int blockSize = _r.nextInt(maxBlockSize) + 1;

            if (blockSize == minNum) minNumPlaced = true;

            if(!minNumPlaced)
            {
                if(_columnNumber - (counter + 1 + blockSize) < minNum)
                {
                    blockSize = maxBlockSize;
                    minNumPlaced = true;
                }

                else
                {
                    int choose = _r.nextInt(2);
                    if (choose == 0)
                    {
                        blockSize = minNum;
                        minNumPlaced = true;
                    }
                }
            }

            for(int i = 0; i < blockSize; i++) _solucion[fila][counter + i] = true;

            counter += blockSize;
            totalCount -= blockSize;
            n += String.valueOf(blockSize);

            if(counter + 1 <= _columnNumber)
            {
                counter++;
                totalCount--;
            }
        }

        for(int i = counter; i < _columnNumber; i++) _solucion[fila][i] = false;

        return n;
    }

    //Metodo que escoge que filas serán resolubles por si solas
    private void eligeFilas(int size)
    {
        Vector<Integer> filaPool = new Vector<Integer>();

        for(int i = 0; i < size; i++) filaPool.add(i);

        for(int i = 0; i < _lineasResolubles; i++)
        {
            int elem = _r.nextInt(filaPool.size());
            int fila = filaPool.get(elem);

            generaFilaBasica(fila, _columnNumber);
            filaPool.remove(elem);
        }
    }

    int _lineasResolubles;
}
