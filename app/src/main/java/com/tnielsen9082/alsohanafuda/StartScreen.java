package com.tnielsen9082.alsohanafuda;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import static android.support.v4.content.ContextCompat.startActivity;

public class StartScreen extends AppCompatActivity {
    private Handler mHandler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //sets the display to the start screen
        setContentView(R.layout.start_scree);
        //locks it as landscape
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        //creates the start button
        Button draw = findViewById(R.id.start);
        draw.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                (findViewById(R.id.loadingLayoutStart)).setVisibility(View.VISIBLE);
                mHandler.postDelayed(new Runnable() {
                    public void run() {
                        Intent myIntent = new Intent(StartScreen.this, Namer.class);
                        StartScreen.this.startActivity(myIntent);
                        StartScreen.this.finish();
                    }
                }, 10);
            }
        });
        Button rules = findViewById(R.id.rules);
        rules.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                (findViewById(R.id.loadingLayoutStart)).setVisibility(View.VISIBLE);
                mHandler.postDelayed(new Runnable() {
                    public void run() {
                        Intent myIntent = new Intent(StartScreen.this, Tutorial.class);
                        myIntent.putExtra("scoreOne",0);
                        myIntent.putExtra("scoreTwo",0);
                        myIntent.putExtra("scoreThree",0);
                        myIntent.putExtra("pOne","One");
                        myIntent.putExtra("pTwo","Two");
                        myIntent.putExtra("pThree","Three");
                        myIntent.putExtra("rainStatus",false);
                        myIntent.putExtra("turnCounter",0);
                        StartScreen.this.startActivity(myIntent);
                        StartScreen.this.finish();
                    }
                }, 10);
            }
        });
        Button cardGallery = findViewById(R.id.cardIntro);
        cardGallery.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                (findViewById(R.id.loadingLayoutStart)).setVisibility(View.VISIBLE);
                mHandler.postDelayed(new Runnable() {
                    public void run() {
                        Intent myIntent = new Intent(StartScreen.this, Gallery.class);
                        StartScreen.this.startActivity(myIntent);
                        StartScreen.this.finish();
                    }
                }, 10);
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
    //in summation:
    //this is the activity that you first see when you open the game
    //it has the title and the start button and the button to open the rules
    //the start button takes you to the cardInitializer
    //as the first of four methods...
    //it's an important method
}
