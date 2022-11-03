package com.nonogram.engine;

import java.util.List;

public interface Input {

    public class TouchEvent{
        public enum InputType{
            PULSAR,
            SOLTAR
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

    List<TouchEvent> getTouchEvents();
    public void newEvent(int x, int y, int ID, TouchEvent.InputType tipo);

}
