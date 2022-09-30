package com.example.modpc;

import javax.swing.JFrame;
import java.awt.Panel;
import java.awt.Button;
import java.awt.TextField;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JOptionPane;

public class GUI implements MouseListener {
    public GUI(String name, int w, int h) {
        _window = new JFrame(name);
        _window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        _wWidth = w; _wHeight = h;
        _window.setSize(_wWidth, _wHeight);


        //hace cosas
        //panel
        _panel = new Panel();
        //añadir textfield al panel
        _textField = new TextField(15);
        //botón
        _button = new Button("no pulsar");
        _button.addMouseListener(this);
        _panel.add(_button);
        _panel.add(_textField);

        _window.getContentPane().add(_panel);


        _window.setVisible(true);
    }

    //variables
    JFrame _window;
    Button _button;
    Panel _panel;
    TextField _textField;
    int _wWidth; int _wHeight;

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        System.out.println("bona tarda");
        JOptionPane.showMessageDialog(_panel, _textField.getText(),"felicitats", JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }
}
