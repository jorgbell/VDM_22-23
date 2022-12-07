package com.nonogram.androidlauncher;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.nonogram.androidengine.AndroidEngine;
import com.nonogram.logic.MenuScene;

public class AndroidLauncher extends AppCompatActivity implements SensorEventListener {

    Sensor tempSensor;
    Sensor luxSensor;
    SensorManager sensorManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        tempSensor = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        sensorManager.registerListener(this, tempSensor, SensorManager.SENSOR_DELAY_NORMAL);
        luxSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        sensorManager.registerListener(this, luxSensor, SensorManager.SENSOR_DELAY_NORMAL);



        MenuScene sceneinicial = new MenuScene(450,800);
        _myEngine = new AndroidEngine(this);
        //manejo de errores: si se crea mal algo, para antes de empezar.
        if(!_myEngine.init() || !_myEngine.getSceneManager().push(sceneinicial)){
            _myEngine.stop();
        }
        getSupportActionBar().hide();

        View decorView = getWindow().getDecorView();
        // Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
        _myEngine.pause();
    }

    @Override
    protected void onResume() {
        // Avisamos a la vista (que es la encargada del active render)
        // de lo que está pasando.
        super.onResume();
        sensorManager.registerListener(this, tempSensor, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, luxSensor, SensorManager.SENSOR_DELAY_NORMAL);
        _myEngine.resume();

    }

    @Override
    protected void onDestroy() {
        _myEngine.stop();
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        _myEngine.stop();
        super.onStop();
    }

    MenuScene sceneinicial;
    private AndroidEngine _myEngine;



    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        //do something
        if(sensorEvent.sensor.getType() == Sensor.TYPE_LIGHT)
            _myEngine.getSensors().setLux(sensorEvent.values[0]);
        else if(sensorEvent.sensor.getType() == Sensor.TYPE_AMBIENT_TEMPERATURE)
            _myEngine.getSensors().setTemperature(sensorEvent.values[0]);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
        //do something
    }
}
