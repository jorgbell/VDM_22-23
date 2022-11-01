package com.nonogram.logic;

import com.nonogram.engine.AbstractScene;
import com.nonogram.engine.Graphics;

//NO TOCAR ESTA CLASE. No por nada, simplemente es una clase que he creado para ir probando las cosas del motor, hagan el juego en otras
public class PruebasScene extends AbstractScene {

    public PruebasScene(){
    }

    @Override
    public void init() {
        graphics = _myEngine.getGraphics();

    }

    @Override
    public void render() {
        graphics.clear(0xFFA800FF);
        graphics.setColor(0XFF552100);
        graphics.fillSquare(graphics.getWindowWidth()/2, graphics.getWindowHeight()/2, 80 + growth);
        graphics.drawLine(graphics.getWindowWidth()/2, graphics.getWindowHeight()/2, graphics.getWindowWidth(), graphics.getWindowHeight());
        graphics.setColor(0XFF016584);
        graphics.drawSquare(graphics.getWindowWidth()/4, graphics.getWindowHeight()/4, 40 + growth);

    }

    @Override
    public void update(double deltaTime) {
        growth += 1;
    }

    int growth = 0;
    Graphics graphics;
}
