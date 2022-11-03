package com.nonogram.logic;

import com.nonogram.engine.AbstractScene;
import com.nonogram.engine.Font;
import com.nonogram.engine.Graphics;
import com.nonogram.engine.Image;
import com.nonogram.engine.Input;

//NO TOCAR ESTA CLASE. No por nada, simplemente es una clase que he creado para ir probando las cosas del motor, hagan el juego en otras
public class PruebasScene extends AbstractScene {

    public PruebasScene(){
    }

    @Override
    public void init() {
        graphics = _myEngine.getGraphics();
        f = graphics.newFont("JosefinSans-Bold.ttf", 60, false);
        graphics.setActualFont(f);
        i = graphics.newImage("saul.png");
    }

    @Override
    public void render() {
        graphics.clear(0xFFA800FF);
        graphics.setColor(0XFF552100);
        graphics.fillSquare(graphics.getWindowWidth()/2, graphics.getWindowHeight()/2, 80 + growth);
        graphics.drawLine(graphics.getWindowWidth()/2, graphics.getWindowHeight()/2, graphics.getWindowWidth(), graphics.getWindowHeight());
        graphics.setColor(0XFFFFFFFF);
        graphics.drawSquare(graphics.getWindowWidth()/4, graphics.getWindowHeight()/4, 40 + growth);
        graphics.drawText("SAMPLE TEXT", 120, 120);
        graphics.drawImage(i, 120,120);
    }

    @Override
    public void update(double deltaTime) {
        growth += 1;

    }

    @Override
    public void processInput(Input.TouchEvent input) {
        switch (input.get_type()){
            case PULSAR:
                System.out.println("AND THE OSCAR GOES TO");
                System.out.println("x: " + input.get_posX() + "/y: " + input.get_posY() + "/ID: " + input.get_touchID());
                break;
            case SOLTAR:
                System.out.println("JOSE ALBERTO MESA GUERRERO");
                break;
        }
    }

    int growth = 0;
    Graphics graphics;
    Font f;
    Image i;
}
