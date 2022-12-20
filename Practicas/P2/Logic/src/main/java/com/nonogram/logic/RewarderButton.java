package com.nonogram.logic;

import com.nonogram.engine.Input;

public class RewarderButton extends Button{
    public RewarderButton(int x, int y, int w, int h) {
        super(x, y, w, h);
    }


    public void handleEvent(Input.TouchEvent e) {
        _engine.getAdManager().showRewarder();
    }
}
