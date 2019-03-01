package com.tnielsen9082.alsohanafuda;

import android.view.MotionEvent;
import android.view.View;

//this is the class that dismisses the display cards
//that's all it does

public class CardDescDismisser implements View.OnTouchListener {
    @Override
    //this is assigned to all the display cards
    public boolean onTouch(View v, MotionEvent event) {
        //if the view is tapped make it disappear
        v.setVisibility(View.GONE);
        //fin
        return true;
    }
}
