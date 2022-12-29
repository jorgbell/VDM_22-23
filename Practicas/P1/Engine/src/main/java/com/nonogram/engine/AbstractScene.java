package com.nonogram.engine;


public abstract class AbstractScene implements Scene{
    public AbstractScene(){
    }

    @Override
    public boolean init(){
        _gameWidth = _myEngine.getSceneManager().getGameWidth();
        _gameHeight = _myEngine.getSceneManager().getGameHeight();
        return true;
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
