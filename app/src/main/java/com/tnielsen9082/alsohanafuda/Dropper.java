package com.tnielsen9082.alsohanafuda;

import android.content.ClipData;
import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

//this is the second half of the drag and drop functionality
//

//if you put a card on another card of the same month
//they go to the scoring pile

//if the card doesn't match the month
//but it matches another card in the board
//the card returns for your hand for you to play again

//if the card doesn't match anything on the board
//but another card in your hand does
//the card returns for your hand for you to play again

//if no cards match anywhere
//the card goes to the board

//if the board is empty
//the card goes to the board

public final class Dropper implements View.OnDragListener {
    //in case I ever use logs
    private static final String TAG = "Dropper";
    //where the taken cards go
    private LinearLayout[] tricks;
    //the board
    private LinearLayout board;
    //displays the score
    private TextView[] score;
    //the dragger
    private Dragger drag;
    //the layout where the second card goes
    private LinearLayout second;
    //the draw pile
    private LinearLayout draw;
    //the turnClicker
    private TurnClicker turnClicker;
    private TurnRotator turnRotator;
    private int player;
    private LinearLayout container;
    private ViewGroup owner;
    private View dragger;
    private View dropper;
    private CharSequence dragID;
    private CharSequence dropID;
    private Character dragMonth;
    private Character dropMonth;
    private int dragPoints;
    private int dropPoints;
    private int sco;

