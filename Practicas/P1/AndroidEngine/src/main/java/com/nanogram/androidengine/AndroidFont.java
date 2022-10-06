package com.nanogram.androidengine;


import android.content.res.AssetManager;
import android.graphics.Typeface;

import com.nanogram.engine.AbstractFont;

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
        return 0;
    }

    @Override
    public boolean isBold() {
        return false;
    }

    //VARIABLES
    AssetManager _assetManager;
    Typeface _font;
}
