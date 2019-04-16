package com.tnielsen9082.alsohanafuda;

import android.content.Intent;
import android.view.View;

public class ComboButton implements View.OnClickListener {
    private MainGame activity;
    private boolean rainStatus;
    public void id(MainGame activityTag,boolean rainTag){
        activity=activityTag;
        rainStatus=rainTag;
    }
    @Override
    public void onClick(View v) {
        Intent myIntent;
        myIntent = new Intent(activity, ComboDisplay.class);
        myIntent.putExtra("rainStatus",rainStatus);
        activity.startActivity(myIntent);
    }
}
