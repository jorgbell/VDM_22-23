package com.nonogram.androidlauncher;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.nonogram.androidengine.AndroidEngine;
import com.nonogram.logic.MenuScene;

public class AndroidLauncher extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MenuScene sceneinicial = new MenuScene(450,800);
        _myEngine = new AndroidEngine(this);
        //manejo de errores: si se crea mal algo, para antes de empezar.
        if(!_myEngine.init() || !_myEngine.getSceneManager().push(sceneinicial)){
            _myEngine.stop();
        }
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

    MenuScene sceneinicial;
    private AndroidEngine _myEngine;

}
