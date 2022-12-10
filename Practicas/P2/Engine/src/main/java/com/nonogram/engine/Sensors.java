package com.nonogram.engine;

public interface Sensors {
    public void setTemperature(float t);
    public float getTemperature();
    public boolean isDark();
    public float getLux();
    public void setLux(float lx);
    void unregisterAll();
    void registerAll();
}
