package com.tnielsen9082.alsohanafuda;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

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

public class TurnEnder implements View.OnClickListener{
    //in case I use logs
    private static final String TAG = "TurnClicker";
    //the layout that the second drawn card goes to
    private LinearLayout second;
    //the main activity
    private AppCompatActivity cardInitializer;
    //tracking which player's turn it is
    private int playerTurn =1;
    //the player hands
    private LinearLayout[] hands;
    //the "end turn" button
    private Button endTurn;
    private HideTakenTricks trickHider;
    private TurnRotator turnRotator;
    //this assigns all of those variables
    //it is called from the MainGame class
    public void id(AppCompatActivity activity, LinearLayout[] handsTag,HideTakenTricks trickHiderTag, TurnRotator turnRotatorTag){
        cardInitializer =activity;
        hands=handsTag;
        trickHider =trickHiderTag;
        turnRotator = turnRotatorTag;
        endTurn = activity.findViewById(R.id.endTurn);
        second =cardInitializer.findViewById(R.id.secondCard);
    }
    //this is called from Dropper
    //in the general onDrag method in many places
    //when the current player plays their second card
    //if the deck runs out prematurely
    //then it gets called anyway from the secondCard method
    //also in Dropper
    void hasPlayed(){
        //turn the button on
        endTurn.setEnabled(true);
        endTurn.setVisibility(View.VISIBLE);
    }
    int getHandNum(){
        return playerTurn;
    }

    @Override
    //this is what happens if you click the button
    public void onClick(View v) {
            //this ends the current turn
            //and brings up the turn splitter screen
            //or ends the round if the cards have all been taken

            //if there is no card in the second card layout
            //and the player had played a card
            if(second.getChildCount()==0) {
                trickHider.onClick(v);
                //advance the players' turns
                playerTurn= (playerTurn+1)%3;
                //disable itself
                endTurn.setEnabled(false);
                endTurn.setVisibility(View.INVISIBLE);
                int oneTurnLeft = 0;
                for (int i = 0; i < 3; i++) {
                    if(hands[i].getChildCount()>=1){
                        oneTurnLeft++;
                    }
                }
                if(oneTurnLeft==1){
                    endTurn.setText(R.string.end_round);
                }
                //if all the hands are out of cards it will change to the scoring screen at the end
                boolean done = true;
                for (int i = 0; i < 3; i++) {
                    if(hands[i].getChildCount()!=0){
                        done=false;
                    }
                }
                //if no hand had cards left
                if(done) {
                    //call the activity ender on cardInitializer
                    ((MainGame)cardInitializer).goToScore();
                }
                else {
                    //call the dropper's rotate turns function
                    turnRotator.rotate();
                }
        }
    }
}

