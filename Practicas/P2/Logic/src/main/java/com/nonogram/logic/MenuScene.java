package com.nonogram.logic;

import com.nonogram.engine.AbstractScene;
import com.nonogram.engine.Engine;
import com.nonogram.engine.Font;
import com.nonogram.engine.Input;
import com.nonogram.engine.NotificationData;
import com.nonogram.engine.Scene;
import com.nonogram.engine.Sound;

public class MenuScene extends AbstractScene {
    public MenuScene() {
        super();
    }

    @Override
    public boolean init() {
        super.init();

        LogicJSON.set_myEngine(_myEngine);
        _preferences = LogicJSON.readPreferencesFromJSON("preferences.json");
        LogicJSON.writePreferencesToJson("preferences.json", _preferences);
        allPalettes = LogicJSON.readAllPalettesFromJSON("palettes.json");
        if (_preferences.unlockedPalettes.size() == 0) {
            //añade la paleta default en caso de no tenerlas guardadas de una partida anterior
            LogicJSON.Palette.AddPaletteToList(_preferences.unlockedPalettes, allPalettes[0]);
        }

        _myEngine.getGraphics().setBGColor(LogicJSON.Palette.toInt(_preferences.unlockedPalettes.get(_preferences.actualPalette).bgColor));
        Sound sound = _myEngine.getAudio().newSound("bgm.wav");
        _f2 = _myEngine.getGraphics().newFont("Molle-Regular.ttf", 30, true);

        _h = getGameHeight();
        _w = getGameWidth();

        juegoScene = new DifficultyScene();
        historiaScene = new HistoriaScene();

        ResizeElements();

        if (sound == null || _f1 == null || _f2 == null)
            return false;

        _myEngine.getAudio().playSound("bgm.wav");


        return true;
    }

    @Override
    public void render() {
        _myEngine.getGraphics().setColor(LogicJSON.Palette.toInt(_preferences.unlockedPalettes.get(_preferences.actualPalette).textColor));
        _myEngine.getGraphics().setActualFont(_f2);
        _myEngine.getGraphics().drawText("NONOGRAMAS", _w / 2, _h / 6);
        _myEngine.getGraphics().setActualFont(_f1);
        _botonJugar.render(_myEngine.getGraphics());
        _botonHistoria.render(_myEngine.getGraphics());
        _botonPaletas.render(_myEngine.getGraphics());
        _botonAd.render(_myEngine.getGraphics());
        _myEngine.getGraphics().drawText(String.valueOf(_myEngine.getSensors().getTemperature()) + "°C", _w / 2, tempY);
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
        changeToDarkMode();
    }

    public static boolean changeToDarkMode() {
        int index = _preferences.actualPalette;
        if (index == 0) {
            //index = default palette
            if (_myEngine.getSensors().isDark()) {
                _preferences.unlockedPalettes.set(0, allPalettes[1]);
                _myEngine.getGraphics().setBGColor(LogicJSON.Palette.toInt(_preferences.unlockedPalettes.get(_preferences.actualPalette).bgColor));
                _botonPaletas.addText(_preferences.unlockedPalettes.get(_preferences.actualPalette).id);
            } else {
                _preferences.unlockedPalettes.set(0, allPalettes[0]);
                _myEngine.getGraphics().setBGColor(LogicJSON.Palette.toInt(_preferences.unlockedPalettes.get(_preferences.actualPalette).bgColor));
                _botonPaletas.addText(_preferences.unlockedPalettes.get(_preferences.actualPalette).id);
            }
            return true;
        }
        return false;
    }

    public static boolean UnlockNewPalette(int levelPlayed) {
        //desbloqueara una paleta cada UNLOCK_PALETTE_EVERY niveles
        int unlock = (levelPlayed + 1) % UNLOCK_PALETTE_EVERY;
        if (_preferences.unlockedPalettes.size() < allPalettes.length - 1 && unlock == 0) { //restamos uno ya que la paleta default son dos paletas (light y dark)
            //siempre tendrá como minimo una paleta desbloqueada (default), por lo que se puede acceder a la nueva paleta haciendo unlocked.size()+1
            //ejemplo: tiene 1 paleta desbloqueada (light y dark, posiciones 0 y 1 de allpaletes), por lo que se accede a la siguiente con allPaletes[2]
            LogicJSON.Palette.AddPaletteToList(_preferences.unlockedPalettes, allPalettes[_preferences.unlockedPalettes.size() + 1]);
            return true;
        }
        return false;
    }

