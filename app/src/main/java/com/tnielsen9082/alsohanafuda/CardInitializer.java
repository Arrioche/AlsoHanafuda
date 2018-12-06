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

public class CardInitializer extends AppCompatActivity {
    //the list of all the cards
    ArrayList<View> cards = new ArrayList<>();
    //in case I ever use logs
    private static final String TAG = "MainActivity";
    private Intent intention;
    private TextView[] scores = new TextView[3];
    private String[] names= new String[3];
    private LinearLayout[] tricks=new LinearLayout[3];
    private boolean wipe =false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //we definitely want to change the default contentView later
        setContentView(R.layout.main_game);
        //and the color
        getWindow().getDecorView().setBackgroundColor(Color.LTGRAY);
        //lock in landscape mode
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        //gets the intent that started the activity
        intention=getIntent();
        Bundle bundle = intention.getExtras();
        String[] scoresInit = new String[3];
        if(bundle!=null) {
            scoresInit[0] = bundle.get("scoreOne") + "";
            scoresInit[1] = bundle.get("scoreTwo") + "";
            scoresInit[2] = bundle.get("scoreThree") + "";
            names[0]=bundle.get("pOne")+"";
            names[1]=bundle.get("pTwo")+"";
            names[2]=bundle.get("pThree")+"";
        }
        else{
            scoresInit[0] = 0+"";
            scoresInit[1] = 0+"";
            scoresInit[2] = 0+"";
            names[0]="Player One";
            names[1]="Player Two";
            names[2]="Player Three";
        }
        for (int i = 0; i < scoresInit.length; i++) {
            if(scoresInit[i]==null){
                scoresInit[i]=0+"";
            }
        }
        drawCards(setUp(scoresInit));
    }
    @Override
    public void onBackPressed() {
        //do nothing
    }
    //this is called from turnClicker
    public void goToScore(){
        int total = Integer.parseInt(String.valueOf((scores[0]).getText()))+
        Integer.parseInt(String.valueOf((scores[1]).getText()))+
        Integer.parseInt(String.valueOf((scores[2]).getText()));
        Intent myIntent;
        if(total!=3168) {
            myIntent = new Intent(CardInitializer.this, Scorer.class);
        }
        else{
            myIntent = new Intent(CardInitializer.this, FinalScorer.class);
        }
        //you can put data in the intent
        countUp();
        myIntent.putExtra("scoreOne",Integer.parseInt(String.valueOf((scores[0]).getText())));
        myIntent.putExtra("scoreTwo",Integer.parseInt(String.valueOf((scores[1]).getText())));
        myIntent.putExtra("scoreThree",Integer.parseInt(String.valueOf((scores[2]).getText())));
        myIntent.putExtra("pOne",names[0]);
        myIntent.putExtra("pTwo",names[1]);
        myIntent.putExtra("pThree",names[2]);
        CardInitializer.this.startActivity(myIntent);
        CardInitializer.this.finish();
    }
    public void drawCards(LinearLayout[] hand){
        LinearLayout drawPile = findViewById(R.id.drawPile);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 7; j++) {
                View card;
                card= drawPile.getChildAt((int)(Math.random()*(drawPile.getChildCount()-1)));
                //remove it from the draw pile
                drawPile.removeView(card);
                //put it in the current hand
                hand[i].addView(card);
            }
        }
        for (int i = 0; i < 6; i++) {
            View card;
            LinearLayout board = findViewById(R.id.board);
            card = drawPile.getChildAt((int) (Math.random() * (drawPile.getChildCount() - 1)));
            //remove it from the draw pile
            drawPile.removeView(card);
            //put it in the current hand
            board.addView(card);
        }
    }
    public LinearLayout[] setUp(String[] scoresInit){
        //this initializes all the cards
        //makes all the droppers and buttons and whatnot and gives them their data
        //DOES NOT draw the cards to start the game
        //returns the array of player hands
        //SO IT BEGINS
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

        //put the hands in an array
        LinearLayout[] hands ={findViewById(R.id.handOne),findViewById(R.id.handTwo),findViewById(R.id.handThree)};
        //put the tricks in an array
        tricks[0]=findViewById(R.id.tricks);
        tricks[1]=findViewById(R.id.tricksTwo);
        tricks[2]=findViewById(R.id.tricksThree);

        //initialize the touch listener
        Dropper card = new Dropper();
        //initialize the drag listener
        //and give it the touch listener
        Dragger touch = new Dragger(hands, (LinearLayout)findViewById(R.id.cardTwo), card);
        //initialize the clicker
        //initialize the clicker
        Clicker drawButton = new Clicker();
        //make an array of the scoreboards
        scores[0] = findViewById(R.id.score1);
        scores[1] = findViewById(R.id.score2);
        scores[2] = findViewById(R.id.score3);
        for (int i = 0; i < 3; i++) {
            scores[i].setText(scoresInit[i]);
        }
        //send the data to the touch listener
        card.id(tricks,(LinearLayout)findViewById(R.id.board),scores, hands,touch,drawButton,(ConstraintLayout) findViewById(R.id.turnSplitter),(LinearLayout) findViewById(R.id.cardTwo),(LinearLayout)findViewById(R.id.drawPile),names,(TextView)findViewById(R.id.playerNameMain));
        //it has to be done in this order so that the dragger and dropper can access each other

        //give all the cards touch listeners and drag listeners
        for (int i = 0; i < cards.size(); i++) {
            //nice and easy
            cards.get(i).setOnTouchListener(touch);
            cards.get(i).setOnDragListener(card);
        }
        //give the board a dropper as well
        findViewById(R.id.board).setOnDragListener(card);

        //make the draw button
        Button draw = findViewById(R.id.draw);
        drawButton.id((LinearLayout)findViewById(R.id.drawPile),hands,(Button)findViewById(R.id.draw));
        draw.setOnClickListener(drawButton);

        //make the end turn button
        Button end = findViewById(R.id.endTurn);
        TurnClicker ender = new TurnClicker(card,false,this,(LinearLayout) findViewById(R.id.cardTwo),hands,names,(TextView)findViewById(R.id.nextPlayerAnnounce),end);
        end.setOnClickListener(ender);

        //make the turn splitter button
        Button turn = findViewById(R.id.nextTurn);
        TurnClicker turner = new TurnClicker(card,true,this,(LinearLayout) findViewById(R.id.cardTwo),hands,names,(TextView)findViewById(R.id.nextPlayerAnnounce),end);
        turn.setOnClickListener(turner);
        ((TextView) findViewById(R.id.playerNameMain)).setText(names[0]);
        return hands;
    }
    public void countUp(){
        //this should add the combo points to each player's score
        ComboList combo = new ComboList();
        int[] rain = new int[3];
        ArrayList<cardImage> cardsOne = new ArrayList<>();
        for (int i = 0; i < tricks[0].getChildCount(); i++) {
            cardImage images = new cardImage(tricks[0].getChildAt(i).getContentDescription().charAt(0)+"",Integer.parseInt(String.valueOf(tricks[0].getChildAt(i).getContentDescription().subSequence(1,3))));
            cardsOne.add(images);
        }
        rain[0]=combo.checker(cardsOne,wipe);
        ArrayList<cardImage> cardsTwo = new ArrayList<>();
        for (int i = 0; i < tricks[1].getChildCount(); i++) {
            cardImage images = new cardImage(tricks[1].getChildAt(i).getContentDescription().charAt(0)+"",Integer.parseInt(String.valueOf(tricks[1].getChildAt(i).getContentDescription().subSequence(1,3))));
            cardsTwo.add(images);
        }
        rain[1]=combo.checker(cardsTwo,wipe);
        ArrayList<cardImage> cardsThree = new ArrayList<>();
        for (int i = 0; i < tricks[2].getChildCount(); i++) {
            cardImage images = new cardImage(tricks[2].getChildAt(i).getContentDescription().charAt(0)+"",Integer.parseInt(String.valueOf(tricks[2].getChildAt(i).getContentDescription().subSequence(1,3))));
            cardsThree.add(images);
        }
        rain[2]=combo.checker(cardsThree,wipe);
        boolean rainEffect = false;
        for (int i = 0; i < 3; i++) {
            if(rain[i]==-1){
                rainEffect=true;
            }
        }
        if(!rainEffect){
            scores[0].setText( Integer.parseInt(String.valueOf((scores[0]).getText()))+ combo.checker(cardsOne,false)+"");
            scores[1].setText( Integer.parseInt(String.valueOf((scores[1]).getText()))+ combo.checker(cardsTwo,false)+"");
            scores[2].setText( Integer.parseInt(String.valueOf((scores[2]).getText()))+ combo.checker(cardsThree,false)+"");
        }
    }
    //in summation:
    //this is the function that sets up the main parts of the game
    //it creates the dragger, dropper, clicker, and all the cards
    //it links those all together
    //it gives them all the data they need
    //this happens by creating the custom class
    //then calling id on it, which will take the right data
    //this is the core of the data management in this code
    //it also switches the display over to the main board
    //as the first of four methods...
    //it's an important method
}