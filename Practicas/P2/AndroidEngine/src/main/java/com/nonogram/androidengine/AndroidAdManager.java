package com.nonogram.androidengine;

import android.content.Context;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.nonogram.engine.AdManager;

public class AndroidAdManager implements AdManager {

    public AndroidAdManager() {}

    public void SetContext(Context c) { _context = c; }

    @Override
    public boolean init() {
        _mAdView = new AdView(_context);
        _mAdView.setAdSize(AdSize.FULL_BANNER);
        _mAdView.setAdUnitId("ca-app-pub-3940256099942544/6300978111");

        MobileAds.initialize(_context, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
                // cargamos los anuncios
                AdRequest adRequest = new AdRequest.Builder().build();
                _mAdView.loadAd(adRequest);
                //_mAdView.bringToFront();
            }
        });

        return true;
    }



    Context _context;
    AdView _mAdView;
    RewardedAd _rewardedAd;
}
