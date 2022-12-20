package com.nonogram.androidengine;

import android.content.Context;
import android.content.res.AssetManager;

import com.nonogram.engine.AbstractEngine;
import com.nonogram.engine.JSONManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class AndroidJSONManager implements JSONManager {

    public AndroidJSONManager() {
    }

    @Override
    public String readJSON(String path, boolean asset) {
        String json = "";
        if (asset) {
            //reads from asset folder (apk)
            InputStream stream = null;
            try {
                stream = _context.getAssets().open(_myPaths._JSONPath + path);
                int size = stream.available();
                byte[] buffer = new byte[size];
                stream.read(buffer);
                stream.close();
                json = new String(buffer);

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            //reads from internal storage
            try {
                File file = new File(_context.getFilesDir() + "/" + _myPaths._JSONPath, path); //carpeta de archivos/JSON/preferences.json , por ejemplo
                FileReader r = new FileReader(file);
                // Reads the file
                int i;
                while ((i = r.read()) != -1)
                    json += (char) i;
                r.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return json;
    }

    @Override
    public void writeJSON(String path, String json) {
        try {
            File file = null;
            File dir = new File(_context.getFilesDir(), _myPaths._JSONPath);
            if (!isInInternalStorage(path)) {
                if (!dir.exists())
                    dir.mkdir();
                file = new File(dir, path);
            }
            else {
                file = new File(_context.getFilesDir() + "/" + _myPaths._JSONPath, path);
            }
            FileWriter w = new FileWriter(file);
            w.append(json);
            w.flush();
            w.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @Override
    public boolean isInInternalStorage(String path) {
        File file = new File(_context.getFilesDir() + "/" + _myPaths._JSONPath, path);
        if (file.exists()) {
            return true;
        }
        return false;
    }


    @Override
    public String getChecksum(String json) {

        if(!isInInternalStorage(json)){
            return null;
        }

        File file = new File(_context.getFilesDir() + "/" + _myPaths._JSONPath, json); //carpeta de archivos/JSON/preferences.json , por ejemplo
        //Get file input stream for reading the file content
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        //Create byte array to read data in chunks
        byte[] byteArray = new byte[1024];
        int bytesCount = 0;

        //Use MD5 algorithm
        MessageDigest md5Digest = null;
        try {
            md5Digest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        //Read file data and update in message digest
        while (true) {
            try {
                if (!((bytesCount = fis.read(byteArray)) != -1)) break;
            } catch (IOException e) {
                e.printStackTrace();
            }
            md5Digest.update(byteArray, 0, bytesCount);
        };

        //close the stream; We don't need it now.
        try {
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Get the hash's bytes
        byte[] bytes = md5Digest.digest();

        //This bytes[] has bytes in decimal format;
        //Convert it to hexadecimal format
        StringBuilder sb = new StringBuilder();
        for(int i=0; i< bytes.length ;i++)
        {
            sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
        }

        //return complete hash
        return sb.toString();
    }

    Context _context;
    AbstractEngine.EnginePaths _myPaths;
}