package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.TextView;

public class Edit extends AppCompatActivity {
    Button btnyes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        btnyes = (Button)findViewById(R.id.button);
        TextView textView_title = findViewById(R.id.editTextTextPassword1);
        TextView textView_author = findViewById(R.id.editTextTextPassword2);
        TextView textView_translator = findViewById(R.id.editTextTextPassword3);
        TextView textView_publisher = findViewById(R.id.editTextTextPassword4);
        TextView textView_year = findViewById(R.id.editTextTextPassword5);
        TextView textView_month = findViewById(R.id.editTextTextPassword6);
        TextView textView_isbn = findViewById(R.id.editTextTextPassword7);

        Intent backIntent = new Intent();
        backIntent.putExtra("title",textView_title.getText().toString());
        backIntent.putExtra("Translator",textView_translator.getText().toString());
        backIntent.putExtra("Publisher",textView_publisher.getText().toString());
        backIntent.putExtra("Year",textView_year.getText().toString());
        backIntent.putExtra("Month",textView_month.getText().toString());
        backIntent.putExtra("ISBN",textView_isbn.getText().toString());
        setResult(RESULT_OK,backIntent);
        Edit.this.finish();

        setContentView(R.layout.activity_edit);
    }

    private TextWatcher BTNameOutSizeListener = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
        };

        @Override
        //捕获返回按钮事件方法
        public void onBackPressed(){
            Intent backIntent = new Intent();
            backIntent.putExtra("edit_position",editPosition);
            backIntent.putExtra("title",R.id.editTextTextPassword1.getText().toString());
            backIntent.putExtra("Translator",R.id.editTextTextPassword2.getText().toString());
            backIntent.putExtra("Publisher",R.id.editTextTextPassword3.getText().toString());
            backIntent.putExtra("Year",R.id.editTextTextPassword4.getText().toString());
            backIntent.putExtra("Month",R.id.editTextTextPassword5.getText().toString());
            backIntent.putExtra("ISBN",R.id.editTextTextPassword6.getText().toString());
            setResult(RESULT_OK,backIntent);
            Edit.this.finish();
        }


    }