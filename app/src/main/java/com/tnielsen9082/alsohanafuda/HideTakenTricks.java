package com.tnielsen9082.alsohanafuda;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.zip.DeflaterInputStream;

public class HideTakenTricks implements View.OnClickListener{
    private LinearLayout[] takenTricks;
    private LinearLayout buttons;
    private Button dismiss;
    public void id(Activity activity, LinearLayout[] takenTricksTag){
        takenTricks = takenTricksTag;
        buttons = activity.findViewById(R.id.trickButtons);
        dismiss=activity.findViewById(R.id.dismissal);
    }
    @Override
    public void onClick(View v) {
        //shows buttons hides taken tricks
        buttons.setVisibility(View.VISIBLE);
        for (int i = 0; i < 3; i++) {
            takenTricks[i].setVisibility(View.GONE);
        }
        dismiss.setVisibility(View.GONE);
    }
}
