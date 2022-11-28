package com.nonogram.engine;

import java.util.List;

public interface Input {
//Clase TouchEvent para abstraer los eventos en pantalla de la plataforma
    public class TouchEvent{
        public enum InputType{
            CLICK_CORTO,
            CLICK_LARGO
        }
        public TouchEvent(InputType tipo, float x, float y, int ID){
            _posX = x;
            _posY = y;
            _touchID = ID;
            _type = tipo;
        }
        //getters
        public float get_posX(){return _posX;}
        public float get_posY(){return _posY;}
        public int get_touchID(){return _touchID;}
        public InputType get_type(){return _type;}

        //VARIABLES PRIVADAS
        float _posX;
        float _posY;
        int _touchID;
        InputType _type;
    }//touchevent

    //----------------------------------------------//
    //metodos de la interfaz Input
    List<TouchEvent> getTouchEvents();
    public void newEvent(float x, float y, int ID, TouchEvent.InputType tipo);
    public void setGraphics(Graphics g);

}
