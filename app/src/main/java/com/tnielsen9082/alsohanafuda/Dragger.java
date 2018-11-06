package com.tnielsen9082.alsohanafuda;


import android.content.ClipData;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;


public final class Dragger implements View.OnTouchListener {
    private static final String TAG = "MainActivity";
    //this decides what is available to draw from
    private int rotate=0;
    //this is the hand
    private LinearLayout[] hand;
    //this is the place where the second drawn card will go
    private LinearLayout second;
    //this is so it can connect to the dropper
    private Dropper dropper;
    //to keep track of the current player
    private int handNum =0;
    public void id(LinearLayout[] tag3, LinearLayout tag,Dropper tag1){
        hand = tag3;
        second = tag;
        dropper=tag1;
    }
    public void increase(){
        //this rotates the players when called
        handNum=(handNum+1)%3;
    }
    //the action that the class performs
    public Dragger(LinearLayout[] tag1, LinearLayout tag2, Dropper tag3){
        hand = tag1;
        second = tag2;
        dropper=tag3;
    }
    public boolean onTouch(View view, MotionEvent motionEvent) {
        //makes the class motionEvent perform the getAction function
        //motionEvents track what is touching the screen
        //ACTION_DOWN means that someone just started touching it
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            //makes a clipData
            //since we are just using pictures there is no data
            //but there could be data
            //this is how you copy/paste things
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
            }
            //if rotate is ONE
            //play from the second
            else if(view.getParent()==second&&rotate==1){
                //if the card is in the current hand or the secondary spot
                view.startDrag(data, shadowBuilder, view, 1);
            }
            //if rotate is NEITHER
            //turn over
            return true;
        }
        else {
            return false;
        }
    }
    public void advancer(int num){
        rotate=num;
    }
    public int getAdvancer(){
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

