package com.tnielsen9082.alsohanafuda;


import android.content.ClipData;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
//the dragged and drop system in Android consists of two objects
//the dragger
//and the dropper
//this is the dragger
//it gets assigned to evey card
//if a card is pressed, it packs the card's information (suit/value) into a ClipData
//and puts that along with the card view itself into the startDrag method
//which broadcasts that info to the dropper
//it also creates a dragged shadow that follows your finger around on the screen
//this dragger also checks to see if you're drawing from the right place
//since every card has a dragger, there is a check to make sure that it only activates if
//your first card per turn is from your hand
//and your second card is from the second card drawn
//that is what the advancer variable does

public final class Dragger implements View.OnTouchListener {
    //in case I use logs
    private static final String TAG = "Dragger";
    //this decides which of the hand and the second is available to draw from
    private int advancer =0;
    //this is the array of hands
    private LinearLayout[] hand;
    //this is the place where the second drawn card will go
    private LinearLayout second;
    //to keep track of the current player
    private int handNum =0;
    private boolean dragged;
    private ImageView inspect;
    //this assigns those variable to the right layouts
    //it is called from CardInitializer
    //in the setUp method
    public void id(LinearLayout[] tag3, LinearLayout tag,ImageView tag2){
        hand = tag3;
        second = tag;
        inspect=tag2;
    }
    private float x;
    private float y;
    //this rotates the players when called
    //the system is that there are three layouts that are the player hands
    //only one is visible and active at a time
    //it is called from TurnRotator
    //in the Dropper class
    void increase(){
        handNum=(handNum+1)%3;
    }
    //this activate when a view with a dragger assigned to it is touched
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        Log.e(TAG,"perhaps: "+motionEvent.getAction());
        //makes the class motionEvent perform the getAction function
        //motionEvents track what is touching the screen
        //ACTION_DOWN means that someone just started touching it
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                dragged =false;
                x=motionEvent.getX();
                y=motionEvent.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                if(absoluteDistance(motionEvent.getX(),motionEvent.getY())>10) {
                    //makes a clipData
                    ClipData data;
                    data = ClipData.newPlainText("", view.getContentDescription() + "");
                    //make a custom dragged shadow
                    //once I create it
                    View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                    //if advancer is ZERO
                    //play from the hand
                    if (view.getParent() == hand[handNum] && advancer == 0) {
                        //if the card is in the current hand or the secondary spot
                        dragged =true;
                        view.startDrag(data, shadowBuilder, view, 1);
                        view.setVisibility(View.INVISIBLE);
                        return false;
                    }
                    //if advancer is ONE
                    //play from the second
                    else if (view.getParent() == second && advancer == 1) {
                        //if the card is in the current hand or the secondary spot
                        dragged =true;
                        view.startDrag(data, shadowBuilder, view, 1);
                        view.setVisibility(View.INVISIBLE);
                        return false;
                    }
                    //if advancer is NEITHER
                    //turn over
                    return true;
                }
            case MotionEvent.ACTION_UP:
                if(dragged){
                    Log.e(TAG, "hewwo");
                    return true;
                }
                else if(motionEvent.getAction()==1){
                    Log.e(TAG, "HEWWO");
                    inspect.setVisibility(View.VISIBLE);
                }
            case MotionEvent.ACTION_CANCEL:
                if(dragged){
                    Log.e(TAG, "hewwo");
                    return true;
                }
                else{
                    Log.e(TAG, "HEWWO");
                    inspect.setVisibility(View.VISIBLE);
                }
            default:
                //stops if there is no more dragging
                break;
        }
        //this allows you to receive updates on the status of the dragged thing
        return true;
    }
    void setAdvancer(int num){
        advancer =num;
    }
    int getAdvancer(){
        return advancer;
    }
    private float absoluteDistance(float X, float Y){
        float xDist = Math.abs(x-X);
        float yDist = Math.abs(y-Y);
        /*Log.e(TAG,y+", "+Y);
        Log.e(TAG,x+", "+X);
        Log.e(TAG,yDist+" y");
        Log.e(TAG,xDist+ " x");
        Log.e(TAG,(float)(Math.sqrt((double)(yDist*yDist+xDist*xDist)))+" tot");*/
        return (float)(Math.sqrt((double)(yDist*yDist+xDist*xDist)));
    }
}

