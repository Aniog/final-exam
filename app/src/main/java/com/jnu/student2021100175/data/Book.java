package com.jnu.student2021100175.data;

import java.io.Serializable;

public class Book implements Serializable {

    public String getTitle() {
        return title;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }

    private String title;
    private int imageResourceId;
    public Book(String title_,int imageId_) {
        this.title = title_;
        this.imageResourceId = imageId_;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDrawable(int imageResourceId) {
        this.imageResourceId = imageResourceId;
    }
}
