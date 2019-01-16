package com.tnielsen9082.alsohanafuda;

import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

public class Dismisser implements View.OnTouchListener {
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        v.setVisibility(View.GONE);
        return true;
    }
}
