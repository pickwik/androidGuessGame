package com.example.vlad.lab3;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.IdRes;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import java.util.List;

/**
 * Created by vlad on 25.01.2017.
 */

public class Question {
    private Bitmap bmp;
    private List<Answer> answers;
    private int targetLogoId;

    public Question(Bitmap bmp, List<Answer> answers, int targetLogoId) {
        this.bmp = bmp;
        this.answers = answers;
        this.targetLogoId = targetLogoId;
    }

    public Bitmap getBmp() {
        return bmp;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public int getTargetLogoId() {
        return targetLogoId;
    }

    public ScrollView generateAnswersLayout(Context context){
        ScrollView scrollView = new ScrollView(context);
        LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        scrollView.setLayoutParams(llp);
        RelativeLayout relativeLayout = new RelativeLayout(scrollView.getContext());
        relativeLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        scrollView.addView(relativeLayout);
        int buttonCountInCurrentLine = 0;
        int lineCount = 0;
        int buttonsInOneLine = 0;
        if (((double)answers.size())%2==0)buttonsInOneLine=2;
        else buttonsInOneLine = 3;
        @IdRes int lineID = 100;
        LinearLayout line = null;
        for (Answer answer : answers){
            if (buttonCountInCurrentLine==0) {
                line = new LinearLayout(relativeLayout.getContext());
                line.setId(lineID);
                line.setOrientation(LinearLayout.HORIZONTAL);
                RelativeLayout.LayoutParams lineRules = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                if (lineCount==0)lineRules.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                else lineRules.addRule(RelativeLayout.BELOW,lineID-1);
                line.setLayoutParams(lineRules);
                line.setGravity(Gravity.CENTER);
                relativeLayout.addView(line);
                lineID++;
                lineCount++;
            }
            Button button = new Button(relativeLayout.getContext());
            button.setText(answer.getLabel());
            button.setTag(answer.isWrite());
            button.setOnClickListener(quiz.getClickListener());
            LinearLayout.LayoutParams bllp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            button.setLayoutParams(bllp);
            line.addView(button);
            buttonCountInCurrentLine++;
            if (buttonCountInCurrentLine==buttonsInOneLine)buttonCountInCurrentLine=0;
        }
        return scrollView;
    }
}
