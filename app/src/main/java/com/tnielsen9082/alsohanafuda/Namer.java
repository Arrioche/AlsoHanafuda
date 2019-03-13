package com.tnielsen9082.alsohanafuda;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.Layout;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.ToggleButton;

public class Namer extends AppCompatActivity {
    String nameOne="Player One";
    String nameTwo="Player Two";
    String nameThree="Player Three";
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
                        Intent myIntent = new Intent(Namer.this, CardInitializer.class);
                        //you can put data in the intent
                        myIntent.putExtra("scoreOne",0);
                        myIntent.putExtra("scoreTwo",0);
                        myIntent.putExtra("scoreThree",0);
                        myIntent.putExtra("pOne",nameOne);
                        myIntent.putExtra("pTwo",nameTwo);
                        myIntent.putExtra("pThree",nameThree);
                        myIntent.putExtra("rainStatus",rain);
                        myIntent.putExtra("turnCounter",0);
                        Namer.this.startActivity(myIntent);
                        Namer.this.finish();
                    }
                }, 10);
            }
        });
        EditText playerOne = findViewById(R.id.playerOneName);
        playerOne.setText("Player One");
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
        playerTwo.setText("Player Two");
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
        playerThree.setText("Player Three");
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
    public void onBackPressed() {
        //do nothing
    }
}