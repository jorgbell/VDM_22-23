package com.nonogram.logic;

import com.nonogram.engine.AbstractScene;
import com.nonogram.engine.Audio;
import com.nonogram.engine.Font;
import com.nonogram.engine.Graphics;
import com.nonogram.engine.Image;
import com.nonogram.engine.Input;


//NO TOCAR ESTA CLASE. No por nada, simplemente es una clase que he creado para ir probando las cosas del motor, hagan el juego en otras
public class PruebasScene extends AbstractScene {

    public PruebasScene(){
        super(720,1080);
    }

    @Override
    public boolean init() {
        graphics = _myEngine.getGraphics();
        audio = _myEngine.getAudio();
        f = graphics.newFont("JosefinSans-Bold.ttf", 20, false);
        f2 = graphics.newFont("Molle-Regular.ttf", 80, true);
        i = graphics.newImage("saul.png");
        audio.newSound("saul.wav");
        audio.playSound("saul.wav");


        f.setBold(true);
        f.setSize(60);


        f.setBold(false);
        f2.setSize(20);
        return true;
    }

    @Override
    public void render() {
        graphics.clearGame(0XFF225500);
        graphics.setColor(0XFF2100FF);
        graphics.setActualFont(f);
        graphics.drawText("BOLD HUGE TEXT", getGameWidth()/2, 40);

        graphics.setActualFont(f2);
        graphics.drawText("am potat", getGameWidth()/2, getGameHeight()/2);


    }

    @Override
    public void update(double deltaTime) {
        lastTime += deltaTime;
//        if (lastTime > 5 && !stopped){
//            audio.pauseSound("saul.wav");
//            stopped = true;
//        }
    }

    @Override
    public void processInput(Input.TouchEvent input) {
        switch (input.get_type()){
            case PULSAR:
                System.out.println("FIXED X: " + input.get_posX() + "/ FIXED Y: " + input.get_posY());
                break;
            case SOLTAR:
                _myEngine.getSceneManager().pop();
                break;
        }
    }

    @Override
    public boolean release() {
        return true;
    }

    Graphics graphics;
    Audio audio;
    Font f;
    Font f2;
    Image i;
    double lastTime = 0;
    boolean stopped = false;
}
