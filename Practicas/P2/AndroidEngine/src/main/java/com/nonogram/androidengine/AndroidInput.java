package com.nonogram.androidengine;

import android.annotation.SuppressLint;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;

import com.nonogram.engine.AbstractInput;

public class AndroidInput extends AbstractInput implements View.OnTouchListener {
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View view, @NonNull MotionEvent e) {
        switch (e.getAction()){
            case MotionEvent.ACTION_DOWN:
                startTime = e.getEventTime();
                break;
            case MotionEvent.ACTION_UP:
                float clickTime = e.getEventTime() - startTime;
                if(clickTime > longClickTime){
                    newEvent(e.getX(), e.getY(),
                            e.getPointerId(e.getActionIndex()),
                            TouchEvent.InputType.CLICK_LARGO);
                }
                else{
                    newEvent(e.getX(), e.getY(),
                            e.getPointerId(e.getActionIndex()),
                            TouchEvent.InputType.CLICK_CORTO);
                }
                break;
            default:
                break;
        }
        return true;
    }


    float startTime = 0;
    float longClickTime = 300; //ms;
}
