package com.tnielsen9082.alsohanafuda;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Scorer extends AppCompatActivity {
    private Intent intention;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //we definitely want to change the default contentView later
        setContentView(R.layout.scorer_layout);
        //set the background color
        getWindow().getDecorView().setBackgroundColor(Color.GREEN);
        //lock in landscape mode
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        intention=getIntent();
        final Bundle bundle = intention.getExtras();
        TextView[] scores= {findViewById(R.id.scoreDisplay1),findViewById(R.id.scoreDisplay2),findViewById(R.id.scoreDisplay3)};
        if(bundle!=null) {
            scores[0].setText(bundle.get("scoreOne") + "");
            scores[1].setText(bundle.get("scoreTwo") + "");
            scores[2].setText(bundle.get("scoreThree") + "");
        }
        else{
            scores[0].setText(0+"");
            scores[1].setText(0+"");
            scores[2].setText(0+"");
        }
        Button nextRound = findViewById(R.id.nextRound);
        nextRound.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //starts the cardInitializer
                Intent myIntent = new Intent(Scorer.this, CardInitializer.class);
                //you can put data in the intent
                if (bundle != null){
                    myIntent.putExtra("scoreOne", bundle.get("scoreOne") + "");
                    myIntent.putExtra("scoreTwo", bundle.get("scoreTwo") + "");
                    myIntent.putExtra("scoreThree", bundle.get("scoreThree") + "");
                }
                else{
                    myIntent.putExtra("scoreOne",0);
                    myIntent.putExtra("scoreTwo",0);
                    myIntent.putExtra("scoreThree",0);
                }
                Scorer.this.startActivity(myIntent);
                Scorer.this.finish();
            }
        });
    }
    @Override
    public void onBackPressed() {
        //do nothing
    }
}
