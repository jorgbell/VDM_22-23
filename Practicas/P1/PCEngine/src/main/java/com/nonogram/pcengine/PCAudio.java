package com.nonogram.pcengine;

import com.nonogram.engine.AbstractAudio;
import com.nonogram.engine.Sound;

public class PCAudio extends AbstractAudio {

    public PCAudio(){
        super();
    }

    @Override
    public Sound newSound(String fileName) {
        PCSound sPC = new PCSound(fileName, _basePath);
        _loadedSounds.add(sPC);
        return sPC;
    }

    @Override
    public Sound playSound(String id) {
        PCSound sPC = (PCSound) getSound(id);
        if(sPC != null){
            sPC._clip.start();
        }
        return sPC;
    }
}
