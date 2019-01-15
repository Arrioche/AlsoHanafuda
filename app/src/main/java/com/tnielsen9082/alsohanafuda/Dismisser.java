package com.tnielsen9082.alsohanafuda;

import android.view.View;
import android.widget.Button;

public class Dismisser implements View.OnClickListener {
    @Override
    //this is what happens if you click the button
    public void onClick(View v) {
        v.setVisibility(View.GONE);
    }
}
