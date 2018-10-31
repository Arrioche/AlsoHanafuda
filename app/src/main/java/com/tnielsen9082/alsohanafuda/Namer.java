package com.tnielsen9082.alsohanafuda;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class Namer extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.player_names);
        //and the color
        getWindow().getDecorView().setBackgroundColor(Color.BLUE);
        //lock in landscape mode
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        Button done = findViewById(R.id.playersNamesDone);
        done.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //starts the cardInitializer
                Intent myIntent = new Intent(Namer.this, CardInitializer.class);
                //you can put data in the intent
                myIntent.putExtra("scoreOne",0);
                myIntent.putExtra("scoreTwo",0);
                myIntent.putExtra("scoreThree",0);
                Namer.this.startActivity(myIntent);
                Namer.this.finish();
            }
        });


    }
    @Override
    public void onBackPressed() {
        //do nothing
    }
}