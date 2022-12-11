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
    public void newTweet() {
        //TweetComposer
        Uri builtURI = Uri. parse("https://twitter.com/intent/tweet" ).buildUpon()
                .appendQueryParameter( "text", "Este es mi texto a tweettear")
                .build() ; //Genera la URl https://twitter.com/intent/tweet?text=Este%20es%20mi%20texto%20a%20tweettear
        Intent intent = new Intent(Intent. ACTION_VIEW, builtURI);
        InputStream is;
        intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File("//android_asset + _myPaths._imagesPath + \"15x15/0.png\")")));
        _context.startActivity(intent); // startActivity es un método de Context
    }

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
