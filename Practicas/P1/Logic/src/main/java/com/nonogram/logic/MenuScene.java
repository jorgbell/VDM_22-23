package com.nonogram.logic;

import com.nonogram.engine.AbstractScene;
import com.nonogram.engine.Audio;
import com.nonogram.engine.Font;
import com.nonogram.engine.Input;
import com.nonogram.engine.Scene;

public class MenuScene extends AbstractScene {

    public MenuScene(int gameWidth, int gameHeight) { super(gameWidth, gameHeight);}
    @Override
    public boolean init() {
        _h = getGameHeight();
        _w = getGameWidth();

        _f1 = _myEngine.getGraphics().newFont("JosefinSans-Bold.ttf", 20, false);
        _f2 = _myEngine.getGraphics().newFont("Molle-Regular.ttf", 40, true);

        _audio = _myEngine.getAudio();
        //audio.newSound("saul.wav");
        //audio.playSound("saul.wav");

        Scene s = new SizeScene(getGameWidth(), getGameHeight());
        _botonJugar = new ChangeSceneButton(_w/5*2, _h/2, _w / 7, _w / 7, "Jugar", _myEngine, s);
        return true;
    }

    @Override
    public void render() {
        _myEngine.getGraphics().setColor(0xFF000000);
        _myEngine.getGraphics().setActualFont(_f2);
        _myEngine.getGraphics().drawText("NONOGRAMAS", _w / 15, _h /6);

        _myEngine.getGraphics().setActualFont(_f1);
        _botonJugar.render(_myEngine.getGraphics());
    }

    @Override
    public void update(double deltaTime) {}

    @Override
    public void processInput(Input.TouchEvent input) {
        switch (input.get_type()){
            case PULSAR:
                if(_botonJugar._rect.contains(input.get_posX(), input.get_posY())) _botonJugar.handleEvent(input);
                break;
            case SOLTAR:
                break;
        }
    }

    @Override
    public boolean release() {return false;}

    int _h;
    int _w;
    Font _f1;
    Font _f2;
    Audio _audio;
    ChangeSceneButton _botonJugar;

}
