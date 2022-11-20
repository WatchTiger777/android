package com.example.myapplication;

import android.content.Intent;
import android.widget.Toast;

public class News {
    public String title;
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
        this.pngId  = intent.getIntExtra("pngId",R.drawable.ic_launcher_background);
        
    }

    public Intent sendmessage(News temp)
    {
        Intent go = new Intent();
        go.putExtra("button",3);
        go.putExtra("title",temp.title);
        go.putExtra("author",temp.author);
        go.putExtra("translator",temp.translator);
        go.putExtra("publisher",temp.publisher);
        go.putExtra("year",temp.year);
        go.putExtra("month",temp.month);
        go.putExtra("isbn",temp.isbn);
        go.putExtra("pngId",temp.pngId);
        //Toast.makeText(Add.this,"新建成功", Toast.LENGTH_SHORT).show();
        //go.setClass(Add.this,MainActivity.class);
        //temp.getmessage(go);
        return go;
    }

    
}
