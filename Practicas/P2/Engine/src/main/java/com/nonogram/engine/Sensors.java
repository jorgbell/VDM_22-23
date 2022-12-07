package com.nonogram.engine;

public class Sensors {

    public Sensors(){
        _temperature = 20;
    }

    public void setTemperature(float t){
        _temperature = t;
    };
    public float getTemperature(){
        return _temperature;
    }

    float _temperature; //celcius
}
