package com.example.vlad.lab3;

import android.view.View;
import android.widget.TextView;

/**
 * Created by vlad on 25.01.2017.
 */

public class TextViewClickListener implements View.OnClickListener {
    private quiz activity;
    public TextViewClickListener(quiz activity){
        this.activity = activity;
    }
    @Override
    public void onClick(View v) {
        activity.nextStep();
        TextView textView = (TextView) v;
        textView.setText(R.string.Welcome);
        v.setOnClickListener(null);
    }
}
