package com.example.vlad.lab3.data_extraction;

import com.example.vlad.lab3.quiz;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Created by vlad on 24.01.2017.
 */

public class DataReceiver {
    private quiz runningActivity;
    public void updateBase(quiz activity){
        runningActivity = activity;
        new Thread() {
            public void run() {
                URL url = null;
                BufferedReader in = null;
                try {
                    url = new URL("http://www.pajero.us/tech/logo.shtml");

                    in = new BufferedReader(
                            new InputStreamReader(
                                    url.openStream(),"windows-1251"));

                    String inputLine;
                    StringBuilder builder = new StringBuilder();
                    while ((inputLine = in.readLine()) != null)
                        builder.append(inputLine);
                    String pageContent = builder.toString().replace("\"","'");
                    String mydata = pageContent.substring(pageContent.indexOf("<div class='article logos'"),pageContent.indexOf("<!--class=article end-->"));
                    doCallback(mydata);
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (in != null) {
                        try {
                            in.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }.start();
    }
    private void doCallback(String mydata){
            runningActivity.updateDataContainerCallback(new DataContainer(mydata));

    }
}
