package com.nonogram.engine;

public abstract class AbstractSensors implements Sensors {

    public AbstractSensors(){
        _temperature = 0;
        _lux = 200;
    }
    @Override
    public void setTemperature(float t){
        _temperature = t;
    };
    @Override
    public float getTemperature(){
        return _temperature;
    }
    @Override
    public boolean isDark(){
        return _lux<=150;
    }
    @Override
    public float getLux(){
        return _lux;
    }
    @Override
    public void setLux(float lx){_lux = lx;}

    @Override
    public void unregisterAll() {
    }

    @Override
    public void registerAll() {

    }


    float _temperature; //celcius
    float _lux;
}
