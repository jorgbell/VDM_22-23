package com.nonogram.pclauncher;

import com.nonogram.logic.MenuScene;
import com.nonogram.pcengine.PCEngine;

public class PCLauncher {

    PCLauncher(){
        sceneinicial = new MenuScene(450,800);
        _engine = new PCEngine("finestra",450,800);
        //manejo de errores: si se crea mal algo, para antes de empezar.
        if(!_engine.init() || !_engine.getSceneManager().push(sceneinicial)){
            _engine.stop();
        }
    }

    public static void main(String[] args){
        PCLauncher pclauncher = new PCLauncher();
        pclauncher._engine.resume();
    }


    MenuScene sceneinicial;
    private final PCEngine _engine;
}
