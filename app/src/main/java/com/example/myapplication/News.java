package com.example.myapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.hardware.biometrics.BiometricManager;
import android.widget.Toast;

import java.io.Serializable;

public class News implements Serializable {
    public String title;
    public String author;
    public String translator;
    public String publisher;
    public String year;
    public String month;
    public String isbn ;
    public int pngId;
    public byte[] png;
    public boolean hasBitmap;


    /*
        public void setAll(News temp) {

            this.title = temp.title;
            this.author = temp.author;
            this.translator = temp.translator;
            this.publisher = temp.publisher;
            this.year = temp.year;
            this.month = temp.month;
            this.isbn = temp.isbn;
            this.pngId = temp.pngId;
        }

         */
    public void getmessage(Intent intent)
    {
        this.title = intent.getStringExtra("title");
        this.author = intent.getStringExtra("author");
        this.publisher = intent.getStringExtra("publisher");
        this.translator = intent.getStringExtra("translator");
        this.month = intent.getStringExtra("month");
        this.year = intent.getStringExtra("year");
        this.isbn = intent.getStringExtra("isbn");
        this.pngId  = intent.getIntExtra("pngId",R.drawable.ic_book_foreground);

        this.png = intent.getByteArrayExtra("png");
        this.hasBitmap = intent.getBooleanExtra("hasBitmap",false);
    }

    public Intent sendmessage(News temp,int nowposition)
    {
        Intent go = new Intent();
        go.putExtra("title",temp.title);
        go.putExtra("author",temp.author);
        go.putExtra("translator",temp.translator);
        go.putExtra("publisher",temp.publisher);
        go.putExtra("year",temp.year);
        go.putExtra("month",temp.month);
        go.putExtra("isbn",temp.isbn);
        go.putExtra("pngId",temp.pngId);
        go.putExtra("nowposition",nowposition);
        go.putExtra("hasBitmap",temp.hasBitmap);
        if(temp.hasBitmap) {
            go.putExtra("png", temp.png);
        }

        //Toast.makeText(Add.this,"新建成功", Toast.LENGTH_SHORT).show();
        //go.setClass(Add.this,MainActivity.class);
        //temp.getmessage(go);
        return go;
    }

    
}
