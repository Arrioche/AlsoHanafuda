package com.tnielsen9082.alsohanafuda;

import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class TurnClicker implements View.OnClickListener{
    private boolean swich;
    private Dropper drop;
    public TurnClicker(Dropper tag3,boolean tag2){
        drop=tag3;
        swich=tag2;
    }
    @Override
    public void onClick(View v) {
        if(swich) {
            ConstraintLayout parent = (ConstraintLayout) v.getParent();
            parent.setVisibility(View.GONE);
        }
        else{
            drop.turnRotator();
        }
    }
    //this is the button that closes the turn splitter screen
    //if true
    //it also ends the turn
    //if false
    //as the first of four methods...
    //it's an important method
}

