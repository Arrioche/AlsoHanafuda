package com.tnielsen9082.alsohanafuda;

import android.app.Activity;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TurnRotator extends Activity {
    //the array of player hands
    private LinearLayout[] hands;
    //the screen that breaks up turns
    private ConstraintLayout splitter;
    //displays the score
    private TextView[] score;
    //the place where names are displayed
    private TextView nameDisp;
    //the dragger
    private Dragger drag;
    //to keep track of the current player
    private int playerNum=0;
    //the array of player names
    private String[] names;
    public void id(Activity activity,LinearLayout[] handTag, TextView[] scoreTag, Dragger dragTag, String[] nameTag){
        hands=handTag;
        score=scoreTag;
        drag=dragTag;
        names=nameTag;
        splitter=activity.findViewById(R.id.turnSplitter);
        nameDisp=activity.findViewById(R.id.playerNameMain);
    }
    public int getPlayerNum(){
        return playerNum;
    }

    public void rotate() {
        //hide the old hand
        hands[playerNum % 3].setVisibility(View.GONE);
        //hide the old score
        score[playerNum % 3].setVisibility(View.INVISIBLE);
        //rotate the dropper
        playerNum = (playerNum + 1) % 3;
        //rotate the names
        nameDisp.setText(names[playerNum]);
        //show the new hand
        hands[(playerNum)].setVisibility(View.VISIBLE);
        //show the new score
        score[(playerNum)].setVisibility(View.VISIBLE);
        //rotate the dragger
        drag.increase();
        splitter.setVisibility(View.VISIBLE);
        drag.setAdvancer(0);
    }
}
