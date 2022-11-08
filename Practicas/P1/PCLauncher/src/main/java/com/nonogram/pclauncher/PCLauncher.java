package com.nonogram.pclauncher;

import com.nonogram.logic.PruebasScene;
import com.nonogram.pcengine.PCEngine;
import com.nonogram.logic.GameScene;

public class PCLauncher {

    PCLauncher(){
<<<<<<< HEAD
        //sceneinicial = new GameScene();
        sceneinicial = new PruebasScene();
        _engine = new PCEngine("finestra",720,1080, sceneinicial);
=======
        sceneinicial = new GameScene(450,800);
        //sceneinicial = new PruebasScene();
        _engine = new PCEngine("finestra",450,800, sceneinicial);
>>>>>>> main
        _engine.init();

    }

    public static void main(String[] args){
        PCLauncher pclauncher = new PCLauncher();
        pclauncher._engine.resume();
    }

    GameScene sceneinicial;
    //PruebasScene sceneinicial;
    private final PCEngine _engine;
}
