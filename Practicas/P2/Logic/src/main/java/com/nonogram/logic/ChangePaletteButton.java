package com.nonogram.logic;

import com.nonogram.engine.Engine;
import com.nonogram.engine.Graphics;
import com.nonogram.engine.Input;

public class ChangePaletteButton extends Button{
    public ChangePaletteButton(int x, int y, int w, int h, LogicJSON.PreferencesData pref, Engine e) {
        super(x, y, w, h, pref);
        _preferences = pref;
        _myEngine = e;
        _myGraphics = e.getGraphics();
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

        if(index == 0){
            if(_myEngine.getSensors().isDark())
                _preferences.palettes[0] = _preferences.dark;
            else
                _preferences.palettes[0] = _preferences.light;
        }

        //pasa hacia la siguiente paleta, ciclando cuando llega a la ultima desbloqueada
        _preferences.actualPalette = index;
        _myGraphics.setBGColor((int)_preferences.palettes[index].bgColor);
    }

    private LogicJSON.PreferencesData _preferences;
    private Graphics _myGraphics;
    private Engine _myEngine;
}
