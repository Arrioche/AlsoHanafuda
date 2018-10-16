package com.tnielsen9082.alsohanafuda;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import static android.support.v4.content.ContextCompat.startActivity;

public class StartScreen extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //sets the display to the start screen
        setContentView(R.layout.start_scree);
        //locks it as landscapre
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        //creates the start button
        Button draw = findViewById(R.id.start);
        draw.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //starts the cardInitializer
                Intent myIntent = new Intent(StartScreen.this, CardInitializer.class);
                StartScreen.this.startActivity(myIntent);
            }
        });
        //THERE IS NO CODE FOR THE RULES BUTTON YET
    }
    //in summation:
    //this is the activity that you first see when you open the game
    //it has the title and the start button and the button to open the rules
    //the start button takes you to the cardInitializer
    //as the first of four methods...
    //it's an important method
}
