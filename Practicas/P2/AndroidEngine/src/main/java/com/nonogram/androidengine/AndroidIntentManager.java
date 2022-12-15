package com.nonogram.androidengine;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import com.google.firebase.crashlytics.buildtools.reloc.org.apache.commons.io.IOUtils;
import com.nonogram.engine.AbstractEngine;
import com.nonogram.engine.IntentManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class AndroidIntentManager implements IntentManager {

    public AndroidIntentManager(){};

    @Override
    public void shareImage(String imagePath) {

        Bitmap bitmap = null;
        try {
            InputStream iS = _context.getAssets().open(_myPaths._imagesPath + imagePath);
             bitmap = BitmapFactory.decodeStream(iS);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String path = MediaStore.Images.Media.insertImage(_context.getContentResolver(), bitmap, "Nonograms", "Imagen de dificultad");
        Uri uri = Uri.parse(path);
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setAction(Intent.ACTION_SEND);

        shareIntent.setType("image/jpeg");
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri);

        _context.startActivity(Intent.createChooser(shareIntent, "Share Image"));
    }

    Context _context;
    public AbstractEngine.EnginePaths _myPaths;
}
