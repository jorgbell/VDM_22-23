package com.nonogram.engine;

public abstract class AbstractFont implements Font{
    protected AbstractFont(String p, int s, boolean b){
        _filePath = p;
        _size = s;
        _bold = b;
    }
    protected String _filePath;
    protected int _size;
    protected boolean _bold;
}
