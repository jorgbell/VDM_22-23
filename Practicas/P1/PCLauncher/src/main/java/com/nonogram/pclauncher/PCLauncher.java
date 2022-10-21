package com.nonogram.pclauncher;

import com.nonogram.logic.PruebaScene;
import com.nonogram.pcengine.PCEngine;

public class PCLauncher {

    PCLauncher(){
        sceneinicial = new PruebaScene();
        _engine = new PCEngine("finestra",400,400, sceneinicial);
    }

    public static void main(String[] args){
        PCLauncher pclauncher = new PCLauncher();
        pclauncher._engine.resume();
    }

    PruebaScene sceneinicial;
    private final PCEngine _engine;
}
