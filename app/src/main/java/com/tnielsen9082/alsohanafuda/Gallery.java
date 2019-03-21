package com.tnielsen9082.alsohanafuda;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class Gallery extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //sets the display to the start screen
        setContentView(R.layout.card_gallery);
        getWindow().getDecorView().setBackgroundColor(Color.LTGRAY);
        //locks it as landscape
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        Button backs;
        backs = findViewById(R.id.galleryBack);
        backs.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(Gallery.this, StartScreen.class);
                Gallery.this.startActivity(i);
                Gallery.this.finish();
            }
        });
    }
    @Override
    public void onBackPressed() {
        //do nothing
    }
}
