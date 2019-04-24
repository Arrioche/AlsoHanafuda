package com.tnielsen9082.alsohanafuda;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
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
//a "round" consists of laying out all the cards and then taking cards until there are none left
//a "turn" is a player playing a card from their hand and then a card that they drew

//the board is the layout on top of the screen
//in a physical game it would be laid out between the players
//you take cards from there to get points
//you put cards in there if you have no matches

//the hand is the layout on the bottom of the screen
//you press and drag cards from there onto the board
//if the card matches the card it's dropped on you get the points for those cards
//if the card does not match its location
//but does match at least one different card in the board
//the card returns to your hand and you have to play it on the correct one(s)

//the end turn button ends the turn as expected
//BUT it also handles ending the round
//see the TurnClicker class for details

//when a round ends, the "turn splitter" screen shows up
//this fills the whole screen and has a dismissal button in the middle
//it is a layout that contains an imageView and the button
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
//if a dragged card is dropped (released) on top of a View with a dropper the dropper activates
//and either accepts the card, putting it into the new layout or rejects it
//see the Dragger and Dropper classes for more details

//if you tap on a card instead of dragging it
//a larger version of that card's image will appear
//as well as a description of the card
//tap on this to dismiss it

public class MainGame extends AppCompatActivity {
    //the list of all the cards
    ArrayList<View> cards = new ArrayList<>();
    //the list of the cards that display when you click on a card
    ArrayList<Drawable> cardsDisp = new ArrayList<>();
    //the list of the descriptions that display when you click on a card
    ArrayList<String> cardDescs = new ArrayList<>();
    //in case I ever use logs
    private static final String TAG = "MainActivity";
    //the views displaying the players' scores
    private TextView[] scores = new TextView[3];
    //the players' names
    private String[] names= new String[3];
    //the layouts where the cards taken by the players go
    private LinearLayout[] tricks=new LinearLayout[3];
    //the array of player hands
    private LinearLayout[] hands = new LinearLayout[3];
    //by default the rain combo is turned off
    //see countUp for more details
    private boolean rainStatus =false;
    //the number of turns
    private int turnCount;
    private int turnTotal;
    //the method that triggers when the activity is created
    //it sets up the graphics
    //and calls all the other setting-up methods
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //set the xml layout file
        setContentView(R.layout.main_game);
        //set temporary background
        getWindow().getDecorView().setBackgroundColor(Color.BLACK);
        //lock it in landscape mode
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        methodBundle();
    }
    private void methodBundle(){
        Intent intention = getIntent();
        final Bundle bundle = intention.getExtras();
        String[] scoresInit =bundleExtractor(bundle);
        arraySetup();
        //sets up the listeners
        classSetUp(scoresInit);
        //draws the cards
        drawCards();
    }

    @Override
    public void onBackPressed() {
        //do nothing
    }
    @Override
    protected void onResume() {
        super.onResume();
        View decorView = getWindow().getDecorView();
        // Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
    }
    public String[] bundleExtractor(Bundle bundle){
        String[] scoresInit = new String[3];
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
        else{
            //set default scores to 0
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
        rainStatus=(boolean)bundle.get("rainStatus");
        //gets the number of turns that it has been
        turnCount=(int)bundle.get("turnCounter")+1;
        turnTotal=(int)bundle.get("turnTotal");
        //return the scores
        return scoresInit;

    }
    //this ends the activity and calls the next one
    //it transfers over all the data
    public void goToScore(){
        Intent myIntent;
        //end it at <11 to get 12 rounds
        if(turnCount<turnTotal-1) {
            //if there are still rounds remaining aim the intent to the regular scorer
            myIntent = new Intent(MainGame.this, Scorer.class);
        }
        else{
            //if it's the last round aim the intent to the final scorer
            myIntent = new Intent(MainGame.this, FinalScorer.class);
            myIntent.putExtra("turnTotal",turnTotal);
        }
        //this counts the combos and returns an array of each player's points
        ComboCounter comboCounter = new ComboCounter();
        int[] bonusPoints =comboCounter.countUp(tricks,rainStatus);
        //you can put data in the intent
        //each player's score
        myIntent.putExtra("scoreOne",Integer.parseInt(String.valueOf((scores[0]).getText()))+bonusPoints[0]);
        myIntent.putExtra("scoreTwo",Integer.parseInt(String.valueOf((scores[1]).getText()))+bonusPoints[1]);
        myIntent.putExtra("scoreThree",Integer.parseInt(String.valueOf((scores[2]).getText()))+bonusPoints[2]);
        //plus combo
        myIntent.putExtra("bonusOne",bonusPoints[0]);
        myIntent.putExtra("bonusTwo",bonusPoints[1]);
        myIntent.putExtra("bonusThree",bonusPoints[2]);
        //each player's name
        myIntent.putExtra("pOne",names[0]);
        myIntent.putExtra("pTwo",names[1]);
        myIntent.putExtra("pThree",names[2]);
        //whether or not the rain combo activates
        myIntent.putExtra("rainStatus",rainStatus);
        //how many turns it's been
        myIntent.putExtra("turnCounter",turnCount);
        //how many turns there will be
        myIntent.putExtra("turnTotal",turnTotal);
        //start the new activity
        MainGame.this.startActivity(myIntent);
        //end this activity
        MainGame.this.finish();
    }
    //this takes the cards from the hidden deck layout
    //and puts them in each player's hand and the board
    //it's called from onCreate in the same class
    public void drawCards(){
        //initialize the deck layout
        LinearLayout drawPile = findViewById(R.id.drawPile);
        //for each player's hand
        for (int i = 0; i < 3; i++) {
            //and for their seven cards
            for (int j = 0; j < 7; j++) {
                View card= drawPile.getChildAt((int)(Math.random()*(drawPile.getChildCount()-1)));
                drawPile.removeView(card);
                hands[i].addView(card);
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
                if (board.getChildAt(j).getContentDescription().charAt(0)==card.getContentDescription().charAt(0)){
                    four++;
                }
            }
            if (four==3){
                i--;
            }
            else{
                drawPile.removeView(card);
                board.addView(card);
            }
        }
    }
    //this sets up the arrays of card ImageViews, card Drawables, and card descriptions
    //as well as the trick layouts and score TextViews
    public void arraySetup(){
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

        //here are the imageViews of the cards that will be displayed full-size for easy viewing
        //pine
        cardsDisp.add(getResources().getDrawable(R.drawable.pinecrane));
        cardsDisp.add(getResources().getDrawable(R.drawable.pinenormalone));
        cardsDisp.add(getResources().getDrawable(R.drawable.pinenormaltwo));
        cardsDisp.add(getResources().getDrawable(R.drawable.pinetanzaku));
        //plum
        cardsDisp.add(getResources().getDrawable(R.drawable.plumbird));
        cardsDisp.add(getResources().getDrawable(R.drawable.plumnormalone));
        cardsDisp.add(getResources().getDrawable(R.drawable.plumnormaltwo));
        cardsDisp.add(getResources().getDrawable(R.drawable.plumtanzaku));
        //cherry
        cardsDisp.add(getResources().getDrawable(R.drawable.cherrycurtain));
        cardsDisp.add(getResources().getDrawable(R.drawable.cherrynormalone));
        cardsDisp.add(getResources().getDrawable(R.drawable.cherrynormaltwo));
        cardsDisp.add(getResources().getDrawable(R.drawable.cherrytanzaku));
        //wisteria
        cardsDisp.add(getResources().getDrawable(R.drawable.wisteriacuckoo));
        cardsDisp.add(getResources().getDrawable(R.drawable.wisterianormalone));
        cardsDisp.add(getResources().getDrawable(R.drawable.wisterianormaltwo));
        cardsDisp.add(getResources().getDrawable(R.drawable.wisteriatanzaku));
        //iris
        cardsDisp.add(getResources().getDrawable(R.drawable.irisbridge));
        cardsDisp.add(getResources().getDrawable(R.drawable.irisnormalone));
        cardsDisp.add(getResources().getDrawable(R.drawable.irisnormaltwo));
        cardsDisp.add(getResources().getDrawable(R.drawable.iristanzaku));
        //peony
        cardsDisp.add(getResources().getDrawable(R.drawable.peonybutterfly));
        cardsDisp.add(getResources().getDrawable(R.drawable.peonynormalone));
        cardsDisp.add(getResources().getDrawable(R.drawable.peonynormaltwo));
        cardsDisp.add(getResources().getDrawable(R.drawable.peonytanzaku));
        //clover
        cardsDisp.add(getResources().getDrawable(R.drawable.cloverboar));
        cardsDisp.add(getResources().getDrawable(R.drawable.clovernormalone));
        cardsDisp.add(getResources().getDrawable(R.drawable.clovernormaltwo));
        cardsDisp.add(getResources().getDrawable(R.drawable.clovertanzaku));
        //pampas
        cardsDisp.add(getResources().getDrawable(R.drawable.pampasgeese));
        cardsDisp.add(getResources().getDrawable(R.drawable.pampasmoon));
        cardsDisp.add(getResources().getDrawable(R.drawable.pampasnormalone));
        cardsDisp.add(getResources().getDrawable(R.drawable.pampasnormaltwo));
        //chrysanthemum
        cardsDisp.add(getResources().getDrawable(R.drawable.chryscup));
        cardsDisp.add(getResources().getDrawable(R.drawable.chrysnormalone));
        cardsDisp.add(getResources().getDrawable(R.drawable.chrysnormaltwo));
        cardsDisp.add(getResources().getDrawable(R.drawable.chrystanzaku));
        //maple
        cardsDisp.add(getResources().getDrawable(R.drawable.mapledeer));
        cardsDisp.add(getResources().getDrawable(R.drawable.maplenormalone));
        cardsDisp.add(getResources().getDrawable(R.drawable.maplenormaltwo));
        cardsDisp.add(getResources().getDrawable(R.drawable.mapletanzaku));
        //rain
        cardsDisp.add(getResources().getDrawable(R.drawable.rainbird));
        cardsDisp.add(getResources().getDrawable(R.drawable.rainlightning));
        cardsDisp.add(getResources().getDrawable(R.drawable.rainpoet));
        cardsDisp.add(getResources().getDrawable(R.drawable.raintanzaku));
        //paulownia
        cardsDisp.add(getResources().getDrawable(R.drawable.paulnormalone));
        cardsDisp.add(getResources().getDrawable(R.drawable.paulnormaltwo));
        cardsDisp.add(getResources().getDrawable(R.drawable.paulnormalthree));
        cardsDisp.add(getResources().getDrawable(R.drawable.paulphoenix));

        //each card needs to be individually put into the card list
        //but only once
        //pine
        cardDescs.add(getResources().getString(R.string.a20desc));
        cardDescs.add(getResources().getString(R.string.a01desc));
        cardDescs.add(getResources().getString(R.string.a01desc));
        cardDescs.add(getResources().getString(R.string.a05desc));
        //plum
        cardDescs.add(getResources().getString(R.string.b10desc));
        cardDescs.add(getResources().getString(R.string.b01desc));
        cardDescs.add(getResources().getString(R.string.b01desc));
        cardDescs.add(getResources().getString(R.string.b05desc));
        //cherry
        cardDescs.add(getResources().getString(R.string.c20desc));
        cardDescs.add(getResources().getString(R.string.c01desc));
        cardDescs.add(getResources().getString(R.string.c01desc));
        cardDescs.add(getResources().getString(R.string.c05desc));
        //wisteria
        cardDescs.add(getResources().getString(R.string.d10desc));
        cardDescs.add(getResources().getString(R.string.d01desc));
        cardDescs.add(getResources().getString(R.string.d01desc));
        cardDescs.add(getResources().getString(R.string.d05desc));
        //iris
        cardDescs.add(getResources().getString(R.string.e10desc));
        cardDescs.add(getResources().getString(R.string.e01desc));
        cardDescs.add(getResources().getString(R.string.e01desc));
        cardDescs.add(getResources().getString(R.string.e05desc));
        //peony
        cardDescs.add(getResources().getString(R.string.f10desc));
        cardDescs.add(getResources().getString(R.string.f01desc));
        cardDescs.add(getResources().getString(R.string.f01desc));
        cardDescs.add(getResources().getString(R.string.f05desc));
        //clover
        cardDescs.add(getResources().getString(R.string.g10desc));
        cardDescs.add(getResources().getString(R.string.g01desc));
        cardDescs.add(getResources().getString(R.string.g01desc));
        cardDescs.add(getResources().getString(R.string.g05desc));
        //pampas
        cardDescs.add(getResources().getString(R.string.h10desc));
        cardDescs.add(getResources().getString(R.string.h20desc));
        cardDescs.add(getResources().getString(R.string.h01desc));
        cardDescs.add(getResources().getString(R.string.h01desc));
        //chrysanthemum
        cardDescs.add(getResources().getString(R.string.i10desc));
        cardDescs.add(getResources().getString(R.string.i01desc));
        cardDescs.add(getResources().getString(R.string.i01desc));
        cardDescs.add(getResources().getString(R.string.i05desc));
        //maple
        cardDescs.add(getResources().getString(R.string.j10desc));
        cardDescs.add(getResources().getString(R.string.j01desc));
        cardDescs.add(getResources().getString(R.string.j01desc));
        cardDescs.add(getResources().getString(R.string.j05desc));
        //rain
        cardDescs.add(getResources().getString(R.string.k10desc));
        cardDescs.add(getResources().getString(R.string.k01desc));
        cardDescs.add(getResources().getString(R.string.k20desc));
        cardDescs.add(getResources().getString(R.string.k05desc));
        //paulownia
        cardDescs.add(getResources().getString(R.string.l01desc));
        cardDescs.add(getResources().getString(R.string.l01desc));
        cardDescs.add(getResources().getString(R.string.l01desc));
        cardDescs.add(getResources().getString(R.string.l20desc));

        //put the tricks (layout) in an array
        tricks[0]=findViewById(R.id.tricks);
        tricks[1]=findViewById(R.id.tricksTwo);
        tricks[2]=findViewById(R.id.tricksThree);
        //make an array of the scoreboards
        scores[0] = findViewById(R.id.score1);
        scores[1] = findViewById(R.id.score2);
        scores[2] = findViewById(R.id.score3);

    }
    //this is the class where I have to individually initialize each button and dragger and dropper
    //its data sharing method is to create an object
    //and then call the method "id" on the object
    //which has receptors for all the data that the object needs
    //it's called from onCreate in the same class
    @SuppressLint("SetTextI18n")
    public void classSetUp(String[] scoresInit){
        //what hides the display cards
        CardDescDismisser dismisser = new CardDescDismisser();
        //what initiates drags
        Dragger dragger = new Dragger();
        //what accepts drags
        Dropper dropper = new Dropper();
        //what hides the cards that other people have taken
        HideTakenTricks trickHider = new HideTakenTricks();
        //what ends the current turn
        TurnEnder endTurn = new TurnEnder();
        //what starts the next turn
        TurnStarter startTurn = new TurnStarter();
        //the button that ends the turn
        Button end = findViewById(R.id.endTurn);
        //the button that starts the next turn
        Button turn = findViewById(R.id.nextTurn);
        Button combos =findViewById(R.id.combosDisp);
        TurnRotator turnRotator = new TurnRotator();
        ComboButton comboButton = new ComboButton();
        View backdrop = findViewById(R.id.turnBack);

        //set up the array of player's hands
        hands[0]=findViewById(R.id.handOne);
        hands[1]=findViewById(R.id.handTwo);
        hands[2]=findViewById(R.id.handThree);
        //set up the array of buttons
        Button[] trickButtons={findViewById(R.id.dispYou),findViewById(R.id.dispOne),findViewById(R.id.dispTwo)};
        //set up the initial buttons to display tricks
        if((names[1].toLowerCase().charAt(names[1].length()-1))=="s".charAt(0)){
            //and make sure they pluralize properly
            trickButtons[1].setText(names[1]+"' Cards");
        }
        else {
            trickButtons[1].setText(names[1]+"'s Cards");
        }
        if((names[2].toLowerCase().charAt(names[2].length()-1))=="s".charAt(0)){
            trickButtons[2].setText(names[2]+"' Cards");
        }
        else {
            trickButtons[2].setText(names[2]+"'s Cards");
        }
        //set the starting scores (this carries over between rounds)
        for (int i = 0; i < 3; i++) {
            scores[i].setText(scoresInit[i]);
        }
        //set the text to the first player's name
        ((TextView) findViewById(R.id.playerNameMain)).setText(names[0]);

        //give all the data to all the things that need it
        dragger.id(this, hands,cardsDisp,cards,cardDescs);
        dropper.id(this, tricks, scores,dragger,turnRotator,endTurn);
        trickHider.id(this, tricks,turnRotator);
        endTurn.id(this,hands,trickHider, turnRotator);
        turnRotator.id(this,hands,scores,dragger,names,trickButtons);
        comboButton.id(this,rainStatus);

        //assign various listeners to their spots
        findViewById(R.id.cardDisps).setOnTouchListener(dismisser);
        findViewById(R.id.board).setOnDragListener(dropper);
        findViewById(R.id.dismissal).setOnClickListener(trickHider);
        end.setOnClickListener(endTurn);
        turn.setOnClickListener(startTurn);
        combos.setOnClickListener(comboButton);
        backdrop.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        for (int i = 0; i < trickButtons.length; i++) {
            //this one has to be made within the for loop because it uses i]
            trickButtons[i].setOnClickListener(new ShowTakenTricks(this, tricks,i, endTurn, names));
        }
        for (int i = 0; i < cards.size(); i++) {
            cards.get(i).setOnTouchListener(dragger);
            cards.get(i).setOnDragListener(dropper);
        }
        end.setEnabled(false);
    }
}