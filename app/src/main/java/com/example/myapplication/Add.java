package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Add extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        Button buttonYes=findViewById(R.id.button1);
        Button buttonNo=findViewById(R.id.button2);
        buttonYes.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent yesIntent = new Intent();
                TextView textView_title = findViewById(R.id.editTextTextPassword1);
                TextView textView_author = findViewById(R.id.editTextTextPassword2);
                TextView textView_translator = findViewById(R.id.editTextTextPassword3);
                TextView textView_publisher = findViewById(R.id.editTextTextPassword4);
                TextView textView_year = findViewById(R.id.editTextTextPassword5);
                TextView textView_month = findViewById(R.id.editTextTextPassword6);
                TextView textView_isbn = findViewById(R.id.editTextTextPassword7);
                ImageView imageView_png = findViewById(R.id.imageView);
                //intent返回数据给主acitivity
                yesIntent.putExtra("button",3);
                yesIntent.putExtra("title",textView_title.getText().toString());
                yesIntent.putExtra("author",textView_author.getText().toString());
                yesIntent.putExtra("translator",textView_translator.getText().toString());
                yesIntent.putExtra("publisher",textView_publisher.getText().toString());
                yesIntent.putExtra("year",textView_year.getText().toString());
                yesIntent.putExtra("month",textView_month.getText().toString());
                yesIntent.putExtra("isbn",textView_isbn.getText().toString());
                yesIntent.putExtra("pngId",R.drawable.ic_launcher_foreground);
                imageView_png.setImageResource(R.drawable.ic_launcher_foreground);
                Toast.makeText(Add.this,"新建成功", Toast.LENGTH_SHORT).show();
                yesIntent.setClass(Add.this,MainActivity.class);
                startActivity(yesIntent);


            }
        });

        buttonNo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                setContentView(R.layout.activity_main);
                Add.this.finish();
            }
        });



    }
}