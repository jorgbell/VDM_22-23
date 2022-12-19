package com.nonogram.androidengine;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.nonogram.engine.AbstractSensors;

public class AndroidSensors extends AbstractSensors implements SensorEventListener {

    AndroidSensors(Activity activity){
        super();
        _activity = activity;

        sensorManager = (SensorManager) _activity.getSystemService(Context.SENSOR_SERVICE);
        tempSensor = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        sensorManager.registerListener(this, tempSensor, SensorManager.SENSOR_DELAY_NORMAL);
        luxSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        sensorManager.registerListener(this, luxSensor, SensorManager.SENSOR_DELAY_NORMAL);

    }


    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        //do something
        if(sensorEvent.sensor.getType() == Sensor.TYPE_LIGHT)
            setLux(sensorEvent.values[0]);
        else if(sensorEvent.sensor.getType() == Sensor.TYPE_AMBIENT_TEMPERATURE)
            setTemperature(sensorEvent.values[0]);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
        //do something
    }
    @Override
    public void unregisterAll() {
        sensorManager.unregisterListener(this);
    }

    @Override
    public void registerAll() {
        sensorManager.registerListener(this, tempSensor, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, luxSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }


    Activity _activity;

    Sensor tempSensor;
    Sensor luxSensor;
    SensorManager sensorManager;
}
