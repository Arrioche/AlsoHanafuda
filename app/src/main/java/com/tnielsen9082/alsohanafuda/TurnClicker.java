package com.tnielsen9082.alsohanafuda;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class TurnClicker implements View.OnClickListener{
    private boolean swich;
    private LinearLayout second;
    private Dropper drop;
    private CardInitializer cardInitializer;
    //to keep count of the number of turns
    private int counter =0;
    public TurnClicker(Dropper tag3,boolean tag2, CardInitializer tag1, LinearLayout tag4){
        drop=tag3;
        swich=tag2;
        second =tag4;
        cardInitializer =tag1;
    }
    @Override
    public void onClick(View v) {
        if(swich) {
            //this dismisses the turn splitter screen
            ConstraintLayout parent = (ConstraintLayout) v.getParent();
            parent.setVisibility(View.GONE);
            //it also will change to the scoring screen at the end
            counter++;
            if(counter==-1) {
                cardInitializer.goToScore();
            }
        }
        else{
            //this ends the current turn
            //and brings up the turn splitter screen
            if(second.getChildCount()==0)
            drop.turnRotator();

        }
    }
    //this is the button that closes the turn splitter screen
    //if true
    //it also ends the turn
    //if false
    //it also activates the scoring screen activity
    //as the first of four methods...
    //it's an important method
}

