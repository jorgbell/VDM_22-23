package com.nonogram.pclauncher;

import com.nonogram.logic.PruebasScene;
import com.nonogram.pcengine.PCEngine;
import com.nonogram.logic.GameScene;

public class PCLauncher {

    PCLauncher(){
        //sceneinicial = new GameScene();
        sceneinicial = new PruebasScene(400,400);
        _engine = new PCEngine("finestra",500,500, sceneinicial);
        _engine.init();

    }

    public static void main(String[] args){
        PCLauncher pclauncher = new PCLauncher();
        pclauncher._engine.resume();
    }

    //GameScene sceneinicial;
    PruebasScene sceneinicial;
    private final PCEngine _engine;
}
