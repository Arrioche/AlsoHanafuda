package com.tnielsen9082.alsohanafuda;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

//this is
//in retrospect
//a bad way to organize the buttons
//okay
//this is the button that ends the turn and brings up the turn splitter screen
//OR dismisses the turn splitter screen
//OR ends the round

//the boolean "dismissal" determines if it brings up or takes down the turn splitter
//true means taking down
//false means bringing up
//it is passed in when the button is created and does not change

//the bringing up button also ends the turn
//if the "done" boolean is true
//that's true when there are no cards left in any player's hand

//the end turn button can only be clicked when the player has played their regular card
//and their second card
//if a player was allowed to skip turns it would mess up the pattern of the cards

public class TurnClicker implements View.OnClickListener{
    //the boolean that determines if the button closes the turn splitter screen or opens it
    //if true
    //close
    //if false
    //open
    private boolean dismissal;
    //the layout that the second drawn card goes to
    private LinearLayout second;
    //the dropper
    private Dropper drop;
    //the main activity
    private AppCompatActivity cardInitializer;
    //tracking which player's turn it is
    private int playerTurn =1;
    //the player hands
    private LinearLayout[] hands;
    //the player names
    private String[] names;
    //the name display
    private TextView nameDisplay;
    //the "end turn" button
    private Button endTurn;
    //to make sure the player actually plays a card
    private boolean played = false;
    //this assigns all of those variables
    //it is called from the CardInitializer class
    TurnClicker(Dropper tag3, boolean tag2, AppCompatActivity tag1, LinearLayout tag4, LinearLayout[] tag5, String[] tag6, TextView tag7, Button tag8){
        drop=tag3;
        dismissal=tag2;
        second =tag4;
        cardInitializer =tag1;
        hands=tag5;
        names=tag6;
        nameDisplay = tag7;
        endTurn = tag8;
    }
    //this is called from Dropper
    //when the current player plays a card
    void hasPlayed(){
        played=true;
    }
    @Override
    //this is what happens if you click the button
    public void onClick(View v) {
        if(dismissal) {
            //this dismisses the turn splitter screen
            //get the parent of the button
            //which is the entire turn splitter
            ConstraintLayout parent = (ConstraintLayout) v.getParent();
            //hide it
            parent.setVisibility(View.GONE);
            //turn the end turn button back on
            endTurn.setEnabled(true);
        }
        else{
            //this ends the current turn
            //and brings up the turn splitter screen
            //or ends the round if the cards have all been taken

            //if there is no card in the second card layout
            //and the player had played a card
            if(second.getChildCount()==0&&played) {
                //set the text on the turn splitter
                nameDisplay.setText("Next Up: "+names[playerTurn]);
                //advance the players' turns
                playerTurn= (playerTurn+1)%3;
                //disable the end turn button
                //otherwise you can click it through the turn splitter
                endTurn.setEnabled(false);
                //reset played for the next player
                played=false;
                //if all the hands are out of cards
                //it also will change to the scoring screen at the end
                boolean done = true;
                //if any hand has cards left
                //turn off done
                for (int i = 0; i < 3; i++) {
                    if(hands[i].getChildCount()!=0){
                        done=false;
                    }
                }
                //if no hand had cards left
                if(done) {
                    //call the activity ender on cardInitializer
                    ((CardInitializer)cardInitializer).goToScore();
                }
                else {
                    //call the dropper's rotate turns function
                    drop.turnRotator();
                }
            }
        }
    }
}

