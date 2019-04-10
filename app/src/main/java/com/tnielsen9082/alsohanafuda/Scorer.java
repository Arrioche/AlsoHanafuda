package com.tnielsen9082.alsohanafuda;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Scorer extends AppCompatActivity {
    private Intent intention;
    private String[] names = new String[3];
    private String[] scores= new String[3];
    private String[] bonus = new String[3];
    private Handler mHandler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scorer_layout);
        //set the background color
        getWindow().getDecorView().setBackgroundColor(Color.GREEN);
        //lock in landscape mode
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        intention=getIntent();
        final Bundle bundle = intention.getExtras();
        TextView[] scoresDisplay= {findViewById(R.id.scoreDisplay1),findViewById(R.id.scoreDisplay2),findViewById(R.id.scoreDisplay3)};
        TextView[] namesDisplay ={findViewById(R.id.titleOne),findViewById(R.id.titleTwo),findViewById(R.id.titleThree)};
        TextView[] bonusDisplay ={findViewById(R.id.bonusPoints1),findViewById(R.id.bonusPoints2),findViewById(R.id.bonusPoints3)};
        if(bundle!=null) {
            scores[0]=(bundle.get("scoreOne") + "");
            scores[1]=(bundle.get("scoreTwo") + "");
            scores[2]=(bundle.get("scoreThree") + "");
            names[0]=bundle.get("pOne")+"";
            names[1]=bundle.get("pTwo")+"";
            names[2]=bundle.get("pThree")+"";
            bonus[0]=(bundle.get("bonusOne") + "");
            bonus[1]=(bundle.get("bonusTwo") + "");
            bonus[2]=(bundle.get("bonusThree") + "");
        }
        else{
            for (int i = 0; i < 3; i++) {
                scores[i]=0+"";
                bonus[0]=0+"";
            }
            names[0]="Player One";
            names[1]="Player Two";
            names[2]="Player Three";
        }
        for (int i = 0; i < 3; i++) {
            scoresDisplay[i].setText("Total Points: "+scores[i]);
            namesDisplay[i].setText(names[i]);
            bonusDisplay[i].setText("Combo Points: "+bonus[i]);
        }
        Button nextRound = findViewById(R.id.nextRound);
        nextRound.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                (findViewById(R.id.loadingLayout)).setVisibility(View.VISIBLE);
                mHandler.postDelayed(new Runnable() {
                    public void run() {
                        //starts the cardInitializer
                        Intent myIntent = new Intent(Scorer.this, MainGame.class);
                        //you can put data in the intent
                        myIntent.putExtra("scoreOne", scores[0]);
                        myIntent.putExtra("scoreTwo", scores[1]);
                        myIntent.putExtra("scoreThree", scores[2]);
                        myIntent.putExtra("pOne",names[0]);
                        myIntent.putExtra("pTwo",names[1]);
                        myIntent.putExtra("pThree",names[2]);
                        myIntent.putExtra("rainStatus",(boolean)bundle.get("rainStatus"));
                        myIntent.putExtra("turnCounter",(int)bundle.get("turnCounter")+1);
                        Scorer.this.startActivity(myIntent);
                        Scorer.this.finish();
                    }
                }, 20);
            }
        });
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
