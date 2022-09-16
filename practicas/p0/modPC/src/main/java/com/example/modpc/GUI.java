package com.example.modpc;

import javax.swing.JFrame;
import java.awt.Panel;
import java.awt.Button;
import java.awt.TextField;
import java.awt.event.MouseListener;

public class GUI {
    public GUI(String name, int w, int h) {
        _window = new JFrame(name);
        _window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        _wWidth = w; _wHeight = h;
        _window.setSize(_wWidth, _wHeight);


        //hace cosas
        //panel
        Panel p = new Panel();
        //añadir textfield al panel
        TextField text = new TextField(15);
        //botón
        Button b = new Button("no pulsar");
        p.add(b);
        p.add(text);

        _window.getContentPane().add(p);


        _window.setVisible(true);
    }

    //variables
    JFrame _window;
    int _wWidth; int _wHeight;
}
