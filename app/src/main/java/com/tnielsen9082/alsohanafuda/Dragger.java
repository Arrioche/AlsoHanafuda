package com.tnielsen9082.alsohanafuda;


import android.content.ClipData;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
//the drag and drop system in Android consists of two halves
//the dragger
//and the dropper
//this is the dragger
//rather
//this is the touchListener that performs the dragger's function
//it gets assigned to evey card
//if a card is pressed and moved, it packs the card's information (suit/value) into a ClipData
//and puts that along with the card view itself into the startDrag method
//which broadcasts that info to the dropper
//it also creates a dragged shadow that follows your finger around on the screen

//every card has its suit and value encoded in three characters in its content description
//first, a letter ccrresponding to the month
//a=january, b=february, etc.
//then a number between 01 and 20 which is the point value

//this dragger also checks to see if you're drawing from the right place
//since every card has a dragger, there is a check to make sure that it only activates if
//your first card per turn is from your hand
//and your second card is from the second card drawn
//that is what the advancer variable does

//if the card is tapped but not moved
//it acts as a button
//and displays a full-size version of that card's image
//along with a description of it
//on the screen
//if you tap this display it disappears

//it discerns between a tap and a move
//by tracking how far you move your finger before releasing it

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
    //the way to tell if the card is being dragged or tapped
    private boolean dragged;
    //the list of cards that display full-size
    private ArrayList<View> inspect;
    //the list of cards to compare the prior list to
    private ArrayList<View> cards;
    //this is the variable that tracks how far you move your finger in the x-axis
    private float x;
    //this is the variable that tracks how far you move your finger in the y-axis
    private float y;

    //this assigns all those variables
    //it is called from CardInitializer
    //in the setUp method
    public void id(LinearLayout[] tag3, LinearLayout tag, ArrayList<View> tag2,ArrayList<View> tag4){
        hand = tag3;
        second = tag;
        inspect=tag2;
        cards=tag4;
    }

    //this rotates the players when called
    //the system is that there are three layouts that are the player hands
    //only one is visible and active at a time
    //it is called from TurnRotator
    //in the Dropper class
    void increase(){
        handNum=(handNum+1)%3;
    }

    //this activates when a view with a dragger assigned to it is touched
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        //makes the class motionEvent perform the getAction function
        //motionEvents track what is touching the screen
        //ACTION_DOWN means that someone just started touching it
        //ACTION_MOVE means that you are still touching it
        //ACTION_UP means that you have released it
        //...those last two don't always work
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //upon touching the card
                //mark that it has not been dragged yet
                dragged =false;
                //get the initial coordinates of your finger
                x=motionEvent.getX();
                y=motionEvent.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                //if you move your finger further than 10 units in any direction
                if(absoluteDistance(motionEvent.getX(),motionEvent.getY())>10) {
                    //initiate the drag
                    //makes a clipData
                    ClipData data;
                    //puts the view's description in the clipdata
                    data = ClipData.newPlainText("", view.getContentDescription() + "");
                    //make a drag shadow
                    //that follows your finger around transparently
                    View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                    //if advancer is ZERO
                    //play from the hand
                    if (view.getParent() == hand[handNum] && advancer == 0) {
                        //mark that a drag has happened
                        dragged =true;
                        //start the drag
                        //this signals the dropper
                        view.startDrag(data, shadowBuilder, view, 1);
                        //hide the view
                        //otherwise you would have a view in the layout and a copy in the drag shadow
                        view.setVisibility(View.INVISIBLE);
                    }
                    //if advancer is ONE
                    //play from the second
                    else if (view.getParent() == second && advancer == 1) {
                        //same as before
                        dragged =true;
                        view.startDrag(data, shadowBuilder, view, 1);
                        view.setVisibility(View.INVISIBLE);
                    }
                    //if advancer is NEITHER
                    //the turn is over
                }
                break;
            case MotionEvent.ACTION_UP:
                //first, we need to put this if statement here
                //even though this is only supposed to activate when getAction returns a 1
                //it doesn't. It just activates whenever it feels like it
                //so we've got two layers of checking
                if(motionEvent.getAction()==1){
                    //set the view to visiblr when you release it
                    //if this doesn't happen you can't interact with the view again at all
                    //and it softlocks the game sometimes
                    view.setVisibility(View.VISIBLE);
                }
                //now if no drag happened
               if(motionEvent.getAction()==1&&!dragged){
                    //go through the arrays of regular and display cards
                    for (int i = 0; i < inspect.size(); i++) {
                        //hide any display card that might already have been up
                        inspect.get(i).setVisibility(View.GONE);
                        //if the touched card matches a regular card
                        if(cards.get(i)==view){
                            //display that regular card's counterpart
                            //both arrays have the cards in the same order so this works
                            inspect.get(i).setVisibility(View.VISIBLE);
                        }
                    }
                }
                break;
            default:
                //stops if there is no more dragging
                break;
        }
        //this allows you to receive updates on the status of the dragged thing
        //essentially
        //returning true means that it consumes that motionEvent and continues to use it
        //returning false would mean the motionEvent is passed on to any other listeners on the view
        return true;
    }

    //this sets the advancer to show what layout you can draw from
    //this is called from both onDrag and turnRotator
    //in the dropper class
    void setAdvancer(int num){
        advancer =num;
    }
    //this returns what the advancer is currently at
    //this is called from the onDrag method
    //in the dropper class
    int getAdvancer(){
        return advancer;
    }

    //this is the function that takes the x-distance and the y-distance
    //and turns it into absolute distance
    private float absoluteDistance(float X, float Y){
        //get the distances moved
        float xDist = Math.abs(x-X);
        float yDist = Math.abs(y-Y);
        //do pythagoras to it
        return (float)(Math.sqrt((double)(yDist*yDist+xDist*xDist)));
    }
}

