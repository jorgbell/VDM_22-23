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

    public AndroidAdManager(Context c) {_context = c;}

    public void SetContext(Context c) { _context = c; }

    @Override
    public boolean init() {


        return true;
    }



    Context _context;
    AdView _mAdView;
    RewardedAd _rewardedAd;
}
