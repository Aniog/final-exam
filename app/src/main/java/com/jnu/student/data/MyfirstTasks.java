package com.jnu.student.data;

import java.io.Serializable;

public class MyfirstTasks implements Serializable {

    public String getTitle() {
        return title;
    }

    public int getScore() {
        return score;
    }

    private String title;
    private int score;
    public MyfirstTasks(String title_, int score_) {
        this.title = title_;
        this.score = score_;
    }

}
