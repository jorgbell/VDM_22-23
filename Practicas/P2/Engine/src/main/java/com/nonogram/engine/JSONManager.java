package com.nonogram.engine;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.Reader;

public interface JSONManager {

    String readJSON(String path);
    void writeJSON(String path, String content);
}
