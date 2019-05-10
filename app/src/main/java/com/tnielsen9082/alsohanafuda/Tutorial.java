package com.tnielsen9082.alsohanafuda;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class Tutorial extends MainGame{
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

}
