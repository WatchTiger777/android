package com.example.myapplication;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentResultListener;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    BookListFragment bookListFragment = new BookListFragment();
    HistoryFragment historyFragment = new HistoryFragment();
    ArrayList<Fragment>  fragmentsArray = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentsArray.add(bookListFragment);
        fragmentsArray.add(historyFragment);
        //fragmentsArray.add(new BlankFragment());
        /*
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.setFragmentResultListener("deleteBook", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                News temp = (News)result.getSerializable("key");
                News temp2;
            }
        });

         */
        //创建适配器，并传入fragment
        ViewPaperAdapter viewPaperAdapter = new ViewPaperAdapter(MainActivity.this,fragmentsArray);
        //添加适配器
        ViewPager2 viewPager = findViewById(R.id.viewpaper2);
        viewPager.setAdapter(viewPaperAdapter);
        viewPager.setCurrentItem(0);
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