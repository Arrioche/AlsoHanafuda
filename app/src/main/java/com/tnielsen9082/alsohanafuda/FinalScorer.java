package com.tnielsen9082.alsohanafuda;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class FinalScorer extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private String[] scoresInit =new String[3];
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
        Intent intention = getIntent();
        final Bundle bundle = intention.getExtras();
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
        Button nextRound = findViewById(R.id.restart);
        nextRound.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //starts the cardInitializer
                Intent myIntent = new Intent(FinalScorer.this, MainGame.class);
                myIntent.putExtra("scoreOne",0);
                myIntent.putExtra("scoreTwo",0);
                myIntent.putExtra("scoreThree",0);
                myIntent.putExtra("pOne",names[0]);
                myIntent.putExtra("pTwo",names[1]);
                myIntent.putExtra("pThree",names[2]);
                myIntent.putExtra("rainStatus",(boolean)bundle.get("rainStatus"));
                myIntent.putExtra("turnCounter",0);
                FinalScorer.this.startActivity(myIntent);
                FinalScorer.this.finish();
            }
        });
        Button mainMenu = findViewById(R.id.mainMenu);
        mainMenu.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //starts the cardInitializer
                Intent myIntent = new Intent(FinalScorer.this, StartScreen.class);
                FinalScorer.this.startActivity(myIntent);
                FinalScorer.this.finish();
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
