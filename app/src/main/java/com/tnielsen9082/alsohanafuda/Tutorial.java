package com.tnielsen9082.alsohanafuda;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class Tutorial extends AppCompatActivity {
    //the list of all the cards
    ArrayList<View> cards = new ArrayList<>();
    //in case I ever use logs
    private static final String TAG = "MainActivity";
    private Intent intention;
    private TextView[] scores = new TextView[3];
    private String[] names= new String[3];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //we definitely want to change the default contentView later
        setContentView(R.layout.tutorial_game);
        //and the color
        getWindow().getDecorView().setBackgroundColor(Color.LTGRAY);
        //lock in landscape mode
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        ComboList comboList = new ComboList();
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
        //drawCards();
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
    public void arraySetup() {
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
    public void goToScore(){
        Intent myIntent = new Intent(Tutorial.this, StartScreen.class);
        Tutorial.this.startActivity(myIntent);
        Tutorial.this.finish();
    }

}
