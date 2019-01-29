package com.tnielsen9082.alsohanafuda;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.zip.DeflaterInputStream;

public class DisplayClicker implements View.OnClickListener{
    //
    private LinearLayout[] takenTricks;
    //
    private LinearLayout buttons;
    //
    private boolean upDown;
    //
    private Button dismiss;
    //
    private int player=0;
    private TurnClicker turnClicker;
    //to keep track of which player is up
    private int handNum =0;
    public void id(LinearLayout tag1, LinearLayout[] tag2, boolean tag3, Button tag4, TurnClicker tag6){
        buttons = tag1;
        takenTricks = tag2;
        upDown=tag3;
        dismiss=tag4;
        turnClicker=tag6;
    }
    DisplayClicker(LinearLayout tag1, LinearLayout[] tag2, boolean tag3, Button tag4, int tag5, TurnClicker tag6){
        buttons = tag1;
        takenTricks = tag2;
        upDown=tag3;
        dismiss=tag4;
        player= tag5;
        turnClicker=tag6;

    }
    DisplayClicker(){}
    public void wipe(){
        buttons.setVisibility(View.VISIBLE);
        for (int i = 0; i < 3; i++) {
            takenTricks[i].setVisibility(View.GONE);
        }
        dismiss.setVisibility(View.GONE);
    }
    @Override
    public void onClick(View v) {
        if(upDown){
            buttons.setVisibility(View.GONE);
            handNum=turnClicker.getHandNum();
            takenTricks[(handNum+player+2)%3].setVisibility(View.VISIBLE);
            dismiss.setVisibility(View.VISIBLE);
        }
        else{
            buttons.setVisibility(View.VISIBLE);
            for (int i = 0; i < 3; i++) {
                takenTricks[i].setVisibility(View.GONE);
            }
            dismiss.setVisibility(View.GONE);
        }
    }
}
