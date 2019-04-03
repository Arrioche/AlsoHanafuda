package com.tnielsen9082.alsohanafuda;

import android.content.Intent;
import android.view.View;

public class ComboButton implements View.OnClickListener {
    private MainGame activity;
    public void id(MainGame activityTag){
        activity=activityTag;
    }
    @Override
    public void onClick(View v) {
        Intent myIntent;
        myIntent = new Intent(activity, ComboDisplay.class);
        activity.startActivity(myIntent);
    }
}
