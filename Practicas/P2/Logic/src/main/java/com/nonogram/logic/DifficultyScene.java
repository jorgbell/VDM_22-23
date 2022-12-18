package com.nonogram.logic;

import com.nonogram.engine.AbstractScene;
import com.nonogram.engine.Font;
import com.nonogram.engine.Image;
import com.nonogram.engine.Input;
import com.nonogram.engine.Scene;


public class DifficultyScene extends AbstractScene {

    public DifficultyScene() { super(); _preferences = MenuScene._preferences; }

    @Override
    public boolean init() {
        _f = _myEngine.getGraphics().newFont("JosefinSans-Bold.ttf", 15 * SCALE, false);
        _volverImage = _myEngine.getGraphics().newImage("Arrow.png");
        if(_f == null || _volverImage == null)
            return false;

        _h = getGameHeight();
        _w = getGameWidth();
        //Crea los botones de los diferentes tableros
        for (int i = 0; i < _botonesSizes.length; i++)
        {
            //todo: igual meter que dependiendo del numero de sizes se dispongan mas o menos en la misma fila?
            int percentage = 60 - 20 * i;
            Scene s = new SizeScene(percentage);
            _botonesSizes[i] = new ChangeSceneButton((_w / 20 + 120) * (1 + i) - 115, _h / 3, _w / 4, _w / 4, s);
            _botonesSizes[i].addText(_difficulties[i]);
        }
        _botonVolver = new ChangeSceneButton( _w / 10, _h / 20 , _w * 2 / 7, _h / 15, null);
        _botonVolver.addImage(_volverImage,0.04, Button.ImagePos.LEFT);
        _botonVolver.addText("Volver");

        return true;
    }

    @Override
    public void render() {
        _myEngine.getGraphics().setActualFont(_f);
        _myEngine.getGraphics().setColor(LogicJSON.Palette.toInt(_preferences.unlockedPalettes.get(_preferences.actualPalette).textColor));
        _myEngine.getGraphics().drawText("Selecciona la dificultad del puzzle", _w /2, _h /5);

        _botonVolver.render(_myEngine.getGraphics());
        for(int i = 0; i < _botonesSizes.length; i++) _botonesSizes[i].render(_myEngine.getGraphics());
    }

    @Override
    public void update(double deltaTime) {
        MenuScene.changeToDarkMode();
    }

    @Override
    public void processInput(Input.TouchEvent input) {
        switch (input.get_type()){
            case CLICK_CORTO:
                if(_botonVolver._rect.contains(input.get_posX(), input.get_posY())) _botonVolver.handleEvent(input);

                for(int i = 0; i < _botonesSizes.length; i++)
                {
                    if(_botonesSizes[i]._rect.contains(input.get_posX(), input.get_posY())) _botonesSizes[i].handleEvent(input);
                }
                break;
            case CLICK_LARGO:
                break;
        }
    }

    @Override
    public boolean release() {
        persist();
        return true;
    }

    @Override
    public boolean persist() {
        LogicJSON.writePreferencesToJson("preferences.json", _preferences);
        return false;
    }

    @Override
    public void handleClosingNotifications() {

    }

    @Override
    public void handleOpeningNotifications() {

    }

    int _h;
    int _w;
    Font _f;
    Image _volverImage;
    String[] _difficulties ={"Facil", "Normal", "Dificil"}; //el minimo es 5x5
    ChangeSceneButton _botonVolver;
    ChangeSceneButton[] _botonesSizes = new ChangeSceneButton[_difficulties.length];
    LogicJSON.PreferencesData _preferences;
}