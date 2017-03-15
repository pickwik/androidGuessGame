package com.example.vlad.lab3;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.vlad.lab3.data_extraction.DataContainer;
import com.example.vlad.lab3.data_extraction.LogoAndName;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by vlad on 25.01.2017.
 */

public class QuestionCreator {
    private List<Integer> alredyUsedLogos;
    private List<LogoAndName> data;
    public QuestionCreator(DataContainer dataContainer){
        this.data = dataContainer.getData();
        this.alredyUsedLogos = new ArrayList<>();
    }
    public int getDataSize(){
        return data.size();
    }
    public Question getQuestion(int targetLogoIndex,int numberOfOptions){
        if (alredyUsedLogos.size()==data.size())return null;
        List<Integer> options = new ArrayList<>();
        options.add(targetLogoIndex);
        while (options.size()<numberOfOptions){
            options.add(getRandomExcept(options));
        }
        List<Answer> answers = new ArrayList<>();
        for (Integer optionIndex : options){
            answers.add(new Answer(data.get(optionIndex).getName(),optionIndex==targetLogoIndex));
        }
        URL url = null;
        Bitmap bmp = null;
        try {
            url = new URL("http://www.pajero.us"+data.get(targetLogoIndex).getImgUrl());
            bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Question(bmp,answers,targetLogoIndex);
    }
    public Question getNextQuestion(int numberOfOptions) {
        int targetLogoIndex = getNextRandomNotUsed();
        return getQuestion(targetLogoIndex,numberOfOptions);
    }
    private int getNextRandomNotUsed(){
        int random;
        do {
            random=ThreadLocalRandom.current().nextInt(0,data.size());
        }while (alredyUsedLogos.contains(random));
        return random;
    }
    private int getRandomExcept(List<Integer> exclusions){
        int random;
        do {
            random=ThreadLocalRandom.current().nextInt(0,data.size());
        }while (exclusions.contains(random));
        return random;
    }
}
