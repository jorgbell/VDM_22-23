package com.nonogram.logic;

import java.util.Vector;
import java.util.Random;

public class Tablero {
    public Tablero() {};

    enum State { //Estados de las casillas
        EMPTY(0), PICK(1), CROSS(2), WRONG(3);
        private final int value;
        State(int value) {
            this.value = value;
        }
        public int getValue() {
            return value;
        }
    }

    class Casilla{ //casilla logica
        int x=-1, y=-1;
        State estado = State.EMPTY;

        public Casilla(int i, int j)
        {
            x = j;
            y = i;
        }

        public void setState(State s)
        {
            estado = s;
            if(estado == State.PICK) _blues.add(this);
            else if(estado == State.WRONG) _wrongs.add(this);
            else _blues.remove(this);
        };

        public State getState() { return estado; };
    }

    class Linea //Numeros de una fila/columna y si está generada
    {
        public boolean generated;
        public String numbers;

        Linea()
        {
            generated = false;
            numbers = "";
        }
    }

    public void  init(int size){
        this._size = size;

        _tablero = new Casilla[size][size];
        _blues = new Vector<Casilla>();
        _wrongs = new Vector<Casilla>();

        for (int i =0; i<size;++i){ for (int j =0; j<size;++j) _tablero[i][j] = new Casilla(i,j); }

        generaSolucion();
    }

    /*
    - addPick / remove -> estos metodos son publicos, se llaman desde el button casilla
    */

    //public Boolean getSolution(int i, int j) { return _solucion[i][j]; }
    public Casilla getCasilla(int i, int j) { return _tablero[i][j]; }

    public int getRemaining() //cuenta las casillas que faltan por seleccionarse (azul)
    {
        int total = 0;
        int correctCount = 0;

        for(int i = 0; i < _size; i++) for(int j = 0; j < _size; j++)
        {
            if(_solucion[i][j])
            {
                total++;
                if(_tablero[i][j].getState() == State.PICK) correctCount++;
            }
        }
        return total - correctCount;
    }

    public int ComprobarTablero() //coge la lista de casillas en azul y las compara con la solucion
    {
        for(Casilla c : _blues) if(!_solucion[c.y][c.x]) { c.setState(State.WRONG); }
        return _wrongs.size();
    }

    public void LimpiarErrores() { //quita de la lista de seleccionadas las erroneas y las elimina de la lista wrong

        for(Casilla c : _wrongs)
        {
            c.setState(State.EMPTY);
            _blues.remove(c);
        }

        _wrongs.clear();
    }

    // --- Metodos de generacion de la solucion ---
    private void generaSolucion(){
        _r = new Random();
        _solucion = new boolean[_size][_size];
        _filas = new Linea[_size];
        _columnas = new Linea[_size];

        for(int i = 0; i < _filas.length; i++) //Inicializamos los arrays de lineas
        {
            _filas[i] = new Linea();
            _columnas[i] = new Linea();
        }

        this.eligeFilas(_size); //Elegimos las filas basicas

        //Recorremos las filas y las vamos rellenando
        for (int i = 0; i< _size; ++i) if(!_filas[i].generated) _filas[i].numbers = rellenaFila(i, 1, _size / 2, 0);

        //Leemos las columnas
        for (int i = 0; i < _size; ++i) completaColumna(i);

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

        while(counter < _size)
        {

            if(minSize == 0)
            {
                if(_r.nextInt(3) == 0) mode = false;
                else mode = true;
            }

            int maxBlockSize = minSize + _r.nextInt(maxSize - minSize) + 1;
            int blockSize = Math.min(maxBlockSize, (_size - counter));

            for(int i = 0; i < blockSize; i++) _solucion[fila][counter + i] = mode;

            if(mode)
            {
                if(n != "") n += ".";
                n += String.valueOf(blockSize);
            }
            counter += blockSize;

            if(counter + 1 <= _size) counter++;
        }
        if(n == "") n = "0";

        return n;
    }
    //Se asegura de que la suma de los numeros es mayor que la mitad del tamaño y al menos uno es igual al tamaño menos la suma
    private String rellenaFilaMaxNum(int fila, int space)
    {
        String n = "";
        int counter = _r.nextInt(space);

        int totalCount = (_size / 2) + 1;
        int minNum = _size - totalCount;

        boolean minNumPlaced = false;


        while(totalCount > 0 && counter < _size)
        {
            if(n != "") n += ".";

            int maxBlockSize = Math.min(minNum, _size - counter);
            int blockSize = _r.nextInt(maxBlockSize) + 1;

            if (blockSize == minNum) minNumPlaced = true;

            if(!minNumPlaced)
            {
                if(_size - (counter + 1 + blockSize) < minNum)
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

            if(counter + 1 <= _size)
            {
                counter++;
                totalCount--;
            }
        }

        for(int i = counter; i < _size; i++) _solucion[fila][i] = false;

        return n;
    }
    //Genera las columnas viendo las filas
    private void completaColumna(int columna)
    {
        int block = 0;
        String n = "";

        for(int j = 0; j < _size; j++)
        {
            if(_solucion[j][columna])
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
        if(n == "") n= "0";

        _columnas[columna].generated = true;
        _columnas[columna].numbers = n;
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

            generaFilaBasica(fila, size);
            filaPool.remove(elem);
        }
    }

    private void leeTablero() //para sacarlo por consola
    {
        for(int i = 0; i < _size; ++i)
        {
            for(int j = 0; j < _size; ++j)
            {
                if(_solucion[i][j]) System.out.print("X");
                else System.out.print("_");
            }

            if(_filas[i].generated) System.out.print(" true");
            else System.out.print(" false");

            System.out.print(" " + _filas[i].numbers);

            System.out.print("\n");
        }

        for(int j = 0; j < _size; j++) System.out.print("Column " + j + ": " + _columnas[j].numbers + "\n");
    }

    Random _r;
    Casilla _tablero[][];
    Vector<Casilla> _blues;
    Vector<Casilla> _wrongs;
    private boolean _solucion[][];
    Linea _filas[];
    Linea _columnas[];
    int _lineasResolubles = 5;
    int _size;
}