package com.nonogram.pcengine;

import com.nonogram.engine.AbstractGraphics;
import com.nonogram.engine.Font;
import com.nonogram.engine.Image;
import com.nonogram.engine.Input;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;


public class PCGraphics extends AbstractGraphics {

    public PCGraphics(String windowName, int w, int h, boolean fullScreen) {     //el w y el h igual hay que tocarlos, ahora mismo son el de la ventana
        super();
        //inicializar JFrame
        _myView = new JFrame(windowName);
        _myView.setSize(w, h);
        _myView.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        _myView.setIgnoreRepaint(true);
        setFullScreen(fullScreen);
        _myView.setVisible(true);

    }

    @Override
    public boolean init() {
        return createBufferStrategy();
    }

    @Override
    public void setFullScreen(boolean fullScreen) {
        if(fullScreen)
            _myView.setExtendedState(JFrame.MAXIMIZED_BOTH);
        else
            _myView.setExtendedState(JFrame.NORMAL);

        _myView.setUndecorated(fullScreen);
    }

    private boolean createBufferStrategy() {
        // Intentamos crear el buffer strategy con 2 buffers.
        int intentos = 100;
        while (intentos-- > 0) {
            try {
                _myView.createBufferStrategy(2);
                break;
            } catch (Exception e) {
            }
        } // while pidiendo la creación de la buffeStrategy
        if (intentos == 0) {
            System.err.println("No pude crear la BufferStrategy");
            return false;
        }
        _bufferStrategy = _myView.getBufferStrategy();
        _graphics2D = (Graphics2D) _bufferStrategy.getDrawGraphics();
        return true;

    }

    @Override
    public void render() {
        // Pintamos el frame
        do {
            do {
                Graphics g = _bufferStrategy.getDrawGraphics();
                _graphics2D = (Graphics2D) g;
                try {
                    paintFrame();
                } finally {
                    g.dispose(); //Elimina el contexto gráfico y libera recursos del sistema realacionado
                }
            } while (_bufferStrategy.contentsRestored());
            _bufferStrategy.show();
        } while (_bufferStrategy.contentsLost());

    }

    @Override
    public boolean setInputListener(Input listener) {
        super.setInputListener(listener);
        PCInput i = (PCInput) listener;
        if (i == null)
            return false;

        _myView.addMouseListener(i);
        return true;
    }

    @Override
    public float getBottomBorder() {
        return _myView.getInsets().bottom;
    }

    @Override
    public float getLeftBorder() {
        return _myView.getInsets().left;
    }

    @Override
    public float getTopBorder() {
        return _myView.getInsets().top;
    }

    @Override
    public float getRightBorder() {
        return _myView.getInsets().right;
    }

    @Override
    public Image newImage(String name) {
        PCImage i = null;

        try {
            i = new PCImage(_myPaths._imagesPath + name);
        } catch (Exception e) {
        }

        return i;
    }

    @Override
    public Font newFont(String filename, int size, boolean isBold) {
        PCFont pcfont = null;

        try {
            pcfont = new PCFont(_myPaths._fontsPath + filename, size, isBold);
        } catch (Exception e) {
        }

        return pcfont;
    }

    @Override
    public void clearGame(int color) {
        //Para cambiar el color actual usar el metodo anterior
        Color jColor = new Color(color);
        _graphics2D.setColor(jColor);
        _graphics2D.fillRect(0, 0, sceneManager.getGameWidth(), sceneManager.getGameHeight());
        jColor = new Color(_actualColor);
        _graphics2D.setColor(jColor);
    }

    @Override
    public void clearWindow() {
        //Para cambiar el color actual usar el metodo anterior
        Color jColor = new Color(0xFFFFFF);
        _graphics2D.setColor(jColor);
        _graphics2D.fillRect(0, 0, getWindowWidth() + (int) getLeftBorder() + (int) getRightBorder(), getWindowHeight() + (int) getBottomBorder() + (int) getTopBorder());
        jColor = new Color(_actualColor);
        _graphics2D.setColor(jColor);
    }

    @Override
    public void translate(float x, float y) {
        _graphics2D.translate(x, y);
    }

    @Override
    public void scale(float x, float y) {
        _graphics2D.scale(x, y);
    }

    @Override
    public void save() {
        _save = _graphics2D.getTransform();
    }

    @Override
    public void restore() {
        if (_save != null)
            _graphics2D.setTransform(_save);
    }

    @Override
    public void drawImage(Image image, int x, int y, double scaleX, double scaleY) {
        PCImage i = (PCImage) image;
        if (i._baseImage == null) {
            System.err.println("Error dibujando la imagen");
            return;
        }

        int newW = (int) (i._baseImage.getWidth(null) * scaleX);
        int newH = (int) (i._baseImage.getHeight(null) * scaleY);
        _graphics2D.drawImage(i._baseImage, x, y, newW, newH, null);
    }

    @Override
    public void setColor(int color) {
        super.setColor(color);
        Color jColor = new Color(_actualColor);
        _graphics2D.setColor(jColor);
    }

    @Override
    public void setActualFont(Font font) {
        super.setActualFont(font);
        PCFont f = (PCFont) font;
        if(f._baseFont == null){
            System.err.println("Error seteando el texto de PC");
            return;
        }
        java.awt.Font javaFont = f._baseFont;
        _graphics2D.setFont(javaFont);
    }

    @Override
    public void fillRect(int cx, int cy, int w, int h) {
        _graphics2D.fillRect(cx, cy, w, h);
    }

    @Override
    public void drawRect(int cx, int cy, int w, int h) {
        _graphics2D.drawRect(cx, cy, w, h);
    }

    @Override
    public void drawLine(float initX, float initY, float endX, float endY) {
        Line2D line = new Line2D.Float(initX, initY, endX, endY);
        _graphics2D.drawLine((int) initX, (int) initY, (int) endX, (int) endY);
    }

    @Override
    public void drawText(String text, int x, int y) {
        setActualFont(_actualFont);
        _graphics2D.drawString(text, x, y);
    }

    @Override
    public int getWindowWidth() {
        return _myView.getWidth() - (int) getLeftBorder() - (int) getRightBorder();
    }

    @Override
    public int getWindowHeight() {
        return _myView.getHeight() - (int) getBottomBorder() - (int) getTopBorder();
    }


    //VARIABLES
    private JFrame _myView;
    private BufferStrategy _bufferStrategy;
    private Graphics2D _graphics2D;

    private AffineTransform _save;

}