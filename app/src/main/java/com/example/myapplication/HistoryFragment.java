package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.fragment.app.FragmentTransaction;
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
 * Use the {@link HistoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HistoryFragment extends Fragment {
    RecyclerView mRecyclerView;
    historyAdapter mMyAdapter ;
    public int nowposition = 0;
    //静态类变量
    News deleteBook = new News();
    public List<News> mNewsList = new ArrayList<>();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HistoryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HistoryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HistoryFragment newInstance(String param1, String param2) {
        HistoryFragment fragment = new HistoryFragment();
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

        if(item.getGroupId()==1){
            switch (item.getItemId()) {
                //restore
                case 1:
                    News temp = mNewsList.get(nowposition);
                    //((MainActivity)getActivity()).setTemp(temp,true,false);
                    MainActivity operator = (MainActivity) getActivity();
                    ((MainActivity)getActivity()).getBookListFragment().mNewsList.add(temp);
                    ((MainActivity)getActivity()).getBookListFragment().mMyAdapter.notifyDataSetChanged();
                    operator.getBookListFragment().save();
                    mNewsList.remove(nowposition);
                    save();
                    mMyAdapter.notifyDataSetChanged();
                    //mMyAdapter.notifyItemChanged(nowposition);
                    //((MainActivity)getActivity()).rollTobooklist();
                    //刷新一次activity使得booklistfragment的onCreateView被调用，重新绘制
                    //Intent intent = new Intent(getActivity(), MainActivity.class);
                    //startActivity(intent);

                    break;
                //delete
                case 2:
                    mNewsList.remove(nowposition);
                    if(nowposition==0)
                    {
                        mMyAdapter.notifyDataSetChanged();
                    }
                    else{
                        mMyAdapter.notifyItemChanged(nowposition);
                    }
                    save();
                    //reload();
                    //finish();
                    break;
                case 3:
                    mNewsList.clear();
                    save();
                    //finish();
                    mMyAdapter.notifyDataSetChanged();
                    //Intent intent1 = new Intent(getActivity(), MainActivity.class);
                    //startActivity(intent1);
                    break;
                default:
                    break;
            }
        }

        return super.onContextItemSelected(item);
    }

    public void CreateMenu(Menu menu)
    {
        int groupID = 1;
        int order = 0;
        int[] itemID = {1,2,3};
        for(int i=0;i<itemID.length;i++)
        {
            switch(itemID[i])
            {
                case 1:
                    menu.add(groupID, itemID[i], order, "恢复");
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
            String fileName = getString(R.string.history);
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
            String fileName = getString(R.string.history);
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        /*
        getParentFragmentManager().setFragmentResultListener("deleteBook", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                deleteBook = (News)result.getSerializable("key");

            }
        });
        getParentFragmentManager().setFragmentResultListener("deleteBook", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                String string1 = result.getString("key1");

            }
        });

         */
        String fileName = getString(R.string.history);
        File file = new File(fileName);
        if (file.exists()){
            reload();
        }
        /*
        if (((MainActivity)getActivity()).delete){
            mNewsList.add(((MainActivity)getActivity()).getTemp(false,false));
            save();
        }

         */
        View view = inflater.inflate(R.layout.fragment_history,container,false);
        mRecyclerView= view.findViewById(R.id.recyclerview2);
        DividerItemDecoration mDivider = new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL);
        mRecyclerView.addItemDecoration(mDivider);


        mMyAdapter = new historyAdapter(mNewsList,getActivity());
        mRecyclerView.setAdapter(mMyAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        //((SimpleItemAnimator)mRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        /*
        //右下角添加按钮
        ImageButton imageButton = view.findViewById(R.id.imageButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //跳转到新建页面
                //setContentView(R.layout.activity_add);
                Intent gotoAdd = new Intent();
                gotoAdd.setClass(getActivity(),Add.class);
                startActivity(gotoAdd);
            }
        });

         */
        return view;
    }

    //长按
    class historyAdapter extends RecyclerView.Adapter<historyAdapter.MyViewHoder> {

        private int position;
        private Context mContext;
        private List<News> mList;
        public historyAdapter(List<News> fruitList, Context mContext){this.mContext = mContext;this.mList=fruitList;}
        public int getContextMenuPosition() { return position; }
        public void setContextMenuPosition(int position) { this.position = position; }

        @NonNull
        @Override
        public historyAdapter.MyViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = View.inflate(getActivity(), R.layout.book_list, null);
            historyAdapter.MyViewHoder myViewHoder = new historyAdapter.MyViewHoder(view);
            return myViewHoder;
        }

        @Override
        public void onViewRecycled(@NonNull historyAdapter.MyViewHoder holder) {
            holder.itemView.setOnLongClickListener(null);
            super.onViewRecycled(holder);
        }

        @Override
        public void onBindViewHolder(@NonNull historyAdapter.MyViewHoder holder, int position) {
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
                (((MainActivity)getActivity()).getHistoryFragment()).CreateMenu(menu);
            }
        }
    }
}