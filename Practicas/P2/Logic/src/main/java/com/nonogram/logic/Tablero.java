package com.nonogram.logic;

import java.util.Vector;
import java.util.Random;

public abstract class Tablero {
    public Tablero(int rowNumber, int columnNumber) {
        _rowNumber = rowNumber;
        _columnNumber = columnNumber;
    };

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

    public void init(){
        _tablero = new Casilla[_rowNumber][_columnNumber];
        _blues = new Vector<Casilla>();
        _wrongs = new Vector<Casilla>();
        _solucion = new boolean[_rowNumber][_columnNumber];
        _filas = new Linea[_rowNumber];
        _columnas = new Linea[_columnNumber];

        for (int i =0; i < _rowNumber; ++i){ for (int j = 0; j < _columnNumber; ++j) _tablero[i][j] = new Casilla(i,j); }

        //Inicializamos los arrays de lineas
        for(int i = 0; i < _filas.length; i++) _filas[i] = new Linea();
        for(int i = 0; i < _columnas.length; i++) _columnas[i] = new Linea();

    }

    /*
    - addPick / remove -> estos metodos son publicos, se llaman desde el button casilla
    */

    protected void completaFila(int fila)
    {
        int block = 0;
        String n = "";

        for(int i = 0; i < _columnNumber; i++)
        {
            if(_solucion[fila][i])
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

        _filas[fila].generated = true;
        _filas[fila].numbers = n;
    }

    //Genera las columnas viendo las filas
    protected void completaColumna(int columna)
    {
        int block = 0;
        String n = "";

        for(int j = 0; j < _rowNumber; j++)
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

    public void setSizes(int rowNumber, int columnNumber)
    {
        _rowNumber = rowNumber;
        _columnNumber = columnNumber;
    }

    //public Boolean getSolution(int i, int j) { return _solucion[i][j]; }
    public Casilla getCasilla(int i, int j) { return _tablero[i][j]; }

    public int getRemaining() //cuenta las casillas que faltan por seleccionarse (azul)
    {
        int total = 0;
        int correctCount = 0;

        for(int i = 0; i < _rowNumber; i++) for(int j = 0; j < _columnNumber; j++)
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

    protected void leeTablero() //para sacarlo por consola
    {
        for(int i = 0; i < _columnNumber; ++i)
        {
            for(int j = 0; j < _rowNumber; ++j)
            {
                if(_solucion[i][j]) System.out.print("X");
                else System.out.print("_");
            }

            if(_filas[i].generated) System.out.print(" true");
            else System.out.print(" false");

            System.out.print(" " + _filas[i].numbers);

            System.out.print("\n");
        }

        for(int j = 0; j < _rowNumber; j++) System.out.print("Column " + j + ": " + _columnas[j].numbers + "\n");
    }

    Random _r;
    protected Casilla _tablero[][];
    protected Vector<Casilla> _blues;
    protected Vector<Casilla> _wrongs;
    protected boolean _solucion[][];
    protected Linea _filas[];
    protected Linea _columnas[];
    protected int _rowNumber;
    protected int _columnNumber;
}