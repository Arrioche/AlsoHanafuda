package com.tnielsen9082.alsohanafuda;


import android.app.Activity;
import android.content.ClipData;

import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

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
    //the list of images that display full-size
    private ArrayList<Drawable> inspect;
    //the list of cards to compare the prior list to
    private ArrayList<View> cards;
    //the list of card descriptions that display full-size
    private ArrayList<String> cardDescs;
    //the place where the descriptions show up
    private TextView desc;
    //the card that displays
    private ImageView showCard;
    //this is the variable that tracks how far you move your finger in the x-axis
    private float x;
    //this is the variable that tracks how far you move your finger in the y-axis
    private float y;

    //this assigns all those variables
    public void id(Activity activity,LinearLayout[] handsTag, ArrayList<Drawable> inspectTag, ArrayList<View> cardsTag, ArrayList<String> cardDescsTag){
        hand = handsTag;
        inspect=inspectTag;
        cards=cardsTag;
        cardDescs=cardDescsTag;
        desc = activity.findViewById(R.id.cardInfo);
        second =activity.findViewById(R.id.secondCard);
        showCard= activity.findViewById(R.id.showCard); }

    //this rotates the players when called
    //the system is that there are three layouts that are the player hands
    //only one is visible and active at a time
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
                    dragged =true;
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
                //even though this is only supposed to activate when getAction returns a 1
                //it doesn't. It just activates whenever it feels like it
                //so we've got two layers of checking
                if(motionEvent.getAction()==1){
                    //set the view to visible when you release it
                    //if this doesn't happen you can't interact with the view again at all
                    //and it softlocks the game sometimes
                    view.setVisibility(View.VISIBLE);
                }
                //now if no drag happened
               if(motionEvent.getAction()==1&&!dragged){
                   showCard(view);
                }
                break;
            default:
                //stops if there is no more dragging
                break;
        }
        return true;
    }

    //this sets the advancer to show what layout you can draw from
    void setAdvancer(int num){
        advancer =num;
    }
    //this returns what the advancer is currently at
    int getAdvancer(){
        return advancer;
    }
    private void showCard(View view){
        //go through the arrays of regular and display cards
        for (int i = 0; i < inspect.size(); i++) {
            //if the touched card matches a regular card
            if(cards.get(i)==view){
                //display that regular card's counterpart
                //both arrays have the cards in the same order so this works
                showCard.setImageDrawable(inspect.get(i));
                desc.setText(cardDescs.get(i));
                ((View)showCard.getParent()).setVisibility(View.VISIBLE);
            }
        }
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

