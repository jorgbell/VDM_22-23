package com.nonogram.engine;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.Reader;

public interface JSONManager {

    String readJSON(String path, boolean asset);
    void writeJSON(String path, String content);
    boolean isInInternalStorage(String path);
}
