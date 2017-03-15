package com.example.vlad.lab3;

/**
 * Created by vlad on 25.01.2017.
 */

public class Answer {
    private String label;
    private boolean isWrite;

    public Answer(String label, boolean isWrite) {
        this.label = label;
        this.isWrite = isWrite;
    }

    public String getLabel() {
        return label;
    }

    public boolean isWrite() {
        return isWrite;
    }
}
