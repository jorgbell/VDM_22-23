package com.nonogram.logic;

import java.util.Vector;
import java.util.Random;

import javax.sound.midi.SysexMessage;

public class Tablero {

    class Casilla{
        //False es cross, true es negra
        public boolean type = false;

    }

    //igual estaria bien tener una clase(struct) linea para guardar en un string los numeros que apareceran en el tablero
    class Linea
    {
        public boolean generated;
        public String numbers;

        Linea()
        {
            generated = false;
            numbers = "";
        }
    }

    Random r;
    boolean tablero[][];
    Linea filas[];
    Linea columnas[];
    int lineasResolubles = 5;


    int size;
    public Tablero(){};

    public void  init(int size){

        this.size = size;
        r = new Random();
        tablero = new boolean[size][size];
        filas = new Linea[size];
        columnas = new Linea[size];

        for(int i = 0; i < filas.length; i++) //Inicializamos los arrays de lineas
        {
            filas[i] = new Linea();
            columnas[i] = new Linea();
        }

        this.eligeFilas(size); //Elegimos las filas basicas

        //Recorremos las filas y las vamos rellenando
        for (int i = 0; i<size;++i) if(!filas[i].generated) filas[i].numbers = rellenaFila(i, 1, size / 2, 0);

        //Leemos las columnas
        for (int i = 0; i < size; ++i) completaColumna(i);

        //Sacams el tablero por consola
        leeTablero();
    }

    //Metodo que genera una linea resoluble por si sola
    private void generaFilaBasica(int fila, int size)
    {
        int tipoFila = r.nextInt(5);
        String n = "";

        switch (tipoFila)
        {
            case 0:
                n = "0";
                break;
            case 1:
                n = String.valueOf(size);
                for(int i = 0; i < size; i++) tablero[fila][i] = true;
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

        filas[fila].generated = true;
        filas[fila].numbers = n;
        //filas[fila].numbers = "Case: " + tipoFila + "| " + n;
    }

    private String rellenaFila(int fila, int space, int maxSize, int minSize)
    {
        String n = "";
        int counter = r.nextInt(space);
        boolean mode = true;

        while(counter < size)
        {

            if(minSize == 0)
            {
                if(r.nextInt(3) == 0) mode = false;
                else mode = true;
            }

            int maxBlockSize = minSize + r.nextInt(maxSize - minSize) + 1;
            int blockSize = Math.min(maxBlockSize, (size - counter));

            for(int i = 0; i < blockSize; i++) tablero[fila][counter + i] = mode;

            if(mode)
            {
                if(n != "") n += ".";
                n += String.valueOf(blockSize);
            }
            counter += blockSize;

            if(counter + 1 <= size) counter++;
        }
        if(n == "") n = "0";

        return n;
    }

    private String rellenaFilaMaxNum(int fila, int space)
    {
        String n = "";
        int counter = r.nextInt(space);

        int totalCount = (size / 2) + 1;
        int minNum = size - totalCount;

        boolean minNumPlaced = false;


        while(totalCount > 0 && counter < size)
        {
            if(n != "") n += ".";

            int maxBlockSize = Math.min(minNum, size - counter);
            int blockSize = r.nextInt(maxBlockSize) + 1;

            if (blockSize == minNum) minNumPlaced = true;

            if(!minNumPlaced)
            {
                if(size - (counter + 1 + blockSize) < minNum)
                {
                    blockSize = maxBlockSize;
                    minNumPlaced = true;
                }

                else
                {
                    int choose = r.nextInt(2);
                    if (choose == 0)
                    {
                        blockSize = minNum;
                        minNumPlaced = true;
                    }
                }
            }

            for(int i = 0; i < blockSize; i++) tablero[fila][counter + i] = true;

            counter += blockSize;
            totalCount -= blockSize;
            n += String.valueOf(blockSize);

            if(counter + 1 <= size)
            {
                counter++;
                totalCount--;
            }
        }

        for(int i = counter; i < size; i++) tablero[fila][i] = false;

        return n;
    }

    private void completaColumna(int columna)
    {
        int block = 0;
        String n = "";

        for(int j = 0; j < size; j++)
        {
            if(tablero[j][columna])
            {
                if (block == 0 && n != "") n += ".";
                block++;
            }

            else if(block != 0)
            {
                n += String.valueOf(block);
                block = 0;
            }
        }

        if(block != 0) n += String.valueOf(block);

        columnas[columna].generated = true;
        columnas[columna].numbers = n;
    }

    //Metodo que escoge que filas serán resolubles por si solas
    private void eligeFilas(int size)
    {
        Vector<Integer> filaPool = new Vector<Integer>();

        for(int i = 0; i < size; i++) filaPool.add(i);

        for(int i = 0; i < lineasResolubles; i++)
        {
            int elem = r.nextInt(filaPool.size());
            int fila = filaPool.get(elem);

            generaFilaBasica(fila, size);
            filaPool.remove(elem);
        }
    }

    private void leeTablero()
    {
        for(int i = 0; i < size; ++i)
        {
            for(int j = 0; j < size; ++j)
            {
                if(tablero[i][j]) System.out.print("X");
                else System.out.print("_");
            }

            if(filas[i].generated) System.out.print(" true");
            else System.out.print(" false");

            System.out.print(" " + filas[i].numbers);

            System.out.print("\n");
        }

        for(int j = 0; j < size; j++) System.out.print("Column " + j + ": " + columnas[j].numbers + "\n");
    }
}