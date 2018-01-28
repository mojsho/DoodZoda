package com.example.mojtaba.doodzoda.model;

public class Suggestion {
    private int drawableId;
    private String title;

    public Suggestion(int drawableId, String title) {
        this.drawableId = drawableId;
        this.title = title;
    }

    public int getDrawableId() {
        return drawableId;
    }

    public void setDrawableId(int drawableId) {
        this.drawableId = drawableId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
