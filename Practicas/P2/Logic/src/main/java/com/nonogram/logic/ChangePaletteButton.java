package com.nonogram.logic;

import com.nonogram.engine.Engine;
import com.nonogram.engine.Graphics;
import com.nonogram.engine.Input;

import java.awt.Menu;

public class ChangePaletteButton extends Button {
    public ChangePaletteButton(int x, int y, int w, int h) {
        super(x, y, w, h);
        _preferences = MenuScene._preferences;
        _myGraphics = _engine.getGraphics();
        _text = _preferences.unlockedPalettes.get(_preferences.actualPalette).id;
    }


    @Override
    public void render(Graphics g) {
        if (_preferences.unlockedPalettes.size() > 1) {
            super.render(g);
        }
    }

    @Override
    public void handleEvent(Input.TouchEvent e) {
        if (_preferences.unlockedPalettes.size() <= 1)
            return;
        //numero maximo de paletas con el que ciclar
        int n = _preferences.unlockedPalettes.size(); //de 1 a npaletas
        int actual = _preferences.actualPalette; // de 0 a npaletas
        //pasa hacia la siguiente paleta, ciclando cuando llega a la ultima desbloqueada
        int index = (actual + 1) % n;
        _preferences.actualPalette = index;
        //comprueba si debe cambiar a modo oscuro/claro
        if (!MenuScene.changeToDarkMode()) {
            _myGraphics.setBGColor(LogicJSON.Palette.toInt(_preferences.unlockedPalettes.get(index).bgColor));
            _text = _preferences.unlockedPalettes.get(_preferences.actualPalette).id;
        }


    }

    private Graphics _myGraphics;
}
