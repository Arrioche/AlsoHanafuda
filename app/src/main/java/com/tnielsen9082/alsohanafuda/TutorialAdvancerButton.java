package com.tnielsen9082.alsohanafuda;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableRow;

public class TutorialAdvancerButton implements View.OnClickListener {
    private LinearLayout[] rows= new LinearLayout[4];
    private LinearLayout[] boxes = new LinearLayout[3];
    private AppCompatActivity activity;
    public void id(AppCompatActivity activityTag){
        activity=activityTag;
        rows[0]=activity.findViewById(R.id.tableRow0);
        rows[1]=activity.findViewById(R.id.tableRow1);
        rows[2]=activity.findViewById(R.id.tableRow2);
        rows[3]=activity.findViewById(R.id.tableRow3);
        boxes[0]=activity.findViewById(R.id.rules0layout);
        boxes[1]=activity.findViewById(R.id.rules1layout);
        boxes[2]=activity.findViewById(R.id.rules2layout);
    }
    @Override
    public void onClick(View v) {
        for (int i = 2; i >=0; i--) {
            if(boxes[i].getVisibility()==View.VISIBLE){
                boxes[i].setVisibility(View.GONE);
                if(i+1<3){
                    boxes[i+1].setVisibility(View.VISIBLE);
                }
                else{
                    activity.setContentView(R.layout.tutorial_game);
                    ((Tutorial)activity).nextStep();
                }
                if(i==1){
                    for (int j = 0; j < 4; j++) {
                        for (int k = 0; k < rows[j].getChildCount(); k++) {
                            if(!rows[j].getChildAt(k).getContentDescription().equals("h20")){
                                rows[j].getChildAt(k).setAlpha((float)0.3);
                            }
                        }
                    }
                }
            }
        }
    }
}
