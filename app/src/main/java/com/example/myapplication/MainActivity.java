package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView mRecyclerView;
    MyAdapter mMyAdapter ;
    //静态类变量
    static List<News> mNewsList = new ArrayList<>();

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case 1:
                Intent gotoEdit = new Intent();
                gotoEdit.setClass(MainActivity.this,Edit.class);
                startActivity(gotoEdit);

                break;
                //
            //目前还只是清空
            case 2:
                mNewsList=new ArrayList<News>();
                save();
                //reload();
                finish();
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
        return super.onContextItemSelected(item);
    }

    public void CreateMenu(Menu menu)
    {
        int groupID = 0;
        int order = 0;
        int[] itemID = {1,2,3};
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
                case 3:
                    menu.add(groupID, itemID[i], order, "清空");
                default:
                    break;
            }
        }
    }

    //序列化存储书本数据
    protected void save()
    {
        try{
            Context cont = this.getApplicationContext();
            File name = cont.getFilesDir();
            String fileName = getString(R.string.bookData);
            FileOutputStream fos= new FileOutputStream(fileName,true);
            ObjectOutputStream oos= new ObjectOutputStream(fos);
            oos.writeObject(mNewsList);
            oos.close();
            fos.close();
        }catch(IOException ioe){
            ioe.printStackTrace();
        }
    }

    //反序列化读取书本数据
    protected void reload()
    {
        try
        {
            String fileName = getString(R.string.bookData);
            FileInputStream fis = new FileInputStream(fileName);
            ObjectInputStream ois = new ObjectInputStream(fis);
            mNewsList = (List<News>) ois.readObject();
            ois.close();
            fis.close();
        }catch(IOException ioe){
            ioe.printStackTrace();
            return ;
        }catch(ClassNotFoundException c){
            //System.out.println("Class not found");
            c.printStackTrace();
            return ;
        }
        return ;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = findViewById(R.id.recyclerview);

        //右下角添加按钮
        ImageButton imageButton = findViewById(R.id.imageButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View view) {
                                               //跳转到新建页面
                                               //setContentView(R.layout.activity_add);
                                               Intent gotoAdd = new Intent();
                                               gotoAdd.setClass(MainActivity.this,Add.class);
                                               startActivity(gotoAdd);
                                           }
                                       });
        //接受来自imageButoon即Add的数据
        reload();
        News news = new News();
        Intent receiveButton = this.getIntent();
        int Button = receiveButton.getIntExtra("button",0);
        switch (Button)
        {
            //edit
            case 1:
                news.getmessage(receiveButton);
                mNewsList.add(news);
                save();
                break;
            //delete
            case 2:
                mNewsList=new ArrayList<News>();
                save();
                break;
            //add
            case 3:
                news.getmessage(receiveButton);
                news.pngId= R.drawable.ic_launcher_foreground;
                mNewsList.add(news);
                save();

                break;
            default:
                break;

        }
        // 构造一些数据
        /*
        for (int i = 0; i < 50; i++) {
            News news = new News();
            news.title = "标题" + i;
            news.content = "内容" + i;
            news.pngId= R.drawable.ic_launcher_foreground;
        }

         */
        reload();
        mMyAdapter = new MyAdapter(mNewsList,this);
        mRecyclerView.setAdapter(mMyAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        mRecyclerView.setLayoutManager(layoutManager);
    }



    //长按
    class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHoder> {

        private int position;
        private Context mContext;
        private List<News> mList;
        public MyAdapter(List<News> fruitList, Context mContext){this.mContext = mContext;this.mList=fruitList;}
        public int getContextMenuPosition() { return position; }
        public void setContextMenuPosition(int position) { this.position = position; }

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
            holder.mTitleContent.setText(news.author);
            holder.mTitlePng.setImageResource(news.pngId);
        }

        @Override
        public int getItemCount() {
            return mNewsList.size();
        }

        class MyViewHoder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener{
            TextView mTitleTv;
            TextView mTitleContent;
            ImageView mTitlePng;

            public MyViewHoder(@NonNull View itemView) {
                super(itemView);
                mTitleTv = itemView.findViewById(R.id.textView);
                mTitleContent = itemView.findViewById(R.id.textView2);
                mTitlePng = itemView.findViewById(R.id.imageView);
                itemView.setOnCreateContextMenuListener(this);
            }

            @Override
            public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
                //注意传入的menuInfo为null
                News mSelectModelUser = mNewsList.get(getContextMenuPosition());
                Log.i("UserAdapter", "onCreateContextMenu: "+getContextMenuPosition());
                menu.setHeaderTitle(mSelectModelUser.title);
                ((MainActivity)mContext).CreateMenu(menu);
            }
        }
    }
    }



