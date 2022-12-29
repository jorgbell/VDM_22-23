package com.nonogram.pcengine;

import com.nonogram.engine.AbstractAudio;
import com.nonogram.engine.Sound;

public class PCAudio extends AbstractAudio {

    public PCAudio() {
        super();
    }

    @Override
    public Sound newSound(String fileName) {
        PCSound sPC = null;
        try {
            sPC = new PCSound(fileName, _basePath);
            _loadedSounds.add(sPC);
        }catch(Exception e){
        }

        return sPC;
    }

    @Override
    public Sound playSound(String id) {
        PCSound sPC = (PCSound) getSound(id);
        if (sPC != null) {
            sPC._clip.start();
        }
        else{
            System.err.println("No se encuentra el sonido entre la lista de sonidos cargados");
        }
        return sPC;
    }

    @Override
    public void pauseSound(String id) {
        PCSound sPC = (PCSound) getSound(id);
        if (sPC != null) {
            sPC._clip.stop();
        }else{
            System.err.println("No se encuentra el sonido entre la lista de sonidos cargados");
        }
    }
}
