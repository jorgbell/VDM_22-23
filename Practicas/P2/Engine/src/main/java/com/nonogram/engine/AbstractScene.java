package com.nonogram.engine;


public abstract class AbstractScene implements Scene{
    public AbstractScene(int gameWidth, int gameHeight){
        _gameWidth = gameWidth;
        _gameHeight = gameHeight;
    }

    @Override
    public void setEngine(Engine e) {
        _myEngine = e;
    }
    @Override
    public int getGameWidth(){ return _gameWidth;}
    @Override
    public int getGameHeight(){return _gameHeight;}

    protected Engine _myEngine;
    private int _gameWidth, _gameHeight;
}
