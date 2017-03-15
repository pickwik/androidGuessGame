package com.example.vlad.lab3;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.vlad.lab3.data_extraction.DataContainer;
import com.example.vlad.lab3.data_extraction.DataReceiver;


public class quiz extends AppCompatActivity {
    public static final String PREFS_NAME = "MyPrefsFile";
    private static ButtonClickListener clickListener;
    private QuestionCreator questionCreator;
    private ImageView imageView;
    private LinearLayout rootView;
    private TextView textView;
    private TextViewClickListener textViewClickListener;
    private ScrollView currentScrollView = null;
    private int optionsCount = 6;
    private int currentQuestionId = 0;
    private int passedQuestions = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        clickListener = new ButtonClickListener(this);
        textViewClickListener = new TextViewClickListener(this);
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        passedQuestions = settings.getInt("passedQuestions",0);
        optionsCount = settings.getInt("optionsCount",6);
        currentQuestionId = settings.getInt("passedQuestions",0);
    }

    @Override
    protected void onStop() {
        super.onStop();
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt("optionsCount",optionsCount);
        editor.putInt("currentQuestionId",currentQuestionId);
        editor.putInt("passedQuestions",passedQuestions);
        editor.apply();
    }

    @Override
    protected void onStart() {
        super.onStart();
        imageView = (ImageView) findViewById(R.id.quizImage);
        rootView = (LinearLayout) findViewById(R.id.activity_quiz);
        textView = (TextView) findViewById(R.id.textView);
        new DataReceiver().updateBase(this);
    }

    public void updateDataContainerCallback(DataContainer dataContainer) {
        this.questionCreator = new QuestionCreator(dataContainer);
        updateQuestion(questionCreator.getNextQuestion(optionsCount));
    }
    public void nextStep(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                updateQuestion(questionCreator.getNextQuestion(optionsCount));
            }
        }).start();

    }
    private void updateQuestion(final Question question){
        if (question==null||passedQuestions==questionCreator.getDataSize()) {
            textView.setText("Логотипы кончились, твой результат "+passedQuestions+" из "+questionCreator.getDataSize()+".Жми сюда чтобы начать заново");
            textView.setOnClickListener(textViewClickListener);
            return;
        }
        currentQuestionId = question.getTargetLogoId();
        updateImageView(question.getBmp());
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(currentScrollView!=null)rootView.removeView(currentScrollView);
                ScrollView newScrollView = question.generateAnswersLayout(getApplicationContext());
                rootView.addView(newScrollView);
                currentScrollView = newScrollView;
            }
        });
    }

    private void updateImageView(final Bitmap bitmap){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                imageView.setImageBitmap(bitmap);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.item1:
                optionsCount = 2;
                break;
            case R.id.item2:
                optionsCount = 3;
                break;
            case R.id.item3:
                optionsCount = 4;
                break;
            case R.id.item4:
                optionsCount = 5;
                break;
            case R.id.item5:
                optionsCount = 6;
                break;
        }
        if (currentScrollView==null)return true;
        else new Thread(new Runnable() {
            @Override
            public void run() {
                updateQuestion(questionCreator.getQuestion(currentQuestionId,optionsCount));
            }
        }).start();
        return true;
    }
    public static ButtonClickListener getClickListener(){
        return clickListener;
    }
    public void grace(){
        passedQuestions++;
        textView.setText("Правильно, ты ответил на "+passedQuestions+" из "+questionCreator.getDataSize()+". Жми сюда чтобы продолжить");
        textView.setOnClickListener(textViewClickListener);
        rootView.removeView(currentScrollView);
        currentScrollView=null;
    }
    public void wrong(){
        textView.setText("Неверно, ты ответил на "+passedQuestions+" из "+questionCreator.getDataSize()+". Жми сюда чтобы продолжить");
        textView.setOnClickListener(textViewClickListener);
        rootView.removeView(currentScrollView);
        currentScrollView=null;
    }
}
