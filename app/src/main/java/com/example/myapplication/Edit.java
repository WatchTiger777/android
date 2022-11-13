package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Edit extends AppCompatActivity {
    Button btnyes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
    }

    public class ButtonActivity extends AppCompatActivity implements View.OnClickListener {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_edit);
            Button button=findViewById(R.id.button1);
            button.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.button1:
                    Intent yesIntent = new Intent();
                    btnyes = (Button)findViewById(R.id.button1);
                    TextView textView_title = findViewById(R.id.editTextTextPassword1);
                    TextView textView_author = findViewById(R.id.editTextTextPassword2);
                    TextView textView_translator = findViewById(R.id.editTextTextPassword3);
                    TextView textView_publisher = findViewById(R.id.editTextTextPassword4);
                    TextView textView_year = findViewById(R.id.editTextTextPassword5);
                    TextView textView_month = findViewById(R.id.editTextTextPassword6);
                    TextView textView_isbn = findViewById(R.id.editTextTextPassword7);
                    //intent返回数据给主acitivity
                    yesIntent.putExtra("title",textView_title.getText().toString());
                    yesIntent.putExtra("author",textView_author.getText().toString());
                    yesIntent.putExtra("Translator",textView_translator.getText().toString());
                    yesIntent.putExtra("Publisher",textView_publisher.getText().toString());
                    yesIntent.putExtra("Year",textView_year.getText().toString());
                    yesIntent.putExtra("Month",textView_month.getText().toString());
                    yesIntent.putExtra("ISBN",textView_isbn.getText().toString());
                    Toast.makeText(this,"按钮被点击了", Toast.LENGTH_SHORT).show();
                    setResult(RESULT_OK,yesIntent);
                    Edit.this.finish();
                default:
                    break;
            }
        }
    }


    }