package com.tnielsen9082.alsohanafuda;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TurnClicker implements View.OnClickListener{
    private boolean swich;
    private LinearLayout second;
    private Dropper drop;
    private CardInitializer cardInitializer;
    private int count =1;
    private LinearLayout[] hands;
    private String[] names;
    private TextView nameDisplay;
    private Button disable;
    public TurnClicker(Dropper tag3,boolean tag2, CardInitializer tag1, LinearLayout tag4,LinearLayout[] tag5,String[] tag6,TextView tag7, Button tag8){
        drop=tag3;
        swich=tag2;
        second =tag4;
        cardInitializer =tag1;
        hands=tag5;
        names=tag6;
        nameDisplay = tag7;
        disable = tag8;
    }
    @Override
    public void onClick(View v) {
        if(swich) {
            //this dismisses the turn splitter screen
            ConstraintLayout parent = (ConstraintLayout) v.getParent();
            parent.setVisibility(View.GONE);
            disable.setEnabled(true);
            //it also will change to the scoring screen at the end
            boolean done = true;
            for (int i = 0; i < 3; i++) {
                if(hands[i].getChildCount()!=0){
                    done=false;
                }
            }
            if(done) {
                cardInitializer.goToScore();
            }

        }
        else{
            //this ends the current turn
            //and brings up the turn splitter screen
            if(second.getChildCount()==0) {
                nameDisplay.setText("Next Up: "+names[count]);
                count= (count+1)%3;
                disable.setEnabled(false);
                drop.turnRotator();
            }
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

