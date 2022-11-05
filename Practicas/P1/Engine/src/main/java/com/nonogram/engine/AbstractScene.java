package com.nonogram.engine;

import java.util.List;

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

    @Override
    public void getInput() {
        List<Input.TouchEvent> inputList = _myEngine.getInput().getTouchEvents();
        while(inputList.size()>0){//mientras haya input que procesar
            Input.TouchEvent aux = inputList.get(0); //cogemos el primero a procesar
            inputList.remove(0); //lo borramos de la lista
            processInput(aux); //lo procesamos
        }
    }
    protected Engine _myEngine;
    protected int _gameWidth, _gameHeight;
}
