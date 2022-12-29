package com.nonogram.pclauncher;

import com.nonogram.logic.MenuScene;
import com.nonogram.pcengine.PCEngine;

public class PCLauncher {

    PCLauncher(){
        sceneinicial = new MenuScene();
        _engine = new PCEngine("Nonograma",450,800, false);
        //manejo de errores: si se crea mal algo, para antes de empezar.
        if(!_engine.init()){
            _engine.close();
        }
        _engine.getSceneManager().setGameSize(450,800);
        MenuScene sceneinicial = new MenuScene();
        if(!_engine.getSceneManager().push(sceneinicial)){
            _engine.close();
        }
    }

    public static void main(String[] args){
        PCLauncher pclauncher = new PCLauncher();
        pclauncher._engine.resume();
    }


    MenuScene sceneinicial;
    private final PCEngine _engine;
}