    @Override
    public void processInput(Input.TouchEvent input) {
        switch (input.get_type()) {
            case CLICK_CORTO:
                if (_botonJugar._rect.contains(input.get_posX(), input.get_posY())){
                    _botonJugar.handleEvent(input);
                    //_myEngine.getAdManager().deleteBanner();
                }
                if (_botonHistoria._rect.contains(input.get_posX(), input.get_posY())){
                    _botonHistoria.handleEvent(input);
                    //_myEngine.getAdManager().deleteBanner();
                }
                if (_botonPaletas._rect.contains(input.get_posX(), input.get_posY())) {
                    _botonPaletas.handleEvent(input);
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
        _myEngine.addClosingNotification(new NotificationData("Nonogramas", "TE ECHAMOS DE MENOS!", "Entra ahora y consigue una vida extra GRATIS. Ven y disfruta de nuestros Nonogramas. Bombón", 2));
    }

    @Override
    public void handleOpeningNotifications() {
        _preferences.currentLifes++;
    }

    @Override
    public void handleAdd() {
        _preferences.currentLifes++;
    }

    @Override
    public void ResizeElements(){
        super.ResizeElements();

        _h = getGameHeight();
        _w = getGameWidth();

        ChangeSceneButton auxJugar;
        ChangeSceneButton auxHistoria;
        ChangePaletteButton auxPaletas;
        RewarderButton auxAd;

        if(!super.landscape){
            auxJugar = new ChangeSceneButton(_w / 4, _h * 9 / 20, _w / 2, _h / 10, juegoScene);
            auxHistoria = new ChangeSceneButton(_w / 4, _h * 6 / 20, _w / 2, _h / 10, historiaScene);
            auxPaletas = new ChangePaletteButton(_w / 4, _h * 12 / 20, _w / 2, _h / 10);
            auxAd = new RewarderButton(_w / 4, _h * 15 / 20, _w / 2, _h / 10);

            _f1 = _myEngine.getGraphics().newFont("JosefinSans-Bold.ttf", _w / 15, false);
            tempY=_h / 5;
        }

        else{
            auxJugar = new ChangeSceneButton(_w * 1 / 16, _h * 7 / 20, _w * 2 / 5, _w / 9, juegoScene);
            auxHistoria = new ChangeSceneButton(_w * 3 / 5, _h * 7 / 20, _w * 2 / 5, _w / 9, historiaScene);
            auxPaletas = new ChangePaletteButton(_w * 1 / 16, _h * 13 / 20, _w * 2 / 5, _w / 9);
            auxAd = new RewarderButton(_w * 3 / 5, _h * 13 / 20, _w * 2 / 5, _w / 9);

            _f1 = _myEngine.getGraphics().newFont("JosefinSans-Bold.ttf", _w / 25, false);
            tempY=_h *2/9;
        }

        if(_botonJugar == null) {
            _botonJugar = auxJugar;
            _botonJugar.addText("Juego Rapido");
        }

        else _botonJugar.setDimensions(auxJugar._rect._x, auxJugar._rect._y, auxJugar._rect._w, auxJugar._rect._h);

        if(_botonHistoria == null) {
            _botonHistoria = auxHistoria;
            _botonHistoria.addText("Modo Historia");
        }

        else _botonHistoria.setDimensions(auxHistoria._rect._x, auxHistoria._rect._y, auxHistoria._rect._w, auxHistoria._rect._h);

        if(_botonPaletas == null){
            _botonPaletas = auxPaletas;}

        else _botonPaletas.setDimensions(auxPaletas._rect._x, auxPaletas._rect._y, auxPaletas._rect._w, auxPaletas._rect._h);

        if(_botonAd == null){
            _botonAd = auxAd;
            _botonAd.addText("+1 Vida");
        }

        else _botonAd.setDimensions(auxAd._rect._x, auxAd._rect._y, auxAd._rect._w, auxAd._rect._h);
    }

    int _h;
    int _w;
    Font _f1;
    Font _f2;
    int tempY;
    ChangeSceneButton _botonJugar;
    ChangeSceneButton _botonHistoria;
    RewarderButton _botonAd;
    Scene juegoScene, historiaScene;
    static ChangePaletteButton _botonPaletas;
    public static LogicJSON.PreferencesData _preferences;
    public static LogicJSON.Palette[] allPalettes;
    static int UNLOCK_PALETTE_EVERY = 5;
    boolean changed = false;


}
