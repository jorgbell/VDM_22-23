package com.nonogram.engine;

import java.util.List;
import java.util.ArrayList;


public abstract class AbstractAudio implements Audio{


    protected AbstractAudio(){
        _loadedSounds = new ArrayList<Sound>();
    }

    @Override
    public void setPath(String audioPath) {
        _basePath = audioPath;
    }

    @Override
    public Sound getSound(String id){
        //buscamos un sonido en la lista de sonidos según su ID. si existen ID repetidas, devolvera el primero que encuentre
        //en caso de no encontrarlo, devuelve un sonido nulo
        Sound s = null;
        boolean encontrado = false;
        int i = 0;
        while(!encontrado && i < _loadedSounds.size()){
            Sound si = _loadedSounds.get(i);
            if(si._id == id){
                encontrado = true;
                s = si;
            }
            else{
                i+=1;
            }
        }

        return s;
    }

    @Override
    public void stopAll() {
        //pausa todos los sonidos que haya activos
        for (Sound s: _loadedSounds) {
            pauseSound(s._id);
        }
        _loadedSounds.clear();
    }

    protected List<Sound> _loadedSounds;
    protected String _basePath;

}
