package com.example.vlad.lab3;

import android.view.View;

/**
 * Created by vlad on 25.01.2017.
 */

public class ButtonClickListener implements View.OnClickListener {
    private quiz runningActivity;
    public ButtonClickListener(quiz activity){
        this.runningActivity = activity;
    }
    @Override
    public void onClick(View v) {
        if(Boolean.parseBoolean(v.getTag().toString()))runningActivity.grace();
        else runningActivity.wrong();
    }
}
