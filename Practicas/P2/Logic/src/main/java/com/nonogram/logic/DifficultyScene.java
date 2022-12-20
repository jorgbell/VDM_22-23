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
        super.init();

        _volverImage = _myEngine.getGraphics().newImage("Arrow.png");

        _h = getGameHeight();
        _w = getGameWidth();
        //Crea los botones de los diferentes tableros

        ResizeElements();

        if(_f == null || _volverImage == null)
            return false;

        return true;
    }

    @Override
    public void render() {
        _myEngine.getGraphics().setActualFont(_f);
        _myEngine.getGraphics().setColor(LogicJSON.Palette.toInt(_preferences.unlockedPalettes.get(_preferences.actualPalette).textColor));
        _myEngine.getGraphics().drawText("Selecciona la dificultad del puzzle", _w /2, textY);

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
    public void rotate() {
        super.rotate();
        _w = super.getGameWidth();
        _h = super.getGameHeight();

        ResizeElements();
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
    @Override
    public void ResizeElements()
    {

        _h = getGameHeight();
        _w = getGameWidth();
        for (int i = 0; i < _botonesSizes.length; i++)
        {
            int x;
            int y;
            int w;

            if(!super.landscape){
                x = _w * 1 / 3;
                y = _h * 1 / 4 + _h / 4 * i;
                w = _w / 3;
                textY=_h /6;

                _f = _myEngine.getGraphics().newFont("JosefinSans-Bold.ttf", _w / 15 , false);
            }

            else {
                x = _w / 15 + _w * 5 / 16 *  i;
                y = _h * 5 / 12;
                w = _w / 4;

                textY=_h /4;

                _f = _myEngine.getGraphics().newFont("JosefinSans-Bold.ttf", _w / 20 , false);
            }

            if(_botonesSizes[i] == null){
                //todo: igual meter que dependiendo del numero de sizes se dispongan mas o menos en la misma fila?
                int percentage = 60 - 20 * i;
                Scene s = new SizeScene(percentage);
                _botonesSizes[i] = new ChangeSceneButton(x, y, w, w, s);
                _botonesSizes[i].addText(_difficulties[i]);
            }

            else _botonesSizes[i].setDimensions(x, y, w, w);
        }

        ChangeSceneButton auxVolver;

        if(!super.landscape) auxVolver = new ChangeSceneButton( _w / 10, _h / 20 , _w * 2 / 7, _h / 15, null);

        else auxVolver = new ChangeSceneButton( _w / 20, _h / 15 , _w  / 7, _h / 10, null);

        if(_botonVolver == null) {
            _botonVolver = auxVolver;
            _botonVolver.addImage(_volverImage,0.04, Button.ImagePos.LEFT);
            _botonVolver.addText("Volver");
        }

        else _botonVolver.setDimensions(auxVolver._rect._x, auxVolver._rect._y, auxVolver._rect._w, auxVolver._rect._h);
    }

    int _h;
    int _w;
    Font _f;
    int textY;

    Image _volverImage;
    String[] _difficulties ={"Facil", "Normal", "Dificil"}; //el minimo es 5x5
    ChangeSceneButton _botonVolver;
    ChangeSceneButton[] _botonesSizes = new ChangeSceneButton[_difficulties.length];
    LogicJSON.PreferencesData _preferences;
}