package com.tnielsen9082.alsohanafuda;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.zip.DeflaterInputStream;

public class HideTakenTricks implements View.OnClickListener{
    private LinearLayout[] takenTricks;
    private LinearLayout buttons;
    private Button dismiss;
    private TextView scoreStarter;
    private TurnRotator turnRotator;
    private TextView[] scores= new TextView[3];
    public void id(Activity activity, LinearLayout[] takenTricksTag,TurnRotator turnRotatorTag){
        takenTricks = takenTricksTag;
        buttons = activity.findViewById(R.id.trickButtons);
        dismiss=activity.findViewById(R.id.dismissal);
        scoreStarter=activity.findViewById(R.id.scoreStarter);
        scores[0]=activity.findViewById(R.id.score1);
        scores[1]=activity.findViewById(R.id.score2);
        scores[2]=activity.findViewById(R.id.score3);
        turnRotator = turnRotatorTag;
    }
    @Override
    public void onClick(View v) {
        //shows buttons hides taken tricks
        buttons.setVisibility(View.VISIBLE);
        for (int i = 0; i < 3; i++) {
            takenTricks[i].setVisibility(View.GONE);
            scores[i].setVisibility(View.GONE);
        }
        int handNum = turnRotator.getPlayerNum();
        scores[handNum].setVisibility(View.VISIBLE);
        dismiss.setVisibility(View.GONE);
        scoreStarter.setText(R.string.your_score);
    }
}
