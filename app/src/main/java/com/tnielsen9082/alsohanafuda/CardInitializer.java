package com.tnielsen9082.alsohanafuda;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

//this is the main activity
//so let's define some terms
//you play the game by matching cards from your hand with cards from the board that have the same suit
//suit can also be called "month" or "flower," but here it is usually month
//you drag the cards and drop them on the matching cards
//you play one card from your hand
//you draw and play one card from the (hidden) deck per turn
//each player needs to play both cards to keep the pattern going
//else not everyone gets an equal number of turns

//a "game" is 12 rounds
//a "round" consists of
//laying out all the cards
//and then taking cards until there are none left
//a "turn" is a player playing a card from their hand
//and then a card that they drew


//the board is the layout on top of the screen
//in a physical game it would be laid out between the players
//you take cards from there to get points
//you put cards in there if you have no matches

//the hand is the layout on the bottom of the screen
//you press and drag cards from there onto the board
//if the card matches the card it's dropped on
//you get the points for those cards
//if the card does not match its location
//but does match at least one different card in the board
//the card returns to your hand and you have to play it on the correct one(s)

//the end turn button ends the turn as expected
//BUT it also handles ending the round
//see the TurnClicker class for details

//when a round ends, the "turn splitter" screen shows up
//this fills the whole screen and has a dismissal button in the middle
//it is a layout that covers the whole screen
//and contains an imageView and the button
//not a new activity
//it says which player is up next
//the intended use is to pass the phone to the next person while the screen is up
//so that nobody else can see their hand

//"tricks" are what the pair of taken cards are called
//it is also the term for the hidden layouts that the cards go to once taken

//then there are combos
//if you have certain combinations of cards you get extra points
//see the countUp method for details

//the "dragger" and the "dropper" are the two classes that deal with dragging and dropping
//the dragger is assigned to all cards
//if the card is touched and held, the dragger activates
//you can now drag the card around
//the dropper is assigned to all cards and the board
//if a dragged card is dropped (released) on top of a View with a dropper
//the dropper activates
//and either accepts the card, putting it into the new layout
//or rejects it
//see the Dragger and Dropper classes for more details

