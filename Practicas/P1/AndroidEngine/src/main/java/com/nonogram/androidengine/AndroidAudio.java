package com.nonogram.androidengine;

import android.content.res.AssetManager;

import com.nonogram.engine.AbstractAudio;
import com.nonogram.engine.Sound;

public class AndroidAudio extends AbstractAudio {
    @Override
    public Sound newSound(String fileName) {
        AndroidSound sA = new AndroidSound(fileName, _basePath, _assetManager);
        _loadedSounds.add(sA);
        return sA;
    }

    @Override
    public Sound playSound(String id) {
        AndroidSound sA = (AndroidSound) getSound(id);
        if(sA != null){
            sA._clip.start();
        }
        return sA;
    }

    @Override
    public void pauseSound(String id) {
        AndroidSound sA = (AndroidSound) getSound(id);
        if(sA != null){
            sA._clip.pause();
        }
    }

    AssetManager _assetManager;
}
