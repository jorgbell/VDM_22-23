package com.nonogram.engine;

public class Sensors {

    public Sensors(){
        _temperature = 0;
        _lux = 200;
    }
    public void setTemperature(float t){
        _temperature = t;
    };
    public float getTemperature(){
        return _temperature;
    }
    public boolean isDark(){
        return _lux<=150;
    }
    public float getLux(){
        return _lux;
    }
    public void setLux(float lx){_lux = lx;}

    float _temperature; //celcius
    float _lux;
}
