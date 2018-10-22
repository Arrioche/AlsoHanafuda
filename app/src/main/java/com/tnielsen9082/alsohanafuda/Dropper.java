package com.tnielsen9082.alsohanafuda;

import android.content.ClipData;
import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

//this is the class that the receptacles get
public final class Dropper implements View.OnDragListener {
    //the clip that will be received from the dropper
    private CharSequence clip;
    //in case I ever use logs
    private static final String TAG = "MainActivity";
    //where the taken cards go
    private LinearLayout tricks;
    private boolean done=true;
    //actually the board
    private LinearLayout deck;
    //displays the score
    private TextView[] score;
    //the dragger
    private Dragger drag;
    //the clicker
    private Clicker click;
    //to keep track of the current player
    private int handNum =0;
    private int scoreNum=0;
    //the array of player hands
    private LinearLayout[] hands;
    //the screen that breaks up turns
    private ConstraintLayout splitter;

    public void id(LinearLayout tag, LinearLayout tag2, TextView[] tag3, LinearLayout[] tag4, Dragger tag5, Clicker tag6, ConstraintLayout tag7){
        //initializing all those variables
        tricks = tag;
        deck = tag2;
        score = tag3;
        hands= tag4;
        drag = tag5;
        click = tag6;
        splitter= tag7;
    }
    @Override
    //when a drag is started this activates
    public boolean onDrag(View dropper, DragEvent event) {
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
        switch (event.getAction()) {
            case DragEvent.ACTION_DRAG_STARTED:
                //stop the code
                break;
            case DragEvent.ACTION_DRAG_ENTERED:
                //change the look to show it is droppable in
                break;
            case DragEvent.ACTION_DRAG_EXITED:
                //change the look back
                break;
            case DragEvent.ACTION_DROP:
                int sco =Integer.parseInt(String.valueOf(score[scoreNum].getText()));
                // Gets the item containing the dragged data
                ClipData.Item item = event.getClipData().getItemAt(0);
                // Gets the text data from the item.
                clip = item.getText();
                clip=clip.toString();
                View dragger = (View) event.getLocalState();
                //get the layouts that each card comes from
                ViewGroup owner = (ViewGroup) dragger.getParent();
                CharSequence dragID = dragger.getContentDescription();
                CharSequence dropID = dropper.getContentDescription();
                Log.w("TAG",dropID+"");
                LinearLayout container;
                if(dropID!=null){
                    //get the ids of each card
                    //which are hacked around via the ContentView
                    //in a move that I am sure is programming crime
                    //but it worked fine for solitaire, so
                    container = (LinearLayout)dropper.getParent();
                    //the month of the dragged card
                    Character dragMonth = dragID.charAt(0);
                    //the month of the receiving card
                    //(returns null if the receiver is the layout)
                    Character dropMonth = dropID.charAt(0);
                    //the points of the dragged card
                    int dragPoints = Integer.parseInt(String.valueOf(dragID.subSequence(1,3)));
                    //the points of the dropped card
                    //(returns null if the receiver is the layout)
                    int dropPoints = Integer.parseInt(String.valueOf(dropID.subSequence(1,3)));

                    //if you are not dropping in your own container
                    //and the suits are the same
                    if(container==deck&&dropMonth==dragMonth&&done) {
                        owner.removeView(dragger);
                        container.removeView(dropper);

                        //add the views to the middle
                        tricks.addView(dragger);
                        tricks.addView(dropper);
                        //update the score
                        sco+=dropPoints;
                        sco+=dragPoints;
                        score[scoreNum].setText(sco+"");
                        done=false;
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
                        //if there are no matches
                        if(!match&&done){
                            //put the card in the board
                            owner.removeView(dragger);
                            container.addView(dragger);
                            done=false;
                        }
                        //there is no need to add code for if there are indirect matches
                        //the card will either be a direct match, and be placed
                        //or it will have no matches, and be place
                        //we can just end the code and the card will return in the third situation
                        //of indirect matches
                    }
                }
                else{
                   container=(LinearLayout)dropper;
                   //if the container is empty
                    //put the card in it
                   if(container.getChildCount()==0&&done){
                       owner.removeView(dragger);
                       container.addView(dragger);
                       done=false;
                   }
                }

                break;
            case DragEvent.ACTION_DRAG_ENDED:

            default:
                //stops if there is no more dragging
                break;
        }
        //this allows you to receive updates on the status of the dragged thing
        return true;

        //in summation:
        //this is the function that manages the dropping of dragged cards
        //it can connect to the dragger and the clicker
        //it takes the cards and puts them where they are supposed to go
        //it also manages the turn rotation
        //as the first of four methods...
        //it's an important method

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
    }
    public void turnRotator(){
        //hide the old hand
        hands[handNum%3].setVisibility(View.GONE);
        //show the new hand
        hands[(handNum+1)%3].setVisibility(View.VISIBLE);
        //rotate the dropper
        handNum=(handNum+1)%3;
        //hide the old score
        score[scoreNum%3].setVisibility(View.GONE);
        //show the new score
        score[(scoreNum+1)%3].setVisibility(View.VISIBLE);
        //rotate the dropper
        scoreNum=(scoreNum+1)%3;
        //rotate the dragger
        (drag).increase();
        //draw a card to the PREVIOUS hand
        click.onClick(score[0]);
        //rotate the clicker
        click.increase();
        splitter.setVisibility(View.VISIBLE);
        //reset the turn
        done=true;
    }
}


