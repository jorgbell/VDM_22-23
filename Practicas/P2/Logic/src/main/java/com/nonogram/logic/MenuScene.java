package com.nonogram.logic;

import com.nonogram.engine.AbstractScene;
import com.nonogram.engine.Engine;
import com.nonogram.engine.Font;
import com.nonogram.engine.Input;
import com.nonogram.engine.NotificationData;
import com.nonogram.engine.Scene;
import com.nonogram.engine.Sound;

public class MenuScene extends AbstractScene {

    public MenuScene(int gameWidth, int gameHeight) {
        super(gameWidth, gameHeight);
    }
    public MenuScene() {
        super();
    }

    @Override
    public boolean init() {
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
        _f1 = _myEngine.getGraphics().newFont("JosefinSans-Bold.ttf", 15, false);
        _f2 = _myEngine.getGraphics().newFont("Molle-Regular.ttf", 30, true);
        if (sound == null || _f1 == null || _f2 == null)
            return false;

        _h = getGameHeight();
        _w = getGameWidth();
        _myEngine.getAudio().playSound("bgm.wav");
        juegoScene = new DifficultyScene();
        historiaScene = new HistoriaScene();
        _botonJugar = new ChangeSceneButton(_w / 7, _h / 2, _w / 3, _w / 7, juegoScene);
        _botonJugar.addText("Juego Rapido");
        _botonHistoria = new ChangeSceneButton(_w / 7 * 4, _h / 2, _w / 3, _w / 7, historiaScene);
        _botonHistoria.addText("Modo Historia");
        _botonPaletas = new ChangePaletteButton(_w / 3, _h / 4, _w / 3, _w / 7);

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
        _myEngine.getGraphics().drawText(String.valueOf(_myEngine.getSensors().getTemperature()) + "°C", _w / 2, _h / 5);
    }

    @Override
    public void rotate() {
        super.rotate();

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
        int unlock = (levelPlayed+1)%UNLOCK_PALETTE_EVERY;
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
                if (_botonJugar._rect.contains(input.get_posX(), input.get_posY()))
                    _botonJugar.handleEvent(input);
                if (_botonHistoria._rect.contains(input.get_posX(), input.get_posY()))
                    _botonHistoria.handleEvent(input);
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

    int _h;
    int _w;
    Font _f1;
    Font _f2;
    ChangeSceneButton _botonJugar;
    ChangeSceneButton _botonHistoria;
    Scene juegoScene, historiaScene;
    static ChangePaletteButton _botonPaletas;
    public static LogicJSON.PreferencesData _preferences;
    public static LogicJSON.Palette[] allPalettes;
    static int UNLOCK_PALETTE_EVERY = 1;
    boolean changed = false;


}
