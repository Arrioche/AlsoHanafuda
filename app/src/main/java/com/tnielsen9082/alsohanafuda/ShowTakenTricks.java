package com.tnielsen9082.alsohanafuda;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;


public class ShowTakenTricks implements View.OnClickListener{
    private LinearLayout[] takenTricks;
    private LinearLayout buttons;
    private Button dismiss;
    private int player;
    private TurnEnder TurnEnder;
    ShowTakenTricks(Activity activity,LinearLayout[] takenTricksTag,int playerTag, TurnEnder turnEnderTag){
        buttons = activity.findViewById(R.id.trickButtons);
        takenTricks = takenTricksTag;
        dismiss=activity.findViewById(R.id.dismissal);
        player= playerTag;
        TurnEnder=turnEnderTag;

    }
    @Override
    public void onClick(View v) {
        //hides buttons shows taken tricks
        buttons.setVisibility(View.GONE);
        //to keep track of which player is up
        int handNum = TurnEnder.getHandNum();
        takenTricks[(handNum+player+2)%3].setVisibility(View.VISIBLE);
        dismiss.setVisibility(View.VISIBLE);
    }
}