//this activity's total goal is to set up the layouts, the buttons, the dragger, and the dropper
//add the combo points at the end
//and transfer all necessary data to the next activity
public class CardInitializer extends AppCompatActivity {
    //the list of all the cards
    ArrayList<View> cards = new ArrayList<>();
    //in case I ever use logs
    private static final String TAG = "MainActivity";
    //the views displaying the players' scores
    private TextView[] scores = new TextView[3];
    //the players' names
    private String[] names= new String[3];
    //the layouts where the cards taken by the players go
    private LinearLayout[] tricks=new LinearLayout[3];
    //by default the rain combo is turned off
    //see countUp for more details
    private boolean wipe =false;
    //the number of turns
    private int turnCount;
    //the method that triggers when the activity is created
    //it sets up the graphics
    //and calls all the other setting-up methods
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //set the xml layout file
        setContentView(R.layout.main_game);
        //set temporary background
        getWindow().getDecorView().setBackgroundColor(Color.LTGRAY);
        //lock it in landscape mode
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        //get the intent that started the activity
        Intent intention = getIntent();
        //extract the bundle of data that came with it
        final Bundle bundle = intention.getExtras();
        //pull all the data out of the bundle
        //the bits that aren't scoresInit are assigned to global variables
        String[] scoresInit =bundleExtractor(bundle);
        //sets up the objects
        //draws the cards to hand and board
        //setUp returns the array of player hands that gets passed into drawCards
        drawCards(setUp(scoresInit));
    }
    //this disables the back button
    @Override
    public void onBackPressed() {
        //do nothing
    }
    //this pulls all the data out of the bundle
    //this is called from onCreate
    //in the same class
    public String[] bundleExtractor(Bundle bundle){
        //create the array of player's starting scores
        String[] scoresInit = new String[3];
        //if there is a bundle
        if(bundle!=null) {
            //extract the player's scores
            scoresInit[0] = bundle.get("scoreOne") + "";
            scoresInit[1] = bundle.get("scoreTwo") + "";
            scoresInit[2] = bundle.get("scoreThree") + "";
            //and their names
            //which get set to a global variable
            names[0]=bundle.get("pOne")+"";
            names[1]=bundle.get("pTwo")+"";
            names[2]=bundle.get("pThree")+"";
        }
        //if there is no bundle passed in
        else{
            //set default scores o 0
            scoresInit[0] = 0+"";
            scoresInit[1] = 0+"";
            scoresInit[2] = 0+"";
            //and default names
            names[0]="Player One";
            names[1]="Player Two";
            names[2]="Player Three";
        }
        //be really sure that our scores are not null
        //because setting a textView to null crashes the game
        for (int i = 0; i < scoresInit.length; i++) {
            if(scoresInit[i]==null){
                scoresInit[i]=0+"";
            }
        }
        //set whether the rain combo is on or not
        //see countUp for more info on that
        wipe=(boolean)bundle.get("rainStatus");
        //gets the number of turns that it has been
        turnCount=(int)bundle.get("turnCounter");
        //return the scores
        return scoresInit;

    }
    //this ends the activity
    //and calls the next one
    //it transfers over all the data
    //this is called from onClick
    //in the TurnClicker class
    public void goToScore(){
        //this ends the cardInitializer activity
        //and takes us to the scoring screen
        //or the final scoring screen
        //create the intent
        Intent myIntent;
        //end it at <11 to get 12 rounds
        if(turnCount<2) {
            //if there are still rounds remaining
            //aim the intent to the regular scorer
            myIntent = new Intent(CardInitializer.this, Scorer.class);
        }
        else{
            //if it's the last round
            //aim the intent to the final scorer
            myIntent = new Intent(CardInitializer.this, FinalScorer.class);
        }
        //this counts the combos and returns an array of each player's points
        int[] bonusPoints =countUp();
        //you can put data in the intent
        //this is:
        //each player's score
        //plus combo
        myIntent.putExtra("scoreOne",Integer.parseInt(String.valueOf((scores[0]).getText()))+bonusPoints[0]);
        myIntent.putExtra("scoreTwo",Integer.parseInt(String.valueOf((scores[1]).getText()))+bonusPoints[1]);
        myIntent.putExtra("scoreThree",Integer.parseInt(String.valueOf((scores[2]).getText()))+bonusPoints[2]);
        //each player's name
        myIntent.putExtra("pOne",names[0]);
        myIntent.putExtra("pTwo",names[1]);
        myIntent.putExtra("pThree",names[2]);
        //whether or not the rain combo activates
        myIntent.putExtra("rainStatus",wipe);
        //and how many turns it's been
        myIntent.putExtra("turnCounter",turnCount);
        //start the new activity
        CardInitializer.this.startActivity(myIntent);
        //end this activity
        CardInitializer.this.finish();
    }
    //this takes the cards from the hidden deck layout
    //and puts them in each player's hand and the board
    //it's called from onCreate
    //in the same class
    //the array of player's hands is passed into it
    public void drawCards(LinearLayout[] hand){
        //initialize the deck layout
        LinearLayout drawPile = findViewById(R.id.drawPile);
        //for each player's hand
        for (int i = 0; i < 3; i++) {
            //and for their seven cards
            for (int j = 0; j < 7; j++) {
                //select a random imageView from the deck
                View card= drawPile.getChildAt((int)(Math.random()*(drawPile.getChildCount()-1)));
                //remove it from the draw pile
                drawPile.removeView(card);
                //put it in the current hand
                hand[i].addView(card);
            }
        }
        //for the board
        for (int i = 0; i < 6; i++) {
            //initialize the board
            LinearLayout board = findViewById(R.id.board);
            //take a random card from the deck
            View card = drawPile.getChildAt((int) (Math.random()*(drawPile.getChildCount() - 1)));
            //this all checks if there will be all of one suit on the board
            //this would softlock the game so we want to avoid it
            int four =0;
            for (int j = 0; j < board.getChildCount(); j++) {
                //for each card on the board that is the same suit as the randomly selected card
                if (board.getChildAt(j).getContentDescription().charAt(0)==card.getContentDescription().charAt(0)){
                    //add one to the counter
                    four++;
                }
            }
            //if three cards match the fourth card
            if (four==3){
                //subtract one from i
                //and run the loop again
                //do not put the fourth card in the board
                i--;
            }
            else{
                //remove it from the draw pile
                drawPile.removeView(card);
                //put it in the current hand
                board.addView(card);
            }
        }
    }
    //this is the big nasty class where I have to individually initialize each card and button and dragger and dropper
    //its main data sharing method is to create an object
    //and then call the method "id" on the object
    //which has receptors for all the data that the object needs
    //it's called from onCreate
    //in the same class
    //it returns the array of player's hands
    //which gets passed directly into the drawCards method
    public LinearLayout[] setUp(String[] scoresInit){
        //this initializes all the cards
        //makes all the droppers and buttons and whatnot and gives them their data
        //DOES NOT draw the cards to start the game
        //returns the array of player hands
        //SO IT BEGINS
        //here we take each ImageView of each card and put them in an array for easy access
        //pine
        cards.add(findViewById(R.id.pineCrane));
        cards.add(findViewById(R.id.pineNormalOne));
        cards.add(findViewById(R.id.pineNormalTwo));
        cards.add(findViewById(R.id.pineTanzaku));
        //plum
        cards.add(findViewById(R.id.plumBird));
        cards.add(findViewById(R.id.plumNormalOne));
        cards.add(findViewById(R.id.plumNormalTwo));
        cards.add(findViewById(R.id.plumTanzaku));
        //cherry
        cards.add(findViewById(R.id.cherryCurtain));
        cards.add(findViewById(R.id.cherryNormalOne));
        cards.add(findViewById(R.id.cherryNormalTwo));
        cards.add(findViewById(R.id.cherryTanzaku));
        //wisteria
        cards.add(findViewById(R.id.wisteriaCuckoo));
        cards.add(findViewById(R.id.wisteriaNormalOne));
        cards.add(findViewById(R.id.wisteriaNormalTwo));
        cards.add(findViewById(R.id.wisteriaTanzaku));
        //iris
        cards.add(findViewById(R.id.irisBridge));
        cards.add(findViewById(R.id.irisNormalOne));
        cards.add(findViewById(R.id.irisNormalTwo));
        cards.add(findViewById(R.id.irisTanzaku));
        //peony
        cards.add(findViewById(R.id.peonyButterfly));
        cards.add(findViewById(R.id.peonyNormalOne));
        cards.add(findViewById(R.id.peonyNormalTwo));
        cards.add(findViewById(R.id.peonyTanzaku));
        //clover
        cards.add(findViewById(R.id.cloverBoar));
        cards.add(findViewById(R.id.cloverNormalOne));
        cards.add(findViewById(R.id.cloverNormalTwo));
        cards.add(findViewById(R.id.cloverTanzaku));
        //pampas
        cards.add(findViewById(R.id.pampasGeese));
        cards.add(findViewById(R.id.pampasMoon));
        cards.add(findViewById(R.id.pampasNormalOne));
        cards.add(findViewById(R.id.pampasNormalTwo));
        //chrysanthemum
        cards.add(findViewById(R.id.chrysCup));
        cards.add(findViewById(R.id.chrysNormalOne));
        cards.add(findViewById(R.id.chrysNormalTwo));
        cards.add(findViewById(R.id.chrysTanzaku));
        //maple
        cards.add(findViewById(R.id.mapleDeer));
        cards.add(findViewById(R.id.mapleNormalOne));
        cards.add(findViewById(R.id.mapleNormalTwo));
        cards.add(findViewById(R.id.mapleTanzaku));
        //rain
        cards.add(findViewById(R.id.rainBird));
        cards.add(findViewById(R.id.rainLightning));
        cards.add(findViewById(R.id.rainPoet));
        cards.add(findViewById(R.id.rainTanzaku));
        //paulownia
        cards.add(findViewById(R.id.paulNormalOne));
        cards.add(findViewById(R.id.paulNormalTwo));
        cards.add(findViewById(R.id.paulNormalThree));
        cards.add(findViewById(R.id.paulPhoenix));

        //each card needs to be individually put into the card list
        //but only once

        //put the hand layouts in an array
        LinearLayout[] hands ={findViewById(R.id.handOne),findViewById(R.id.handTwo),findViewById(R.id.handThree)};
        //put the tricks (layout) in an array
        tricks[0]=findViewById(R.id.tricks);
        tricks[1]=findViewById(R.id.tricksTwo);
        tricks[2]=findViewById(R.id.tricksThree);

        //initialize the dropper
        Dropper dropper = new Dropper();
        //initialize the dragger
        //give it the array of players' hands
        //and secondCard
        //which is the layout where the second card that you draw is placed before you play it
        Dragger dragger = new Dragger();
        dragger.id(hands, (LinearLayout)findViewById(R.id.secondCard));

        //make an array of the scoreboards
        scores[0] = findViewById(R.id.score1);
        scores[1] = findViewById(R.id.score2);
        scores[2] = findViewById(R.id.score3);
        //set the scoreboards to display the correct scores
        for (int i = 0; i < 3; i++) {
            scores[i].setText(scoresInit[i]);
        }
        //send the data to the dropper
        //okay
        //give it the array of tricks (layout)
        //the board layout
        //the array of score TextViews
        //the array of player hands (layout)
        //the dragger
        //the turn splitter
        //the layout where the second card goes
        //the draw pile (hidden)
        //the array of player names (String)
        //and the TextView where the player names get displayed
        dropper.id(tricks,(LinearLayout)findViewById(R.id.board),scores, hands,dragger,(ConstraintLayout) findViewById(R.id.turnSplitter),(LinearLayout) findViewById(R.id.secondCard),(LinearLayout)findViewById(R.id.drawPile),names,(TextView)findViewById(R.id.playerNameMain));
        //it has to be done in this order so that the dragger and dropper can access each other

        //give all the cards draggers and droppers
        for (int i = 0; i < cards.size(); i++) {
            //nice and easy
            cards.get(i).setOnTouchListener(dragger);
            cards.get(i).setOnDragListener(dropper);
        }
        //give the board a dropper as well
        findViewById(R.id.board).setOnDragListener(dropper);

        //make the end turn/round button
        Button end = findViewById(R.id.endTurn);
        //make the code that ties to the button
        //give it the dropper
        //the tag saying it's the end turn/round button and not the turn splitter dismissal button
        //the second card layout
        //the array of player hands (layout)
        //the array of player names (String)
        //the TextView that displays which player is up next
        //and the button itself
        TurnClicker ender = new TurnClicker(dropper,false,this,(LinearLayout) findViewById(R.id.secondCard),hands,names,(TextView)findViewById(R.id.nextPlayerAnnounce),end);
        //give the click listener to the button
        end.setOnClickListener(ender);
        //disable the button to begin with
        end.setEnabled(false);
        //then we pass it to the dropper
        //so that the dropper can signal it that the player had played a card
        //and the player cannot just skip their turn
        dropper.setTurnClicker(ender);

        //make the turn splitter dismissal button
        Button turn = findViewById(R.id.nextTurn);
        //make the code that ties to the button
        //give it the dropper
        //the tag saying it's the turn splitter dismissal button and not the end turn/round button
        //the second card layout
        //the array of player hands (layout)
        //the array of player names (String)
        //the TextView that displays which player is up next
        //and the button that ends the round
        TurnClicker turner = new TurnClicker(dropper,true,this,(LinearLayout) findViewById(R.id.secondCard),hands,names,(TextView)findViewById(R.id.nextPlayerAnnounce),end);
        turn.setOnClickListener(turner);

        //preset the TextView to the first player's name
        ((TextView) findViewById(R.id.playerNameMain)).setText(names[0]);
        //return the LinearLayout[] of hands for passage to drawCards
        return hands;
    }
    //this adds the combo points to each player's score
    //this is called from goToScore
    //in the same class
    public int[] countUp(){
        //make a custom comboList
        ComboList combo = new ComboList();
        //make an arrayList of cards for the first player
        ArrayList<CardImage> cardsOne = new ArrayList<>();
        //tricks is the array of layouts where each player's taken cards go
        for (int i = 0; i < tricks[0].getChildCount(); i++) {
            //create a cardImage for each card image in the layout
            //mon is the letter corresponding to the month of the card
            //sco is the point value of the card
            CardImage images = new CardImage(tricks[0].getChildAt(i).getContentDescription().charAt(0)+"",Integer.parseInt(String.valueOf(tricks[0].getChildAt(i).getContentDescription().subSequence(1,3))));
            //add it to the array
            cardsOne.add(images);
        }
        //make player two's array
        ArrayList<CardImage> cardsTwo = new ArrayList<>();
        for (int i = 0; i < tricks[1].getChildCount(); i++) {
            //card image to cardImage
            CardImage images = new CardImage(tricks[1].getChildAt(i).getContentDescription().charAt(0)+"",Integer.parseInt(String.valueOf(tricks[1].getChildAt(i).getContentDescription().subSequence(1,3))));
            //add it
            cardsTwo.add(images);
        }
        //do it for the third player
        ArrayList<CardImage> cardsThree = new ArrayList<>();
        for (int i = 0; i < tricks[2].getChildCount(); i++) {
            CardImage images = new CardImage(tricks[2].getChildAt(i).getContentDescription().charAt(0)+"",Integer.parseInt(String.valueOf(tricks[2].getChildAt(i).getContentDescription().subSequence(1,3))));
            cardsThree.add(images);
        }
        //add the points that each player gets from combos to their current scores
        //combo.checker + a cardImage array goes through and sees if there are any combos
        //the wipe boolean is to specify if the rain combo invalidates all combos
        //if it's true and a player has all four rain cards
        //the method returns -1
        //okay
        //make an array of the bonus points that each player earned
        int[] comboPlus= {combo.checker(cardsOne,wipe),combo.checker(cardsTwo,wipe),combo.checker(cardsThree,wipe)};
        //check if any of them had the rain combo while it's activated
        boolean rain = false;
        for (int comboPlu : comboPlus) {
            if (comboPlu == -1) {
                rain = true;
            }
        }
        //if none of them had it
        //return the combo points
        if(!rain){
            return comboPlus;
        }
        //else return an array of zeros
        else{
            return new int[]{0,0,0};
        }

    }
}