package com.example.vlad.lab3.data_extraction;

/**
 * Created by vlad on 24.01.2017.
 */

public class LogoAndName {
    private String imgUrl;
    private String name;
    private String imgStartKey = "<img src=";
    private String labelStartKey = "<br>";
    public LogoAndName(String preProcessedString){
        imgUrl = preProcessedString.substring(preProcessedString.indexOf(imgStartKey)+imgStartKey.length()+1,preProcessedString.indexOf(" alt=")-1);
        name = preProcessedString.substring(preProcessedString.indexOf(labelStartKey)+labelStartKey.length());
    }

    public String getName() {
        return name;
    }

    public String getImgUrl() {
        return imgUrl;
    }
}
