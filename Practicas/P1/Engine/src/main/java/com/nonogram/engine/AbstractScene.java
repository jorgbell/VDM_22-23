package com.nonogram.engine;

import java.util.List;

public abstract class AbstractScene implements Scene{
    @Override
    public void setEngine(Engine e) {
        _myEngine = e;
    }
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
}
