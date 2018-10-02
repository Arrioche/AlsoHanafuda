package com.tnielsen9082.alsohanafuda;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MyClickListener implements View.OnClickListener{
    private LinearLayout drawPile;
    private LinearLayout hand;
    private Button button;
    public void id(LinearLayout tag1, LinearLayout tag2, Button tag3){
        drawPile=tag1;
        hand=tag2;
        button = tag3;
    }
    @Override
    public void onClick(View v) {
        View card;
        if(drawPile.getChildCount()!=0){
            card= drawPile.getChildAt((int)(Math.random()*(drawPile.getChildCount()-1)));
            drawPile.removeView(card);
            hand.addView(card);
            if(drawPile.getChildCount()==0){
                button.setText("empty");
                button.setEnabled(false);
            }
        }
    }
}
