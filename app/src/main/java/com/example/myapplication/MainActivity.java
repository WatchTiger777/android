package com.example.myapplication;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    BookListFragment bookListFragment = new BookListFragment();
    HistoryFragment historyFragment = new HistoryFragment();
    ArrayList<Fragment>  fragmentsArray = new ArrayList<>();
    public ViewPager2 viewpager;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentsArray.add(bookListFragment);
        fragmentsArray.add(historyFragment);


        //创建适配器，并传入fragment
        ViewPaperAdapter viewPaperAdapter = new ViewPaperAdapter(MainActivity.this,fragmentsArray);
        //添加适配器
        ViewPager2 viewPager = findViewById(R.id.viewpaper2);
        viewPager.setOffscreenPageLimit(2);     //让后台先加载history防止delete出bug
        viewPager.setPageTransformer(new ZoomOutPageTransformer());
        viewPager.setAdapter(viewPaperAdapter);
        viewPager.setCurrentItem(0);
        viewpager = viewPager;
        TabLayout tabLayout = findViewById(R.id.tabLayout);
        new TabLayoutMediator(tabLayout, viewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                //设置tab的文字
                switch (position){
                    case 0:
                        tab.setText("bookList");
                        break;
                    case 1:
                        tab.setText("history");
                        break;
                }

            }
        }).attach();

        //ViewPager2提供的滑动监听
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position,positionOffset,positionOffsetPixels);
            }

        });
    }

    public BookListFragment getBookListFragment() {
        return bookListFragment;
    }

    public HistoryFragment getHistoryFragment() {
        return historyFragment;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        MenuItem search=menu.findItem(R.id.search);
        SearchView mysearchview=(SearchView)search.getActionView();
        mysearchview.setQueryHint("搜索");
        List<News> mNewsList = getBookListFragment().mNewsList;
        List<News> mDeleteList = getHistoryFragment().mNewsList;
        List<News> mSearchList = new ArrayList<>();

        mysearchview.setOnQueryTextListener(new SearchView.OnQueryTextListener(){
            @Override
//当提交搜索框内容后执行的方法
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            public List<News> find(List<News> mNewsList,String dst){
               List<News> ans = new ArrayList<>();
               for(int i = 0;i<mNewsList.size();i++)
               {
                   if(mNewsList.get(i).title.equals(dst) || mNewsList.get(i).author.equals(dst) || mNewsList.get(i).isbn.equals(dst) || mNewsList.get(i).translator.equals(dst) || mNewsList.get(i).month.equals(dst) || mNewsList.get(i).publisher.equals(dst)|| mNewsList.get(i).year.equals(dst))
                   {
                       ans.add(mNewsList.get(i));
                   }
               }
               return ans;
            }
            @Override
//当搜索框内内容改变时执行的方法
            public boolean onQueryTextChange(String newText) {
                mSearchList.clear();
                mSearchList.addAll(find(mNewsList,newText));
                mSearchList.addAll(find(mDeleteList,newText));

                getBookListFragment().mNewsList = mSearchList;
                getBookListFragment().mMyAdapter.notifyDataSetChanged();

                return false;
            }
        });

        mysearchview.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {

                getBookListFragment().mNewsList = mNewsList;
                getHistoryFragment().mNewsList = mDeleteList;
                mSearchList.clear();
                getBookListFragment().mMyAdapter.notifyDataSetChanged();
                return false;
            }
        });{

        }
        return super.onCreateOptionsMenu(menu);
    }

    public void refresh (String newText){

        RecyclerView booklist = getBookListFragment().mRecyclerView;


    }

    public class ViewPaperAdapter extends FragmentStateAdapter {
        private ArrayList<Fragment> mfragments;
        public ViewPaperAdapter(@NonNull FragmentActivity fragmentActivity, ArrayList<Fragment> fragments){
            super(fragmentActivity);
            this.mfragments=fragments;
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            return mfragments.get(position);
        }

        @Override
        public int getItemCount() {
            return mfragments.size();
        }
    }
}