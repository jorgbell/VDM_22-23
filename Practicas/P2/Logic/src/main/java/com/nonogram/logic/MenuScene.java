package com.nonogram.logic;

import com.nonogram.engine.AbstractScene;
import com.nonogram.engine.Font;
import com.nonogram.engine.Input;
import com.nonogram.engine.JSONManager;
import com.nonogram.engine.Scene;
import com.nonogram.engine.Sound;

public class MenuScene extends AbstractScene {

    public MenuScene(int gameWidth, int gameHeight) { super(gameWidth, gameHeight);}
    @Override
    public boolean init() {
        _preferences = _myEngine.getJSONManager().readPreferencesFromJSON("preferences.json");
        Sound sound =_myEngine.getAudio().newSound("bgm.wav");
        _f1 = _myEngine.getGraphics().newFont("JosefinSans-Bold.ttf", 20, false);
        _f2 = _myEngine.getGraphics().newFont("Molle-Regular.ttf", 40, true);
        if(sound == null || _f1 == null || _f2 == null)
            return false;

        _h = getGameHeight();
        _w = getGameWidth();
        _myEngine.getAudio().playSound("bgm.wav");
        Scene juegoScene = new DifficultyScene(getGameWidth(), getGameHeight(), _preferences);
        Scene historiaScene = new HistoriaScene(getGameWidth(), getGameHeight(), _preferences);
        _botonJugar = new ChangeSceneButton(_w/7, _h/2, _w / 3, _w / 7,  _myEngine, juegoScene);
        _botonJugar.addText("Juego Rapido");
        _botonHistoria = new ChangeSceneButton(_w/7*4, _h/2, _w / 3, _w / 7,  _myEngine, historiaScene);
        _botonHistoria.addText("Modo Historia");
        return true;
    }

    @Override
    public void render() {
        _myEngine.getGraphics().setColor(0xFF000000);
        _myEngine.getGraphics().setActualFont(_f2);
        _myEngine.getGraphics().drawText("NONOGRAMAS", _w / 2, _h /6);
        _myEngine.getGraphics().setActualFont(_f1);
        _botonJugar.render(_myEngine.getGraphics());
        _botonHistoria.render(_myEngine.getGraphics());
    }

    @Override
    public void update(double deltaTime) {}

    @Override
    public void processInput(Input.TouchEvent input) {
        switch (input.get_type()){
            case CLICK_CORTO:
                if(_botonJugar._rect.contains(input.get_posX(), input.get_posY())) _botonJugar.handleEvent(input);
                if(_botonHistoria._rect.contains(input.get_posX(), input.get_posY())) _botonHistoria.handleEvent(input);
                break;
            case CLICK_LARGO:
                break;
        }
    }

    @Override
    public boolean release() {return true;}

    int _h;
    int _w;
    Font _f1;
    Font _f2;
    ChangeSceneButton _botonJugar;
    ChangeSceneButton _botonHistoria;
    JSONManager.PreferencesData _preferences;

}
