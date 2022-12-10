package com.nonogram.engine;


public abstract class AbstractScene implements Scene{
    public AbstractScene(int gameWidth, int gameHeight){
        _gameWidth = gameWidth;
        _gameHeight = gameHeight;
    }

    @Override
    public void setGameWidth(int w) {
        _gameWidth = w;
    }

    @Override
    public void setGameHeight(int h) {
        _gameHeight = h;
    }

    @Override
    public void setEngine(Engine e) {
        _myEngine = e;
    }
    @Override
    public int getGameWidth(){ return _gameWidth;}
    @Override
    public int getGameHeight(){return _gameHeight;}
    public static Engine getEngine(){return _myEngine;}

    protected static Engine _myEngine;
    private int _gameWidth, _gameHeight;
}
