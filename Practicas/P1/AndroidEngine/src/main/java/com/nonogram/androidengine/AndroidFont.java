package com.nonogram.androidengine;


import android.content.res.AssetManager;
import android.graphics.Typeface;

import com.nonogram.engine.AbstractFont;

public class AndroidFont extends AbstractFont {

    public AndroidFont(String p, int s, boolean b, AssetManager ass){
        super(p,s,b);
        _assetManager = ass;
        try{
            _font = Typeface.createFromAsset(_assetManager, _filePath);
        }
        catch (Exception e) {
            System.err.println("Error cargando la fuente: " + e);
        }
    }


    @Override
    public int getSize() {
        return _size;
    }

    @Override
    public boolean isBold() {
        return _bold;
    }

    //VARIABLES
    AssetManager _assetManager;
    Typeface _font;
}
