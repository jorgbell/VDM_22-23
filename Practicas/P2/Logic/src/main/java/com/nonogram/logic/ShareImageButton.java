package com.nonogram.logic;

import com.nonogram.engine.Input;
import com.nonogram.engine.IntentManager;

public class ShareImageButton extends Button{
    public ShareImageButton(int x, int y, int w, int h, LogicJSON.PreferencesData pref, IntentManager iM, String path) {
        super(x, y, w, h, pref);
        _path = path;
        _intentManager = iM;
    }

    @Override
    public void handleEvent(Input.TouchEvent e) {

        _intentManager.shareImage(_path);
    }

    String _path;
    IntentManager _intentManager;
}