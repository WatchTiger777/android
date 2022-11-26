package com.example.myapplication;

import static android.app.Activity.RESULT_OK;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BookListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BookListFragment extends Fragment {
    RecyclerView mRecyclerView;
    MyAdapter mMyAdapter ;
    public int nowposition = 0;
    //静态类变量
    public List<News> mNewsList = new ArrayList<>();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public BookListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BooklistFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BookListFragment newInstance(String param1, String param2) {
        BookListFragment fragment = new BookListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }



    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if(item.getGroupId()==0){
            switch (item.getItemId()) {
                //edit
                case 1:
                    Intent gotoEdit = new News().sendmessage(mNewsList.get(nowposition),nowposition);
                    gotoEdit.setClass(getActivity(),Edit.class);
                    startActivityForResult(gotoEdit,1);
                    //startActivity(gotoEdit);

                    break;
                //delete
                case 2:
                    News deleteBook = mNewsList.get(nowposition);
                    mNewsList.remove(nowposition);

                    ((MainActivity)getActivity()).getHistoryFragment().mNewsList.add(deleteBook);
                    ((MainActivity)getActivity()).getHistoryFragment().mMyAdapter.notifyDataSetChanged();
                    save();
                    mMyAdapter.notifyDataSetChanged();
                    break;

                //clear
                case 3:
                    MainActivity operator = (MainActivity) getActivity();
                    operator.getHistoryFragment().mNewsList.addAll(mNewsList);
                    operator.getHistoryFragment().save();
                    operator.getHistoryFragment().mMyAdapter.notifyDataSetChanged();

                    mNewsList.clear();
                    save();
                    mMyAdapter.notifyDataSetChanged();

                    break;
                default:
                    break;
            }
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
            String fileName = getString(R.string.bookData);
            FileOutputStream fos= new FileOutputStream(fileName);
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

    //接受来自其他activity的数据
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        News news = new News();
        if(resultCode == RESULT_OK)
        {
            switch(requestCode)
            {
                //接受来自edit发来的intent
                case 1:
                    news.getmessage(data);
                    int tempposition = data.getIntExtra("nowposition",0);
                    mNewsList.set(tempposition,news);
                    mMyAdapter.notifyItemChanged(nowposition);
                    save();
                    break;


                //add
                case 2:
                    news.getmessage(data);
                    mNewsList.add(news);
                    save();
                    mMyAdapter.notifyDataSetChanged();

                    break;
                default:
                    break;


            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_booklist,container,false);
        mRecyclerView= view.findViewById(R.id.recyclerview);
        DividerItemDecoration mDivider = new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL);
        mRecyclerView.addItemDecoration(mDivider);

        String fileName = getString(R.string.bookData);
        File file = new File(fileName);
        if (file.exists()){
            reload();
        }


        mMyAdapter = new MyAdapter(mNewsList,getActivity());
        mRecyclerView.setAdapter(mMyAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        //((SimpleItemAnimator)mRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        //右下角添加按钮
        ImageButton imageButton = view.findViewById(R.id.imageButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //跳转到新建页面
                //setContentView(R.layout.activity_add);
                Intent gotoAdd = new Intent();
                gotoAdd.setClass(getActivity(),Add.class);
                //startActivity(gotoAdd);
                startActivityForResult(gotoAdd,2);
            }
        });
        return view;
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
        public MyAdapter.MyViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = View.inflate(getActivity(), R.layout.book_list, null);
            MyViewHoder myViewHoder = new MyAdapter.MyViewHoder(view);
            return myViewHoder;
        }

        @Override
        public void onViewRecycled(@NonNull MyAdapter.MyViewHoder holder) {
            holder.itemView.setOnLongClickListener(null);
            super.onViewRecycled(holder);
        }

        @Override
        public void onBindViewHolder(@NonNull MyAdapter.MyViewHoder holder, int position) {
            News news = mNewsList.get(position);
            holder.mTitleTv.setText(news.title);
            holder.mTitleContent.setText(news.author);
            holder.mTitlePng.setImageResource(news.pngId);
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    setContextMenuPosition(holder.getLayoutPosition());
                    return false;
                }
            });
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
                nowposition = getContextMenuPosition();
                //注意传入的menuInfo为null
                News mSelectModelUser = mNewsList.get(getContextMenuPosition());
                Log.i("UserAdapter", "onCreateContextMenu: "+getContextMenuPosition());
                menu.setHeaderTitle(mSelectModelUser.title);
                (((MainActivity)getActivity()).getBookListFragment()).CreateMenu(menu);
            }
        }
    }

}