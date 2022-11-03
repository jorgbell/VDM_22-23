package com.nonogram.androidengine;

import com.nonogram.engine.AbstractAudio;
import com.nonogram.engine.Sound;

public class AndroidAudio extends AbstractAudio {
    @Override
    public Sound newSound(String fileName) {
        AndroidSound sA = new AndroidSound(fileName, _basePath);
        return sA;
    }

    @Override
    public Sound playSound(String id) {
        AndroidSound sA = (AndroidSound) getSound(id);
        if(sA != null){

        }
        return sA;
    }
}
