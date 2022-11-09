package com.example.myapplication;

public class News {
    public String title;
    public String content;
    public int pngId;
    public int position;

    public int getPosition() {
        return position;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getPngId() {
        return pngId;
    }

    public void setPngId(int pngId) {
        this.pngId = pngId;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
