package com.nonogram.androidlauncher;

import android.os.Bundle;
import android.view.SurfaceView;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.nonogram.androidengine.AndroidEngine;
import com.nonogram.logic.MenuScene;

public class AndroidLauncher extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MenuScene sceneinicial = new MenuScene(450,800);
        setContentView(R.layout.androidlauncher);
        _myEngine = new AndroidEngine(this, findViewById(R.id.surfaceView));

//        _mAdView.setAdSize(AdSize.FULL_BANNER);
//        _mAdView.setAdUnitId("ca-app-pub-3940256099942544/6300978111");



        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
                // cargamos los anuncios
                AdView _mAdView = findViewById(R.id.adView);
                AdRequest adRequest = new AdRequest.Builder().build();
                _mAdView.loadAd(adRequest);
            }
        });


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
