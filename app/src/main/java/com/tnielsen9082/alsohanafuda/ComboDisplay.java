package com.tnielsen9082.alsohanafuda;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ComboDisplay extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //sets the display to the start screen
        setContentView(R.layout.combo_list_display);
        //locks it as landscape
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        getWindow().getDecorView().setBackgroundColor(Color.BLACK);
        Button backs;
        backs = findViewById(R.id.comboBack);
        backs.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ComboDisplay.this.finish();
            }
        });
        Intent intention = getIntent();
        final Bundle bundle = intention.getExtras();
        TextView rainText=findViewById(R.id.straightRainText);
        if((boolean)bundle.get("rainStatus")){
            rainText.setText(R.string.straight_rain_alt);
        }
        else{
            rainText.setText(R.string.straight_rain);
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        View decorView = getWindow().getDecorView();
        // Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
    }
}
