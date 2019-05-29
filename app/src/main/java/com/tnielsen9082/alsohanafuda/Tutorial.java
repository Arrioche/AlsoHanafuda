package com.tnielsen9082.alsohanafuda;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class Tutorial extends AppCompatActivity{
    //the list of all the cards
    ArrayList<View> cards = new ArrayList<>();
    //in case I ever use logs
    private static final String TAG = "MainActivity";
    private Intent intention;
    private TextView[] scores = new TextView[3];
    private String[] names= new String[3];
    ArrayList<Drawable> cardsDisp = new ArrayList<>();
    //the list of the descriptions that display when you click on a card
    ArrayList<String> cardDescs = new ArrayList<>();
    //the layouts where the cards taken by the players go
    private LinearLayout[] tricks=new LinearLayout[3];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.card_array);
        getWindow().getDecorView().setBackgroundColor(Color.LTGRAY);
        //lock in landscape mode
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        final View decorView = getWindow().getDecorView();
        decorView.setOnSystemUiVisibilityChangeListener
                (new View.OnSystemUiVisibilityChangeListener() {
                     @Override
                     public void onSystemUiVisibilityChange(int visibility) {
                         // Note that system bars will only be "visible" if none of the
                         // LOW_PROFILE, HIDE_NAVIGATION, or FULLSCREEN flags are set.
                         if ((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0) {
                             int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
                             decorView.setSystemUiVisibility(uiOptions);
                         }
                     }
                 }
                );
        /*ComboList comboList = new ComboList();
        String[] scoresInit = new String[3];
        scoresInit[0] = 0+"";
        scoresInit[1] = 0+"";
        scoresInit[2] = 0+"";
        names[0]="Player One";
        names[1]="Player Two";
        names[2]="Player Three";
        arraySetup();
        for (int i = 1; i < cards.size(); i++) {
            cards.get(i).setAlpha((float)0.5);
        }
        //classSetUp(scoresInit);
        //drawCards();*/
        LinearLayout row1 = findViewById(R.id.rules0layout);
        LinearLayout row2 = findViewById(R.id.rules1layout);
        LinearLayout row3 = findViewById(R.id.rules2layout);
        LinearLayout[] rows ={row1,row2,row3};
        Button next = findViewById(R.id.nextButtonTutorial);
        TutorialAdvancerButton button = new TutorialAdvancerButton();
        button.id(this);
        next.setOnClickListener(button);
    }
    public void nextStep(){

    }

    /*
    brights a20 c20 h20 k20 l20 -100
    brights a20 c20 h20 l20 -60
    seven of a05 b05 c05 d05 e05 f05 g05 i05 j05 -40
    all three of a05 b05 c05 -40
    all three of f05 i05 j05 -40
    two or three of h20 i10 c20 -20/40
    6 of a05 b05 c05 d05 e05 f05 g05 i05 j05 -30
    a20 b10 c20 -30
    a20 l20 h20 -20
    g10 j10 f10 -20
    d05 e05 g05 -20
    d10 d05 d01 d01 -10
    l20 l01 l01 l01 -10
    k20 k10 k05 k01 -10, may invalidate other combos
     */
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
    @Override
    protected void onResume() {
        super.onResume();
        View decorView = getWindow().getDecorView();
        // Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
    }
    @Override
    public void onBackPressed() {
        //do nothing
    }
}
