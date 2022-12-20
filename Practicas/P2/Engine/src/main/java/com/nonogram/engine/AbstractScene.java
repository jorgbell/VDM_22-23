package com.nonogram.engine;


public abstract class AbstractScene implements Scene{
    public AbstractScene(int gameWidth, int gameHeight){
//        _gameWidth = gameWidth;
//        _gameHeight = gameHeight;
//        if(_gameHeight<_gameWidth)
//            landscape = true;
//        System.out.println("Constructor 1: " + landscape);
    }
    public AbstractScene(){
//        _gameWidth = _myEngine.getSceneManager().getGameWidth();
//        _gameHeight = _myEngine.getSceneManager().getGameHeight();
//            if(_gameHeight<_gameWidth)
//                landscape = true;
//        System.out.println("Constructor 2: " + landscape);
    }

    @Override
    public boolean init(){
        _gameWidth = _myEngine.getSceneManager().getGameWidth();
        _gameHeight = _myEngine.getSceneManager().getGameHeight();
        if(_gameHeight<_gameWidth)
            landscape = true;
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
