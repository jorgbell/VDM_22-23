package com.nonogram.logic;

import com.nonogram.engine.AbstractScene;
import com.nonogram.engine.Audio;
import com.nonogram.engine.Font;
import com.nonogram.engine.Graphics;
import com.nonogram.engine.Image;
import com.nonogram.engine.Input;


//NO TOCAR ESTA CLASE. No por nada, simplemente es una clase que he creado para ir probando las cosas del motor, hagan el juego en otras
public class PruebasScene extends AbstractScene {

    public PruebasScene(int gameWidth, int gameHeight){
        super(gameWidth,gameHeight);
    }

    @Override
    public void init() {
        graphics = _myEngine.getGraphics();
        audio = _myEngine.getAudio();
        f = graphics.newFont("JosefinSans-Bold.ttf", 60, false);
        graphics.setActualFont(f);
        i = graphics.newImage("saul.png");
        audio.newSound("saul.wav");
        audio.playSound("saul.wav");
    }

    @Override
    public void render() {
        graphics.clearGame(0xFFA800FF);
        graphics.setColor(0XFF552100);
        graphics.fillSquare(_gameWidth/2 -20,_gameHeight/2 -20,40);
        graphics.drawImage(i,_gameWidth/2 -20,_gameHeight/2 -20, 0.1);


    }

    @Override
    public void update(double deltaTime) {
        growth += 1;

    }

    @Override
    public void processInput(Input.TouchEvent input) {
        switch (input.get_type()){
            case PULSAR:
                growth *= -1;
                break;
            case SOLTAR:
                System.out.print("JOSE ALBERTO MESA GUERRERO");
                break;
        }
    }

    int growth = 0;
    Graphics graphics;
    Audio audio;
    Font f;
    Image i;
}
