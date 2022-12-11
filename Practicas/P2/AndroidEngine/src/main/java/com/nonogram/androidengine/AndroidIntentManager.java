package com.nonogram.androidengine;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;

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

    public void shareImage() {
        String msg = "Texto";
        Uri uri = Uri
                .parse(Environment.getExternalStorageDirectory() + "/Pictures/0/IMG_20221207_214156.jpeg");
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setType("image/*");
//        intent.putExtra(Intent.EXTRA_TEXT, msg);
//        intent.setType("text/plain");
//        intent.putExtra(Intent.EXTRA_STREAM, uri);
//        intent.setType("image/png");
//        intent.setPackage("com.twitter.android");
        //intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        _context.startActivity(intent);
    }

//    @Override
//    public void shareImage() {
//        File targetFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Pictures/1.png");
//        try {
//            InputStream iS = _context.getAssets().open(_myPaths._imagesPath + "15x15/1.png");
//            if (targetFile.createNewFile())
//            {
//                byte[] aByte = IOUtils.toByteArray(iS);
//                FileOutputStream fos = new FileOutputStream(targetFile);
//                IOUtils.write(aByte, fos);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        //File iS = new File("file:///android_asset/" + _myPaths._imagesPath + "15x15/0.png");
//        Uri path = Uri.fromFile(targetFile);
//        Intent shareIntent = new Intent(Intent. ACTION_VIEW, path);
//        shareIntent.setAction(Intent.ACTION_SEND);
//        //shareIntent.putExtra(Intent.EXTRA_TEXT, "Testing");
//        //shareIntent.putExtra(Intent.EXTRA_STREAM, path);
//        //shareIntent.setType("image/*");
//        shareIntent.addFlags(Intent.);
//        _context.startActivity(shareIntent);
//    }

    Context _context;
    public AbstractEngine.EnginePaths _myPaths;
}
