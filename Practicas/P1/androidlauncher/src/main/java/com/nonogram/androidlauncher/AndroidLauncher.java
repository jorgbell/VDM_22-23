package com.nonogram.androidlauncher;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.nonogram.androidengine.AndroidEngine;
import com.nonogram.engine.AbstractEngine;
import com.nonogram.logic.GameScene;
import com.nonogram.logic.PruebasScene;

public class AndroidLauncher extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //PruebasScene g = new PruebasScene();
        GameScene g = new GameScene(450,800);
        _myEngine = new AndroidEngine(this);
        _myEngine.init();
        _myEngine.getSceneManager().push(g);
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
