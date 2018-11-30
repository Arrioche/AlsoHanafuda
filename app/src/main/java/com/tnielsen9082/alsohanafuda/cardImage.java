package com.tnielsen9082.alsohanafuda;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public final class cardImage {
    public String  month;
    public int score;
    public cardImage (String mon, int sco){
        month = mon;
        score =sco;
    }
    public boolean compare(String mon, int sco){
        if(mon.equals(month)&&sco==score) {
            return true;
        }
        return false;
    }
    public boolean compare(String mon){
        if(mon.equals(month)) {
            return true;
        }
        return false;
    }
    public boolean compare(int sco){
        if(sco==score) {
            return true;
        }
        return false;
    }
}
