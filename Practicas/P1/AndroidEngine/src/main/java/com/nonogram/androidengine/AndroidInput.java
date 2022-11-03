package com.nonogram.androidengine;

import android.annotation.SuppressLint;
import android.view.MotionEvent;
import android.view.View;

import com.nonogram.engine.AbstractInput;

public class AndroidInput extends AbstractInput implements View.OnTouchListener {
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View view, MotionEvent e) {
        switch (e.getAction()){
            case MotionEvent.ACTION_POINTER_DOWN:
                newEvent((int)e.getX(), (int)e.getY(),
                        e.getPointerId(e.getActionIndex()),
                        TouchEvent.InputType.PULSAR);
                break;
            case MotionEvent.ACTION_POINTER_UP:
                newEvent((int)e.getX(), (int)e.getY(),
                        e.getPointerId(e.getActionIndex()),
                        TouchEvent.InputType.SOLTAR);
                break;
            default:
                break;
        }
        return true;
    }
}
