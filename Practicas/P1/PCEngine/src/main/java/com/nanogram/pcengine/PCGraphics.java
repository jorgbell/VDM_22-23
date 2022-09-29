package com.nanogram.pcengine;

import com.nanogram.engine.AbstractGraphics;
import com.nanogram.engine.Font;
import com.nanogram.engine.Image;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

public class PCGraphics extends AbstractGraphics { //realmente, extenderá abstractGraphics

    public PCGraphics(String windowName, int w, int h){     //el w y el h igual hay que tocarlos, ahora mismo son el de la ventana
        super(w,h);
        //inicializar JFrame
        _myView = new JFrame(windowName);
        _myView.setSize(_windowWidth, _windowHeight);
        _myView.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        _myView.setIgnoreRepaint(true);
        _myView.setVisible(true);

        createBufferStrategy();
    }

    private void createBufferStrategy() {
        // Intentamos crear el buffer strategy con 2 buffers.
        int intentos = 100;
        while(intentos-- > 0) {
            try {
                _myView.createBufferStrategy(2);
                break;
            }
            catch(Exception e) {
            }
        } // while pidiendo la creación de la buffeStrategy
        if (intentos == 0) {
            System.err.println("No pude crear la BufferStrategy");
            return;
        }
        else {
            // En "modo debug" podríamos querer escribir esto.
            //System.out.println("BufferStrategy tras " + (100 - intentos) + " intentos.");
        }

        //esto se ejecuta cada vez que se cambia el tamaño de la ventana
        _myView.addComponentListener(new ComponentAdapter()
        {
            public void componentResized(ComponentEvent evt) {
                //Component c = (Component)evt.getSource();
                System.out.println("componentResized: "+evt.getSource());
                //este codigo que habia aqui (lo elimine) se carga el canvas y lo crea de nuevo, es lo que hacia que en el ejemplo
                //parpadee. Lo que hay que hacer asi es pillar el tamaño de la ventana que le llega al evento,
                //y cambiar el tamaño del canvas logico
                //TODO: Cambiar el tamaño del canvas logico
            }
        });

        _bufferStrategy =_myView.getBufferStrategy();
        _graphics2D = (Graphics2D) _bufferStrategy.getDrawGraphics();
    }

    public void paintFrame(){
        // Pintamos el frame
        do {
            do {
                Graphics g = _bufferStrategy.getDrawGraphics();
                try {
                    render();
                }
                finally {
                    g.dispose(); //Elimina el contexto gráfico y libera recursos del sistema realacionado
                }
            } while(_bufferStrategy.contentsRestored());
            _bufferStrategy.show();
        } while(_bufferStrategy.contentsLost());

    }

    private void render(){
        // "Borramos" el fondo.
        _graphics2D.setColor(Color.BLUE);
        _graphics2D.fillRect(0,0, getWindowWidth(), getWindowHeight());
        // Pintamos la escena
        _myScene.render();
    }

    @Override
    public Image newImage(String name) {
        return null;
    }

    @Override
    public Font newFont(String filename, int size, boolean isBold) {
        return null;
    }

    @Override
    public void clear(int color) {
        //Para cambiar el color actual usar el metodo anterior
        Color jColor = new Color(color);
        _graphics2D.setColor(jColor);
        _graphics2D.fillRect(0,0, getWindowWidth(), getWindowHeight());
        jColor = new Color(_actualColor);
        _graphics2D.setColor(jColor);
    }

    @Override
    public void translate(int x, int y) {
        _graphics2D.translate(x,y);
    }

    @Override
    public void scale(int x, int y) {
        _graphics2D.scale(x,y);
    }

    @Override
    public void save() {
        _save = _graphics2D.getTransform();
    }

    @Override
    public void restore() {
        if(_save!=null)
            _graphics2D.setTransform(_save);
    }

    @Override
    public void drawImage(Image image, int x, int y) {

    }

    @Override
    public void setColor(int color) {
        _actualColor = color;
        Color jColor = new Color(_actualColor);
        _graphics2D.setColor(jColor);
    }

    @Override
    public void fillSquare(int cx, int cy, int side) {

    }

    @Override
    public void drawSquare(int cx, int cy, int side) {

    }

    @Override
    public void drawLine(int initX, int initY, int endX, int endY) {

    }

    @Override
    public void drawText(String text, int x, int y) {

    }

    @Override
    public int getWindowWidth() {
        return _myView.getWidth();
    }

    @Override
    public int getWindowHeight() {
        return _myView.getHeight();
    }



    //VARIABLES
    private JFrame _myView;
    private BufferStrategy _bufferStrategy;
    private Graphics2D _graphics2D;

    private AffineTransform _save;

}
