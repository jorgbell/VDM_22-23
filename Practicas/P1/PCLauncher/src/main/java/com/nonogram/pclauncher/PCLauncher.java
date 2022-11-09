package com.nonogram.pclauncher;

import com.nonogram.logic.PruebasScene;
import com.nonogram.logic.SizeScene;
import com.nonogram.pcengine.PCEngine;
import com.nonogram.logic.GameScene;

public class PCLauncher {

    PCLauncher(){
        sceneinicial = new SizeScene(450,800);
        _engine = new PCEngine("finestra",450,800);
        _engine.init();
        _engine.getSceneManager().push(sceneinicial);

    }

    public static void main(String[] args){
        PCLauncher pclauncher = new PCLauncher();
        pclauncher._engine.resume();
    }


    SizeScene sceneinicial;
    private final PCEngine _engine;
}
