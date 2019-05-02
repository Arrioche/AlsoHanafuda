package com.tnielsen9082.alsohanafuda;

import android.app.Activity;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TurnRotator extends Activity {
    //the array of player hands
    private LinearLayout[] hands;
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
    private TextView nameDisplaySplitter;
    private Button[] trickButtons;
    private Activity activity;
    public void id(Activity activityTag,LinearLayout[] handTag, TextView[] scoreTag, Dragger dragTag, String[] nameTag,Button[] trickButtonsTag){
        activity=activityTag;
        hands=handTag;
        score=scoreTag;
        drag=dragTag;
        names=nameTag;
        nameDisplaySplitter = activity.findViewById(R.id.nextPlayerAnnounce);
        nameDisp=activity.findViewById(R.id.playerNameMain);
        trickButtons = trickButtonsTag;
    }
    public int getPlayerNum(){
        return playerNum;
    }

    public void rotate() {
        nameDisplaySplitter.setText("Next Up: "+names[(playerNum+1)%3]);
        ((View)nameDisplaySplitter.getParent()).setVisibility(View.VISIBLE);
        for (int i = 0; i < hands.length; i++) {
            for (int j = 0; j < hands[i].getChildCount(); j++) {
                hands[i].getChildAt(j).setAlpha((float)1);
            }
        }
        //hide the old hand
        hands[playerNum % 3].setVisibility(View.GONE);
        //hide the old score
        score[playerNum % 3].setVisibility(View.GONE);
        //rotate the dropper
        playerNum = (playerNum + 1) % 3;
        if((names[(playerNum+1)%3].toLowerCase().charAt(names[(playerNum+1)%3].length()-1))=="s".charAt(0)){
            trickButtons[1].setText(names[(playerNum+1)%3]+"'\nCards");
        }
        else {
            trickButtons[1].setText(names[(playerNum+1)%3]+"'s\nCards");
        }
        if((names[(playerNum+2)%3].toLowerCase().charAt(names[(playerNum+2)%3].length()-1))=="s".charAt(0)){
            trickButtons[2].setText(names[(playerNum+2)%3]+"'\nCards");
        }
        else {
            trickButtons[2].setText(names[(playerNum+2)%3]+"'s\nCards");
        }
        //rotate the names
        nameDisp.setText(names[playerNum]);
        //show the new hand
        hands[(playerNum)].setVisibility(View.VISIBLE);
        //show the new score
        score[(playerNum)].setVisibility(View.VISIBLE);
        //rotate the dragger
        drag.increase();
        drag.setAdvancer(0);
    }
}
