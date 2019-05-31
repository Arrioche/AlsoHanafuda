package com.tnielsen9082.alsohanafuda;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class NextTutorialAdvancerButton implements View.OnClickListener {
    private LinearLayout hand;
    private LinearLayout board;
    private AppCompatActivity activity;
    private TextView text1;
    private TextView text2;
    private TextView score;
    private int counter=0;
    public void id(AppCompatActivity activityTag){
        activity=activityTag;
        hand = activity.findViewById(R.id.handOne);
        board = activity.findViewById(R.id.board);
        text1 = activity.findViewById(R.id.rules3);
        text2 = activity.findViewById(R.id.rules4);
        score = activity.findViewById(R.id.score1);
    }

    @Override
    public void onClick(View v) {
        if(counter==0)
                text1.setText(R.string.tutorial_three);
                text2.setText(R.string.tutorial_four);
         if(counter==1){
                text1.setText(R.string.tutorial_five);
                ((View)text2.getParent()).setVisibility(View.GONE);
                for (int i = 0; i < board.getChildCount(); i++) {
                    if(board.getChildAt(i)!=activity.findViewById(R.id.demoCardBoard))
                    {
                        board.getChildAt(i).setAlpha((float)0.5);
                    }
                }
                for (int i = 0; i < hand.getChildCount(); i++) {
                    if(hand.getChildAt(i)!=activity.findViewById(R.id.demoCardHand))
                    {
                        hand.getChildAt(i).setAlpha((float)0.5);
                    }
                }
        }
         if(counter==2){
             ImageView card1= activity.findViewById(R.id.demoCardBoard);
             ImageView card2= activity.findViewById(R.id.demoCardHand);
             LinearLayout tricks = activity.findViewById(R.id.tricks);
             ((LinearLayout)card1.getParent()).removeView(card1);
             tricks.addView(card1);
             ((LinearLayout)card2.getParent()).removeView(card2);
             tricks.addView(card2);
             score.setText("21");
             activity.findViewById(R.id.secondCardCard).setVisibility(View.VISIBLE);
             for (int i = 0; i < board.getChildCount(); i++) {
                 board.getChildAt(i).setAlpha((float)1);
             }
         }
        counter++;
    }
}
