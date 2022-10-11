package com.nonogram.pcengine;

import com.nonogram.engine.AbstractEngine;

public class PCEngine extends AbstractEngine /*implements Runnable?? preguntar sobre esto*/{

    public PCEngine(){
        super();
    }

    @Override
    public void run() {
        super.run();
        // Bucle de juego principal.
        while(_running){
            update();
            _myGraphics.render();
        }


    }

}
