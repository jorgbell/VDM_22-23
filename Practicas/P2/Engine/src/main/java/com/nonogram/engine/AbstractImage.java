package com.nonogram.engine;

public abstract class AbstractImage implements Image{

    protected AbstractImage(String s){
        _path = s;
    }

    @Override
    public int getWidth() {
        return _width;
    }

    @Override
    public int getHeight() {
        return _height;
    }
    //VARIABLES
    protected int _width;
    protected int _height;
    protected String _path;
}
