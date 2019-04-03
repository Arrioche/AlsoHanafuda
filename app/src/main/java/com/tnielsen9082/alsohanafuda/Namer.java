package com.tnielsen9082.alsohanafuda;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;

public class Namer extends AppCompatActivity {
    String nameOne;
    String nameTwo;
    String nameThree;
    boolean rain = false;
    private Handler mHandler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.player_names);
        //and the color
        getWindow().getDecorView().setBackgroundColor(Color.WHITE);
        //lock in landscape mode
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        Button done = findViewById(R.id.playersNamesDone);
        done.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                (findViewById(R.id.loadingLayout)).setVisibility(View.VISIBLE);
                mHandler.postDelayed(new Runnable() {
                    public void run() {
                        //starts the cardInitializer
                        Intent myIntent = new Intent(Namer.this, MainGame.class);
                        //you can put data in the intent
                        myIntent.putExtra("scoreOne",0);
                        myIntent.putExtra("scoreTwo",0);
                        myIntent.putExtra("scoreThree",0);
                        if(nameOne.length()!=0){
                            myIntent.putExtra("pOne",nameOne);
                        }
                        else{
                            myIntent.putExtra("pOne","Player One");
                        }
                        if(nameTwo.length()!=0){
                            myIntent.putExtra("pTwo",nameTwo);
                        }
                        else{
                            myIntent.putExtra("pTwo","Player Two");
                        }
                        if(nameThree.length()!=0){
                            myIntent.putExtra("pThree",nameThree);
                        }
                        else{
                            myIntent.putExtra("pThree","Player Three");
                        }
                        myIntent.putExtra("rainStatus",rain);
                        myIntent.putExtra("turnCounter",0);
                        Namer.this.startActivity(myIntent);
                        Namer.this.finish();
                    }
                }, 20);
            }
        });
        EditText playerOne = findViewById(R.id.playerOneName);
        playerOne.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //do nothing
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //do nothing
            }

            @Override
            public void afterTextChanged(Editable s) {
                nameOne=s+"";
            }
        });
        EditText playerTwo = findViewById(R.id.playerTwoName);
        playerTwo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //do nothing
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //do nothing
            }

            @Override
            public void afterTextChanged(Editable s) {
                nameTwo=s+"";
            }
        });
        EditText playerThree = findViewById(R.id.playerThreeName);
        playerThree.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //do nothing
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //do nothing
            }

            @Override
            public void afterTextChanged(Editable s) {
                nameThree=s+"";
            }
        });
        Switch toggle = findViewById(R.id.rainSwitch);
        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    rain =true;
                } else {
                    rain = false;
                }
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