package com.nonogram.engine;


public abstract class AbstractScene implements Scene{

    public AbstractScene(){
    }

    @Override
    public boolean init(){
        _gameWidth = _myEngine.getSceneManager().getGameWidth();
        _gameHeight = _myEngine.getSceneManager().getGameHeight();
        if(_gameHeight<_gameWidth)
            landscape = true;
        else
            landscape =false;

        System.out.println("init: " +  landscape);
        return true;
    }

    @Override
    public void rotate() {
        int aux = _gameWidth;
        _gameWidth = _gameHeight;
        _gameHeight = aux;
        landscape = !landscape;
        System.out.println("rotate: " + landscape);

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
    private int _gameWidth = -1, _gameHeight = -1;
    protected int SCALE;
    protected boolean landscape = false;
}
