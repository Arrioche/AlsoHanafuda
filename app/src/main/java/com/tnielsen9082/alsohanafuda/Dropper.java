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

//this is the class that the receptacles get
public final class Dropper implements View.OnDragListener {
    //the clip that will be received from the dropper
    private CharSequence clip;
    //in case I ever use logs
    private static final String TAG = "MainActivity";
    //where the taken cards go
    private LinearLayout[] tricks;
    //actually the board
    private LinearLayout deck;
    //displays the score
    private TextView[] score;
    //the dragger
    private Dragger drag;
    //the clicker
    private Clicker click;
    //to keep track of the current player
    private int playerNum =0;
    //the array of player hands
    private LinearLayout[] hands;
    //the screen that breaks up turns
    private ConstraintLayout splitter;
    //the layout where the second card goes
    private LinearLayout second;
    //the draw pile
    private LinearLayout draw;
    //the array of player names
    private String[] names;
    //the place where names are displayed
    private TextView nameDisp;

    public void id(LinearLayout[] tag, LinearLayout tag2, TextView[] tag3, LinearLayout[] tag4, Dragger tag5, Clicker tag6, ConstraintLayout tag7, LinearLayout tag8, LinearLayout tag9,String[] tag10,TextView tag11){
        //initializing all those variables
        tricks = tag;
        deck = tag2;
        score = tag3;
        hands= tag4;
        drag = tag5;
        click = tag6;
        splitter= tag7;
        second = tag8;
        draw=tag9;
        names = tag10;
        nameDisp = tag11;

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
                int sco =Integer.parseInt(String.valueOf(score[playerNum].getText()));
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
                    if(container==deck&&dropMonth==dragMonth) {
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
                            tricks[playerNum].addView(dragger);
                            tricks[playerNum].addView(dropper);
                            //update the score
                            sco += dropPoints;
                            sco += dragPoints;
                            score[playerNum].setText(sco + "");
                        }
                        else{
                            owner.removeView(dragger);
                            container.removeView(indices.get(0));
                            container.removeView(indices.get(1));
                            container.removeView(indices.get(2));
                            tricks[playerNum].addView(dragger);
                            tricks[playerNum].addView(indices.get(0));
                            tricks[playerNum].addView(indices.get(1));
                            tricks[playerNum].addView(indices.get(2));
                        }
                        if(drag.getAdvancer()==0) {
                            secondCard();
                        }
                        else{
                            drag.advancer(3);
                        }
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
                        if(!match){
                            //put the card in the board
                            owner.removeView(dragger);
                            container.addView(dragger);
                            if(drag.getAdvancer()==0) {
                                secondCard();
                            }
                            else{
                                drag.advancer(3);
                            }
                        }
                        //there is no need to add code for if there are indirect matches
                        //the card will either be a direct match, and be placed
                        //or it will have no matches, and be placed
                        //we can just end the code and the card will return in the third situation
                        //of indirect matches
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
                           drag.advancer(3);
                       }
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
        hands[playerNum%3].setVisibility(View.GONE);
        //hide the old score
        score[playerNum%3].setVisibility(View.INVISIBLE);
        //rotate the dropper
        playerNum=(playerNum+1)%3;
        //rotate the names
        nameDisp.setText(names[playerNum]);
        //show the new hand
        hands[(playerNum)].setVisibility(View.VISIBLE);
        //show the new score
        score[(playerNum)].setVisibility(View.VISIBLE);
        //rotate the dragger
        drag.increase();
        //rotate the clicker
        click.increase();
        splitter.setVisibility(View.VISIBLE);
        drag.advancer(0);
    }
    public void secondCard(){
        //draws a card to the second slot
        View card;
        if(draw.getChildCount()!=0) {
            card = draw.getChildAt((int) (Math.random() * (draw.getChildCount() - 1)));
            //remove it from the draw pile
            draw.removeView(card);
            //put it in the current hand
            second.addView(card);
            drag.advancer(1);
        }
        else{
            drag.advancer(3);
        }
    }
}


