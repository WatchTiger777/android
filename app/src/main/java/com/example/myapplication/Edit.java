package com.example.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class Edit extends AppCompatActivity {

    private static final int IMAGE_REQUEST_CODE = 1;
    private static final int RESIZE_REQUEST_CODE = 2;
    private boolean editpng=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        Button buttonYes=findViewById(R.id.button1);
        Button buttonNo=findViewById(R.id.button2);


        TextView textView_title = findViewById(R.id.editTextTextPassword1);
        TextView textView_author = findViewById(R.id.editTextTextPassword2);
        TextView textView_translator = findViewById(R.id.editTextTextPassword3);
        TextView textView_publisher = findViewById(R.id.editTextTextPassword4);
        TextView textView_year = findViewById(R.id.editTextTextPassword5);
        TextView textView_month = findViewById(R.id.editTextTextPassword6);
        TextView textView_isbn = findViewById(R.id.editTextTextPassword7);
        ImageView imageView_png = findViewById(R.id.imageView);

        Intent receive = this.getIntent();
        News news = new News();
        news.getmessage(receive);
        //news.pngId = R.drawable.ic_launcher_foreground;
        textView_title.setText(news.title);
        textView_author.setText(news.author);
        textView_translator.setText(news.translator);
        textView_publisher.setText(news.publisher);
        textView_year.setText(news.year);
        textView_month.setText(news.month);
        textView_isbn.setText(news.isbn);
        imageView_png.setImageResource(news.pngId);
        if(news.hasBitmap){
           imageView_png.setImageBitmap(news.png);
        }
        int nowposition = receive.getIntExtra("nowposition",0);

        //更换图片
        imageView_png.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, IMAGE_REQUEST_CODE);

            }
        });


        buttonYes.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent yesIntent = new Intent();
                //intent返回数据给主acitivity
                yesIntent.putExtra("title",textView_title.getText().toString());
                yesIntent.putExtra("author",textView_author.getText().toString());
                yesIntent.putExtra("translator",textView_translator.getText().toString());
                yesIntent.putExtra("publisher",textView_publisher.getText().toString());
                yesIntent.putExtra("year",textView_year.getText().toString());
                yesIntent.putExtra("month",textView_month.getText().toString());
                yesIntent.putExtra("isbn",textView_isbn.getText().toString());
                yesIntent.putExtra("pngId",R.drawable.ic_launcher_foreground);
                yesIntent.putExtra("nowposition",nowposition);
                if(editpng)
                {
                    yesIntent.putExtra("hasBitmap",true);
                }
                else{
                    yesIntent.putExtra("hasBitmap",news.hasBitmap);
                }



                Toast.makeText(Edit.this,"编辑成功", Toast.LENGTH_SHORT).show();
                yesIntent.setClass(Edit.this,MainActivity.class);
                Edit.this.setResult(RESULT_OK,yesIntent);
                //startActivity(yesIntent);

                Edit.this.finish();


            }
        });

        buttonNo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Edit.this.finish();
            }
        });



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        } else {
            switch (requestCode) {
                case IMAGE_REQUEST_CODE:
                    resizeImage(data.getData());
                    break;

                case RESIZE_REQUEST_CODE:
                    if (data != null) {
                        try {
                            showResizeImage(data,this.findViewById(R.id.imageView));
                            this.editpng = true;
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);

    }
    //这里增加裁剪
    public void resizeImage(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        //裁剪的大小
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        //设置返回码
        startActivityForResult(intent, RESIZE_REQUEST_CODE);
    }
    private void showResizeImage(Intent data,ImageView png) throws FileNotFoundException {
        Bundle extras = data.getExtras();
        if (extras != null) {
            Bitmap photo = extras.getParcelable("data");
            //裁剪之后设置保存图片的路径
            String fileName = getString(R.string.image);
            //压缩图片
            storeImage(photo, fileName);
            Drawable drawable = new BitmapDrawable(photo);
            png.setImageDrawable(drawable);
        }


    }
    public static void storeImage(Bitmap bitmap, String outPath) throws FileNotFoundException {
        FileOutputStream os = new FileOutputStream(outPath);
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
        //??为什么不用flush？？
    }


}