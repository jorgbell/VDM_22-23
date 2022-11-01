package com.nonogram.androidlauncher;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.nonogram.androidengine.AndroidEngine;
import com.nonogram.logic.PruebasScene;

public class AndroidLauncher extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PruebasScene g = new PruebasScene();
        _myEngine = new AndroidEngine(this,g);
        g.init();
    }

    @Override
    protected void onPause() {
        super.onPause();
        _myEngine.pause();
    }

    @Override
    protected void onResume() {
        // Avisamos a la vista (que es la encargada del active render)
        // de lo que está pasando.
        super.onResume();
        _myEngine.resume();
    }


    private AndroidEngine _myEngine;

}
