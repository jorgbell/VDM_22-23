package com.nanogram.engine;

public abstract class AbstractImage implements Image{

    @Override
    public int getWidth() {
        return _width;
    }

    @Override
    public int getHeight() {
        return _height;
    }
    //VARIABLES
    int _width;
    int _height;
}
