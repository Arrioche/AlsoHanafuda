package com.tnielsen9082.alsohanafuda;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class Clicker implements View.OnClickListener{
    //where the cards are drawn from
    private LinearLayout drawPile;
    //in case I ever use logs
    private static final String TAG = "MainActivity";
    //the layout of hands
    private LinearLayout[] hand;
    //the button
    private Button button;
    //to keep track of which player is up
    private int handNum =0;
    public void id(LinearLayout tag1, LinearLayout[] tag2, Button tag3){
        drawPile=tag1;
        hand=tag2;
        button = tag3;
    }
    public void increase(){
        //this makes it switch between players
        handNum=(handNum+1)%3;
    }
    @Override
    public void onClick(View v) {
        View card;
        //if there are cards in the draw pile
        if(drawPile.getChildCount()!=0){
            //take a random card
            card= drawPile.getChildAt((int)(Math.random()*(drawPile.getChildCount()-1)));
            //remove it from the draw pile
            drawPile.removeView(card);
            //put it in the current hand
            hand[handNum].addView(card);
            if(drawPile.getChildCount()==0){
                //deactivate the button once the deck is empty
                button.setText("empty");
                button.setEnabled(false);
            }
        }
    }
    //this is the draw button
    //it takes a card from the deck layout and puts it in the current player's hand layout
    //it can rotate between players
    //it is where the random card drawing comes from
    //as the first of four methods...
    //it's an important method
}
