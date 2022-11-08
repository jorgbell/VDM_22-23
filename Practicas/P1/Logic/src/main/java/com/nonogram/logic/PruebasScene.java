package com.nonogram.logic;

import com.nonogram.engine.AbstractScene;
import com.nonogram.engine.Audio;
import com.nonogram.engine.Font;
import com.nonogram.engine.Graphics;
import com.nonogram.engine.Image;
import com.nonogram.engine.Input;

import java.util.Stack;


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
        f2 = graphics.newFont("Molle-Regular.ttf", 40, true);
        i = graphics.newImage("saul.png");
        audio.newSound("saul.wav");
        audio.playSound("saul.wav");
        return true;
    }

    @Override
    public void render() {
        graphics.clearGame(0XFF225500);
        graphics.setColor(0XFF2100FF);
        graphics.fillRect(graphics.getGameWidth()/2 -20,graphics.getGameHeight()/2 -20,31,31);
        graphics.setActualFont(f2);
        graphics.drawText("Title Text Sample",graphics.getGameWidth()/3, graphics.getGameHeight()/2 -40);
        graphics.setActualFont(f);
        graphics.drawText("Normal text sample",graphics.getGameWidth()/3, graphics.getGameHeight()/2 +80);
        graphics.drawImage(i,0,1, 0.3,0.3);
        graphics.fillRect(graphics.getGameWidth()/20,graphics.getGameHeight()/20,40,40);

    }

    @Override
    public void update(double deltaTime) {

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
}
