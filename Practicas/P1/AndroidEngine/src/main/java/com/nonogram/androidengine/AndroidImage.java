package com.nonogram.androidengine;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.nonogram.engine.AbstractImage;

import java.io.IOException;
import java.io.InputStream;

public class AndroidImage extends AbstractImage {
    public AndroidImage(String p, AssetManager ass) {
        super(p);
        _assetManager = ass;
        try (InputStream is = _assetManager.open(_path)) {
            _bitmap = BitmapFactory.decodeStream(is);
            _width = _bitmap.getWidth();
            _height = _bitmap.getHeight();
        } catch (IOException e) {
            System.err.println("No se puede cargar la imagen: " + e);
        }

    }

    public Bitmap _getBitmap() {
        return _bitmap;
    }
    @Override
    public int getWidth() {
        return _bitmap.getWidth();
    }

    @Override
    public int getHeight() {
        return _bitmap.getHeight();
    }


    //VARIABLES
    AssetManager _assetManager;
    Bitmap _bitmap;
}
