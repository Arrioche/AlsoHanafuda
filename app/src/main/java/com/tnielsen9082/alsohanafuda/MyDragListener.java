package com.tnielsen9082.alsohanafuda;

import android.content.ClipData;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Stack;

//this is the class that the receptacles get
public final class MyDragListener implements View.OnDragListener {
    private CharSequence clip;
    private static final String TAG = "MainActivity";
    private LinearLayout tricks;
    private LinearLayout deck;
    private TextView score;
    public void id(LinearLayout tag, LinearLayout tag2, TextView tag3){
        tricks = tag;
        deck = tag2;
        score = tag3;
    }
    @Override
    //when a drag is started this activates
    public boolean onDrag(View dropper, DragEvent event) {
        //calls getAction on the drag event
        //which tells you if it has:
            /*started
              entered the box
              left the box
              dropped
              still in the box
              or force quit
             */
        //we don't actually use this though
        //int action = event.getAction();
        //a new Java syntax appeared!
        //it looks for the action like I said
        //and then performs whichever case matches that action
        switch (event.getAction()) {
            case DragEvent.ACTION_DRAG_STARTED:
                //stop the code
                break;
            case DragEvent.ACTION_DRAG_ENTERED:
                //change the look to show it is droppable in
                break;
            case DragEvent.ACTION_DRAG_EXITED:
                //change the look back
                break;
            case DragEvent.ACTION_DROP:
                int sco =Integer.parseInt(String.valueOf(score.getText()));
                // Gets the item containing the dragged data
                ClipData.Item item = event.getClipData().getItemAt(0);
                // Gets the text data from the item.
                clip = item.getText();
                clip=clip.toString();
                View dragger = (View) event.getLocalState();
                //get the layouts that each card comes from
                ViewGroup owner = (ViewGroup) dragger.getParent();
                LinearLayout container = (LinearLayout)dropper.getParent();

                //get the ids of each card
                //which are hacked around via the ContentView
                //in a move that I am sure is programming crime
                //but it worked fine for solitaire, so
                CharSequence dragID = dragger.getContentDescription();
                CharSequence dropID = dropper.getContentDescription();
                Character dragMonth = dragID.charAt(0);
                Character dropMonth = dropID.charAt(0);
                int dragPoints = Integer.parseInt(String.valueOf(dragID.subSequence(1,3)));
                int dropPoints = Integer.parseInt(String.valueOf(dropID.subSequence(1,3)));

                //if you are not dropping in your own container
                //and the suits are the same
                if(container==deck&&dropMonth==dragMonth) {
                    owner.removeView(dragger);
                    container.removeView(dropper);

                    //add the view
                    tricks.addView(dragger);
                    tricks.addView(dropper);
                    sco+=dropPoints;
                    sco+=dragPoints;
                    score.setText(sco+"");
                }
                else {
                    boolean match = false;
                    for (int i = 0; i < container.getChildCount(); i++) {
                        if(container.getChildAt(i).getContentDescription().charAt(0)==dragMonth){
                            match=true;
                        }
                    }
                    if(!match){
                        owner.removeView(dragger);
                        container.addView(dragger);
                    }
                }
                break;
            case DragEvent.ACTION_DRAG_ENDED:

            default:
                //stops if there is no more dragging
                break;
        }
        //this allows you to receive updates on the status of the dragged thing
        return true;
    }
}


