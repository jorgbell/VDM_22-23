package com.nonogram.logic;

import com.nonogram.engine.AbstractScene;
import com.nonogram.engine.Font;
import com.nonogram.engine.Image;
import com.nonogram.engine.Input;
import com.nonogram.engine.Scene;

public class HistoriaScene extends AbstractScene {

    public HistoriaScene() { super(); _preferences = MenuScene._preferences;}
    @Override
    public boolean init() {
        super.init();

        _categoriesButtons = new ChangeSceneButton[_preferences.cats.length];
        _f = _myEngine.getGraphics().newFont("JosefinSans-Bold.ttf", 20, false);
        _volverImage = _myEngine.getGraphics().newImage("Arrow.png");
        _candadoImage = _myEngine.getGraphics().newImage("lock.png");
        if(_f == null || _volverImage == null)
            return false;

        _h = getGameHeight();
        _w = getGameWidth();

        ResizeElements();

        _botonVolver = new ChangeSceneButton( _w / 10, _h / 20 , _w * 2 / 7, _h / 15, null);
        _botonVolver.addText("Volver");
        _botonVolver.addImage(_volverImage,0.04, Button.ImagePos.LEFT);

        return true;
    }

    @Override
    public void render() {
        _myEngine.getGraphics().setActualFont(_f);
        _myEngine.getGraphics().setColor(LogicJSON.Palette.toInt(_preferences.unlockedPalettes.get(_preferences.actualPalette).textColor));
        _myEngine.getGraphics().drawText("Selecciona el tamaño del puzzle", _w /2, _h /5);

        _botonVolver.render(_myEngine.getGraphics());
        for(int i = 0; i < _categoriesButtons.length; i++) {

            _categoriesButtons[i].render(_myEngine.getGraphics());
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
    public void update(double deltaTime) {
        MenuScene.changeToDarkMode();

    }

    @Override
    public void processInput(Input.TouchEvent input) {
        switch (input.get_type()){
            case CLICK_CORTO:
                if(_botonVolver._rect.contains(input.get_posX(), input.get_posY())) _botonVolver.handleEvent(input);

                for(int i = 0; i < _categoriesButtons.length; i++)
                {
                    if(_categoriesButtons[i]._rect.contains(input.get_posX(), input.get_posY()) && i<= _preferences.unlockedCats-1) _categoriesButtons[i].handleEvent(input);
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

    public void increaseCat(){
        if(_preferences.unlockedCats >= _preferences.cats.length)
            return;

        _preferences.unlockedCats++;
        int i = _preferences.unlockedCats-1;
        _categoriesButtons[i].deleteImage();
        int size = _preferences.cats[i].boardSize;
        _categoriesButtons[i].addText(size + "x" +  size);

    }

    void ResizeElements()
    {
        for (int i = 0; i < _categoriesButtons.length; i++)
        {
            int x;
            int y;
            int w;

            if(!super.landscape){
                x = (_w / 20 + 150) * (1 + i % 2) - 100;
                y = _h * (1 + i / 2) / 4;
                w = _w / 4;
            }

            else {
                x =  _w / 15 + (_w * 2 / 9) * i;
                y = _h / 2;
                w = _w * 2 / 11;
            }

            if(_categoriesButtons[i] == null){
                int size = _preferences.cats[i].boardSize;
                Scene s = new CatScene(getGameWidth(), getGameHeight(), size, this, _preferences.cats[i]);
                _categoriesButtons[i] = new ChangeSceneButton(x, y, w, w, s);

                if (i > _preferences.unlockedCats-1) {
                    _categoriesButtons[i].addImage(_candadoImage,0.8, Button.ImagePos.CENTERED);
                }
                else if(i<=_preferences.unlockedCats-1){
                    _categoriesButtons[i].addText(size + "x" +  size);
                }
            }

            else _categoriesButtons[i].setDimensions(x, y, w, w);
        }
    }

    int _h;
    int _w;
    Font _f;
    Image _volverImage;
    ChangeSceneButton _botonVolver;
    Image _candadoImage;
    ChangeSceneButton[] _categoriesButtons;

    LogicJSON.PreferencesData _preferences;
}
