package com.tnielsen9082.alsohanafuda;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

public class SettingsMenu extends AppCompatActivity {
    private boolean rain = false;
    private int turns=12;
    private Handler mHandler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //we definitely want to change the default contentView later
        setContentView(R.layout.settings_menu);
        //and the color
        getWindow().getDecorView().setBackgroundColor(Color.LTGRAY);
        //lock in landscape mode
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        CheckBox toggle = findViewById(R.id.rainSwitch);
        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                rain=isChecked;
            }
        });
        EditText turnNumber = findViewById(R.id.turnNumberField);
        turnNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //do nothing
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //do nothing
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!s.toString().equals("")){
                    turns=Integer.parseInt(s.toString());
                }
            }
        });
        Button done = findViewById(R.id.startGame);
        done.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                (findViewById(R.id.loadingLayout)).setVisibility(View.VISIBLE);
                mHandler.postDelayed(new Runnable() {
                    public void run() {
                        //starts the cardInitializer
                        Intent myIntent = new Intent(SettingsMenu.this, MainGame.class);
                        Intent intention = getIntent();
                        final Bundle bundle = intention.getExtras();
                        myIntent.putExtras(bundle);
                        myIntent.putExtra("scoreOne",0);
                        myIntent.putExtra("scoreTwo",0);
                        myIntent.putExtra("scoreThree",0);
                        myIntent.putExtra("turnCounter",0);
                        if(turns<=0){
                            myIntent.putExtra("turnTotal",12);
                        }
                        else{
                            myIntent.putExtra("turnTotal",turns);
                        }
                        myIntent.putExtra("rainStatus",rain);
                        SettingsMenu.this.startActivity(myIntent);
                        SettingsMenu.this.finish();
                    }
                }, 100);
            }
        });
    }
    @Override
    public void onBackPressed() {
        //do nothing
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
