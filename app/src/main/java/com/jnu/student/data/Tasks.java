package com.jnu.student.data;

import java.io.Serializable;

public class Tasks implements Serializable {

    public String getTitle() {
        return title;
    }

    public int getScore() {
        return score;
    }

    private String title;
    private int score;
    public Tasks(String title_, int score_) {
        this.title = title_;
        this.score = score_;
    }

}
