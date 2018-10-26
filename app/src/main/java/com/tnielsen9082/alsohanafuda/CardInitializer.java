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
    private String[] scoresInit =new String[3];
    private TextView[] scores = new TextView[3];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //we definitely want to change the default contentView later
        setContentView(R.layout.activity_card_screen);
        //and the color
        getWindow().getDecorView().setBackgroundColor(Color.LTGRAY);
        //lock in landscape mode
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        //gets the intent that started the activity
        intention=getIntent();
        Bundle bundle = intention.getExtras();
        if(bundle!=null) {
            scoresInit[0] = bundle.get("scoreOne") + "";
            scoresInit[1] = bundle.get("scoreTwo") + "";
            scoresInit[2] = bundle.get("scoreThree") + "";
        }
        else{
            scoresInit[0] = 0+"";
            scoresInit[1] = 0+"";
            scoresInit[2] = 0+"";
        }
        for (int i = 0; i < scoresInit.length; i++) {
            if(scoresInit[i]==null){
                scoresInit[i]=0+"";
            }
        }


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
        card.id((LinearLayout)findViewById(R.id.tricks),(LinearLayout)findViewById(R.id.board),scores, hands,touch,drawButton,(ConstraintLayout) findViewById(R.id.turnSplitter), this);
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
        TurnClicker ender = new TurnClicker(card,false,this);
        end.setOnClickListener(ender);

        //make the turn splitter button
        Button turn = findViewById(R.id.nextTurn);
        TurnClicker turner = new TurnClicker(card,true,this);
        turn.setOnClickListener(turner);

    }
    @Override
    public void onBackPressed() {
        //do nothing
    }
    public void goToScore(){
        Intent myIntent = new Intent(CardInitializer.this, Scorer.class);
        //you can put data in the intent
        myIntent.putExtra("scoreOne",Integer.parseInt(String.valueOf((scores[0]).getText())));
        myIntent.putExtra("scoreTwo",Integer.parseInt(String.valueOf((scores[1]).getText())));
        myIntent.putExtra("scoreThree",Integer.parseInt(String.valueOf((scores[2]).getText())));
        CardInitializer.this.startActivity(myIntent);
        CardInitializer.this.finish();
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