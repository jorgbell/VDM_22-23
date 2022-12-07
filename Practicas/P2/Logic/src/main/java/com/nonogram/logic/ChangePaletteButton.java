package com.nonogram.logic;

import com.nonogram.engine.Graphics;
import com.nonogram.engine.Input;

public class ChangePaletteButton extends Button{
    public ChangePaletteButton(int x, int y, int w, int h, LogicJSON.PreferencesData pref, Graphics g) {
        super(x, y, w, h, pref);
        _preferences = pref;
        _myGraphics = g;
    }


    @Override
    public void render(Graphics g) {
        if(_preferences.unlockedPalettes > 1)
            super.render(g);
    }

    @Override
    public void handleEvent(Input.TouchEvent e) {
        if(_preferences.unlockedPalettes <= 1)
            return;
            //numero maximo de paletas con el que ciclar
        int n = _preferences.unlockedPalettes; //de 1 a npaletas
        int actual = _preferences.actualPalette; // de 0 a npaletas
        int index = (actual+1)%n;
        //pasa hacia la siguiente paleta, ciclando cuando llega a la ultima desbloqueada
        _preferences.actualPalette = index;
        _myGraphics.setBGColor((int)_preferences.palettes[index].bgColor);
    }

    private LogicJSON.PreferencesData _preferences;
    private Graphics _myGraphics;
}
