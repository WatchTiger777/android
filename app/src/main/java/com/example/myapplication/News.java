package com.example.myapplication;

import android.content.Intent;

public class News {
    public String title;
    public String content;
    public String author;
    public String translator;
    public String publisher;
    public String year;
    public String month;
    public String isbn ;
    public int pngId;

    public void getmessage(Intent intent)
    {
        this.title = intent.getStringExtra("title");
        this.author = intent.getStringExtra("author");
        this.publisher = intent.getStringExtra("publisher");
        this.translator = intent.getStringExtra("translator");
        this.month = intent.getStringExtra("month");
        this.year = intent.getStringExtra("year");
        this.isbn = intent.getStringExtra("isbn");
        
    }
    
}
