package com.tnielsen9082.alsohanafuda;


import android.content.ClipData;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;


public final class Dragger implements View.OnTouchListener {
    //in case I use logs
    private static final String TAG = "Dragger";
    //this decides which of the hand and the second is available to draw from
    private int rotate=0;
    //this is the array of hands
    private LinearLayout[] hand;
    //this is the place where the second drawn card will go
    private LinearLayout second;
    //to keep track of the current player
    private int handNum =0;
    public void id(LinearLayout[] tag3, LinearLayout tag){
        hand = tag3;
        second = tag;
    }
    //this rotates the players when called
    //it is called from TurnRotator
    //in the Dropper class
    void increase(){
        handNum=(handNum+1)%3;
    }
    public boolean onTouch(View view, MotionEvent motionEvent) {
        //makes the class motionEvent perform the getAction function
        //motionEvents track what is touching the screen
        //ACTION_DOWN means that someone just started touching it
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            //makes a clipData
            ClipData data;
            data = ClipData.newPlainText("", view.getContentDescription()+"");
            //make a custom drag shadow
            //once I create it
            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
            //if rotate is ZERO
            //play from the hand
            if(view.getParent()==hand[handNum]&&rotate==0){
                //if the card is in the current hand or the secondary spot
                view.startDrag(data, shadowBuilder, view, 1);
                view.setVisibility(View.INVISIBLE);
            }
            //if rotate is ONE
            //play from the second
            else if(view.getParent()==second&&rotate==1){
                //if the card is in the current hand or the secondary spot
                view.startDrag(data, shadowBuilder, view, 1);
                view.setVisibility(View.INVISIBLE);
            }
            //if rotate is NEITHER
            //turn over
            return true;
        }
        else {
            return false;
        }
    }
    void setAdvancer(int num){
        rotate=num;
    }
    int getAdvancer(){
        return rotate;
    }
    //in summation:
    //this is the function that lets you pick up and move cards
    //it only takes them from the current hand or the second-drawn card
    //it puts the content description in the clipdata
    //so that the dropper knows what card is coming to it
    //and it could probably fit more in there too
    //as the first of four methods...
    //it's an important method
}

