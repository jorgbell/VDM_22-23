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
        LogicJSON.set_myEngine(_myEngine);
        _preferences = LogicJSON.readPreferencesFromJSON("preferences.json");
        for(int i=0; i<_preferences.palettes.length; i++){
            LogicJSON.Palette _palette = _preferences.palettes[i];
            _myEngine.getGraphics().addPalette(_palette.bgColor,_palette.textColor,_palette.hlColor,_palette.pickColor,_palette.emptyColor);
        }
        _myEngine.getGraphics().setPalette(1);
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
        _myEngine.getGraphics().setColor(_myEngine.getGraphics().getTextColor());
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
    LogicJSON.PreferencesData _preferences;


}
