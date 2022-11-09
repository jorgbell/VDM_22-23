package com.nonogram.engine;

//clase que abstrae los atributos comunes de los sonidos
public abstract class Sound {
    public Sound(String id, String fileName){
        _id = id;
        _fileName = fileName;
    }

    protected String _id;
    protected String _fileName;
}
