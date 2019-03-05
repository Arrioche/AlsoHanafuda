package com.tnielsen9082.alsohanafuda;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import org.w3c.dom.Text;

public class TurnSplitter extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //set the xml layout file
        setContentView(R.layout.turn_splitter);
        //lock it in landscape mode
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        //get the intent that started the activity
        Intent intention = getIntent();
        //extract the bundle of data that came with it
        final Bundle bundle = intention.getExtras();
        //pull all the data out of the bundle
        //the bits that aren't scoresInit are assigned to global variables
        String playerName =(String)bundle.get("nextPlayerName");
        ((TextView)findViewById(R.id.nextPlayerAnnounce)).setText(playerName+ "'s Turn");

    }
    //this disables the back button

}
