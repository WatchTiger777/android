package com.example.myapplication;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView mRecyclerView;
    MyAdapter mMyAdapter ;
    List<News> mNewsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = findViewById(R.id.recyclerview);
        registerForContextMenu(mRecyclerView);
        mRecyclerView.setLongClickable(true);
        // 构造一些数据
        for (int i = 0; i < 50; i++) {
            News news = new News();
            news.title = "标题" + i;
            news.content = "内容" + i;
            news.pngId= R.drawable.ic_launcher_foreground;
            mNewsList.add(news);
        }
        mMyAdapter = new MyAdapter();
        mRecyclerView.setAdapter(mMyAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        mRecyclerView.setLayoutManager(layoutManager);
    }


    public void CreateMenu(Menu menu)
    {
        int groupID = 0;
        int order = 0;
        int[] itemID = {1,2};

        for(int i=0;i<itemID.length;i++)
        {
            switch(itemID[i])
            {
                case 1:
                    menu.add(groupID, itemID[i], order, "编辑");
                    break;
                case 2:
                    menu.add(groupID, itemID[i], order, "删除");
                    break;
                default:
                    break;
            }
        }
    }

    class MyAdapter extends RecyclerView.Adapter<MyViewHoder> {

        @NonNull
        @Override
        public MyViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = View.inflate(MainActivity.this, R.layout.book_list, null);
            MyViewHoder myViewHoder = new MyViewHoder(view);
            return myViewHoder;
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHoder holder, int position) {
            News news = mNewsList.get(position);
            holder.mTitleTv.setText(news.title);
            holder.mTitleContent.setText(news.content);
            holder.mTitlePng.setImageResource(news.pngId);
        }

        @Override
        public int getItemCount() {
            return mNewsList.size();
        }
    }

    class MyViewHoder extends RecyclerView.ViewHolder {
        TextView mTitleTv;
        TextView mTitleContent;
        ImageView mTitlePng;


        public MyViewHoder(@NonNull View itemView) {
            super(itemView);
            mTitleTv = itemView.findViewById(R.id.textView);
            mTitleContent = itemView.findViewById(R.id.textView2);
            mTitlePng = itemView.findViewById(R.id.imageView);
        }
    }
}

