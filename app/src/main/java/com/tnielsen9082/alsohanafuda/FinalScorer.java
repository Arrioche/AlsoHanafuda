package com.tnielsen9082.alsohanafuda;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class FinalScorer extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private Intent intention;
    private String[] scoresInit =new String[3];
    private TextView[] scores = new TextView[3];
    private String[] names= new String[3];
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //sets the display to the start screen
        setContentView(R.layout.final_scorer);
        //and the color
        getWindow().getDecorView().setBackgroundColor(Color.CYAN);
        //locks it as landscape
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        intention=getIntent();
        Bundle bundle = intention.getExtras();
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
        ((TextView)findViewById(R.id.pOneFinalScore)).setText(names[0]);
        ((TextView)findViewById(R.id.pTwoFinalScore)).setText(names[1]);
        ((TextView)findViewById(R.id.pThreeFinalScore)).setText(names[2]);
        ((TextView)findViewById(R.id.POneFSNum)).setText(scoresInit[0]);
        ((TextView)findViewById(R.id.PTwoFSNum)).setText(scoresInit[1]);
        ((TextView)findViewById(R.id.PThreeFSNum)).setText(scoresInit[2]);
    }
    @Override
    public void onBackPressed() {
        //do nothing
    }
}