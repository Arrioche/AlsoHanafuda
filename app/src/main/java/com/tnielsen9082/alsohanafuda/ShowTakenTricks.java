package com.tnielsen9082.alsohanafuda;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;


public class ShowTakenTricks implements View.OnClickListener{
    private LinearLayout[] takenTricks;
    private LinearLayout buttons;
    private Button dismiss;
    private int player;
    private TextView[] scores= new TextView[3];
    private TurnEnder turnEnder;
    private TextView scoreStarter;
    private String[] names;
    ShowTakenTricks(Activity activity,LinearLayout[] takenTricksTag,int playerTag, TurnEnder turnEnderTag, String[] namesTag){
        buttons = activity.findViewById(R.id.trickButtons);
        takenTricks = takenTricksTag;
        dismiss=activity.findViewById(R.id.dismissal);
        player= playerTag;
        turnEnder=turnEnderTag;
        scores[0]=activity.findViewById(R.id.score1);
        scores[1]=activity.findViewById(R.id.score2);
        scores[2]=activity.findViewById(R.id.score3);
        scoreStarter=activity.findViewById(R.id.scoreStarter);
        names=namesTag;
    }
    @Override
    public void onClick(View v) {
        //hides buttons shows taken tricks
        buttons.setVisibility(View.GONE);
        //to keep track of which player is up
        int handNum = turnEnder.getHandNum();
        takenTricks[(handNum+player+2)%3].setVisibility(View.VISIBLE);
        for (int i = 0; i < 3; i++) {
            scores[i].setVisibility(View.INVISIBLE);
        }
        scores[(handNum+player+2)%3].setVisibility(View.VISIBLE);
        dismiss.setVisibility(View.VISIBLE);
        if((handNum+player)%3!=handNum){
            scoreStarter.setText(names[(handNum+player+2)%3]+"'s Score:");
        }
    }
}