    public void id(LinearLayout[] tricksTag, LinearLayout boardTag, TextView[] scoreTag, Dragger dragTag, LinearLayout secondTag, LinearLayout drawTag, TurnRotator turnRotatorTag){
        //initializing all those variables
        tricks = tricksTag;
        board = boardTag;
        score = scoreTag;
        drag = dragTag;
        second = secondTag;
        draw=drawTag;
        turnRotator = turnRotatorTag;
    }
    void setTurnClicker(TurnClicker turner){
        turnClicker=turner;
    }
    @Override
    //when a drag is started this activates
    public boolean onDrag(View drop, DragEvent event) {
        //calls getAction on the drag event
        //which tells you if it has:
            /*started
              entered the box
              left the box
              dropped
              still in the box
              or force quit
             */
        //we don't actually use this though
        //int action = event.getAction();
        //a new Java syntax appeared!
        //it looks for the action like I said
        //and then performs whichever case matches that action
        dropper =drop;
        dragger = (View) event.getLocalState();
        owner = (ViewGroup) dragger.getParent();
        switch (event.getAction()) {
            case DragEvent.ACTION_DRAG_STARTED:
                break;
            case DragEvent.ACTION_DRAG_ENTERED:
                break;
            case DragEvent.ACTION_DRAG_EXITED:
                break;
            case DragEvent.ACTION_DROP:
                player = turnRotator.getPlayerNum();
                sco = Integer.parseInt(String.valueOf(score[player].getText()));
                //get the description of each card
                dragID = dragger.getContentDescription();
                dropID = dropper.getContentDescription();
                if(dropID!=null){
                    //get the ids of each card
                    //which are hacked around via the ContentDescription
                    //in a move that I am sure is programming crime
                    //but it worked fine for solitaire, so
                    container = (LinearLayout)dropper.getParent();
                    //the month of the dragged card
                    dragMonth = dragID.charAt(0);
                    //the month of the receiving card
                    //(returns null if the receiver is the layout)
                    dropMonth = dropID.charAt(0);
                    //the points of the dragged card
                    dragPoints = Integer.parseInt(String.valueOf(dragID.subSequence(1,3)));
                    //the points of the dropped card
                    //(returns null if the receiver is the layout)
                    dropPoints = Integer.parseInt(String.valueOf(dropID.subSequence(1, 3)));

                    //if you are not dropping in your own container
                    //and the suits are the same
                    if(container==board&&dropMonth==dragMonth) {
                        sameSuitOnTarget();
                    }
                    else {
                        boolean match = false;
                        for (int i = 0; i < container.getChildCount(); i++) {
                            for (int j = 0; j < ((ViewGroup) dragger.getParent()).getChildCount(); j++) {
                                //checks if there are any matching cards between the hand and the board
                                if(container.getChildAt(i).getContentDescription().charAt(0)==((ViewGroup) dragger.getParent()).getChildAt(j).getContentDescription().charAt(0)){
                                    match=true;
                                }
                            }
                        }
                        if(container==board&&!match){
                            sameSuitNotOnTarget();
                        }
                        //there is no need to add code for if there are indirect matches
                    }
                }
                else{
                   container=(LinearLayout)dropper;
                   //if the container is empty
                    //put the card in it
                   if(container.getChildCount()==0){
                       owner.removeView(dragger);
                       container.addView(dragger);
                       if(drag.getAdvancer()==0) {
                           secondCard();
                       }
                       else{
                           drag.setAdvancer(3);
                       }
                       if(owner==second){
                           turnClicker.hasPlayed();
                       }
                   }
                   else{
                       boolean match = false;
                       for (int i = 0; i < container.getChildCount(); i++) {
                           for (int j = 0; j < ((ViewGroup) dragger.getParent()).getChildCount(); j++) {
                               //checks if there are any matching cards between the hand and the board
                               if(container.getChildAt(i).getContentDescription().charAt(0)==((ViewGroup) dragger.getParent()).getChildAt(j).getContentDescription().charAt(0)){
                                   match=true;
                               }
                           }
                       }
                       if(!match){
                           owner.removeView(dragger);
                           container.addView(dragger);
                           if(drag.getAdvancer()==0) {
                               secondCard();
                           }
                           else{
                               drag.setAdvancer(3);
                           }
                           if(owner==second){
                               turnClicker.hasPlayed();
                           }
                       }
                   }
                }
                break;
            case DragEvent.ACTION_DRAG_ENDED:
                showCards();
                break;
            default:
                //stops if there is no more dragging
                break;
        }
        //this allows you to receive updates on the status of the dragged thing
        return true;
    }
    private void sameSuitOnTarget(){
        int same =0;
        ArrayList<View> indices = new ArrayList<>();
        for (int i = 0; i < container.getChildCount(); i++) {
            if(container.getChildAt(i).getContentDescription().charAt(0)==dragMonth){
                same++;
                indices.add(container.getChildAt(i));
            }
        }
        if(same<3) {
            owner.removeView(dragger);
            container.removeView(dropper);

            //add the views to the middle
            tricks[player].addView(dragger);
            tricks[player].addView(dropper);
            //update the score
            sco += dropPoints;
            sco += dragPoints;
        }
        else{
            owner.removeView(dragger);
            tricks[player].addView(dragger);
            for (int i = 0; i < 3; i++) {
                container.removeView(indices.get(i));
                tricks[player].addView(indices.get(i));
                sco+= Integer.parseInt(String.valueOf(indices.get(i).getContentDescription().subSequence(1,3)));
            }
            sco += dragPoints;
        }
        score[player].setText(sco + "");
        if(drag.getAdvancer()==0) {
            secondCard();
        }
        else{
            drag.setAdvancer(3);
        }
        if(owner==second){
            turnClicker.hasPlayed();
        }
    }
    private void sameSuitNotOnTarget(){
        //if there are no matches
        //put the card in the board
        owner.removeView(dragger);
        container.addView(dragger);
        if(drag.getAdvancer()==0) {
            secondCard();
        }

        else{
            drag.setAdvancer(3);
        }
        if(owner==second){
            turnClicker.hasPlayed();
        }
    }
    private void showCards(){
        if(dropper.getContentDescription()==null){
            dragger.setVisibility(View.VISIBLE);
            for (int i = 0; i < owner.getChildCount(); i++) {
                owner.getChildAt(i).setVisibility(View.VISIBLE);
            }
        }
    }
    private void secondCard(){
        //draws a card to the second slot
        View card;
        if(draw.getChildCount()!=0) {
            card = draw.getChildAt((int) (Math.random() * (draw.getChildCount() - 1)));
            //remove it from the draw pile
            draw.removeView(card);
            //put it in the current hand
            second.addView(card);
            drag.setAdvancer(1);
        }
        else{
            drag.setAdvancer(3);
            turnClicker.hasPlayed();
        }
    }
}


