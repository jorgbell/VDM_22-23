package com.nonogram.engine;

public abstract class AbstractFont implements Font{
    protected AbstractFont(String p, int s, boolean b){
        _filePath = p;
        _size = s;
        _bold = b;
    }

    @Override
    public void setSize(int size) {
        _size = size;
    }

    @Override
    public int getSize() {
        return _size;
    }

    @Override
    public void setBold(boolean bold) {
        _bold = bold;
    }

    @Override
    public boolean isBold() {
        return _bold;
    }

    protected String _filePath;
    protected int _size;
    protected boolean _bold;
}
