package com.tnielsen9082.alsohanafuda;

import android.support.constraint.ConstraintLayout;
import android.view.View;

public class TurnStarter implements View.OnClickListener{
    //in case I use logs
    private static final String TAG = "TurnClicker";
    //this is called from Dropper
    //in the general onDrag method in many places
    //when the current player plays their second card
    //if the deck runs out prematurely
    //then it gets called anyway from the secondCard method
    //also in Dropper
    @Override
    //this is what happens if you click the button
    public void onClick(View v) {
            //this dismisses the turn splitter screen
            ConstraintLayout parent = (ConstraintLayout) v.getParent();
            parent.setVisibility(View.GONE);
    }
}
