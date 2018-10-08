package com.tnielsen9082.alsohanafuda;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class Clicker implements View.OnClickListener{
    private LinearLayout drawPile;
    private LinearLayout[] hand;
    private Button button;
    private int handNum =0;
    public void id(LinearLayout tag1, LinearLayout[] tag2, Button tag3){
        drawPile=tag1;
        hand=tag2;
        button = tag3;
    }
    public void increase(){
        handNum=(handNum+1)%3;
    }
    @Override
    public void onClick(View v) {
        View card;
        if(drawPile.getChildCount()!=0){
            card= drawPile.getChildAt((int)(Math.random()*(drawPile.getChildCount()-1)));
            drawPile.removeView(card);
            hand[handNum].addView(card);
            if(drawPile.getChildCount()==0){
                button.setText("empty");
                button.setEnabled(false);
            }
        }
    }
}
