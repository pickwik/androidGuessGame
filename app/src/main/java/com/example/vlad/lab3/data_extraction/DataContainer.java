package com.example.vlad.lab3.data_extraction;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by vlad on 24.01.2017.
 */

public class DataContainer {
    private List<LogoAndName> data;
    public DataContainer(String neededDiv){
        data = new ArrayList<>();
        Pattern pattern = Pattern.compile("<div>(.+?)</div>");
        Matcher matcher = pattern.matcher(neededDiv);
        while (matcher.find())
        {
            data.add(new LogoAndName(matcher.group(1)));
        }
    }

    public List<LogoAndName> getData() {
        return data;
    }
}
