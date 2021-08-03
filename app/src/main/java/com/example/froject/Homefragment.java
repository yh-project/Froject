package com.example.froject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Homefragment extends Fragment {
    private ViewPager viewpager;
    private DictionaryViewPagerAdapter pagerAdapter;

    private RecyclerView recyclerView;
    private LikelistAdapter likelistAdapter;
    private ArrayList<LikePostData> list;

    private RecyclerView categoryView;
    private CategoryAdapter categoryAdapter;
    private ArrayList<CategoryData> categoryList;

    int currentPage = 0;
    Timer timer;
    final long DELAY_MS = 500;
    final long PERIOD_MS = 3000;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        ImageButton search = v.findViewById(R.id.search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(SearchActivity.class);
            }
        });

        // 분야사전 배너
        viewpager = v.findViewById(R.id.dictionary_banner);
        pagerAdapter = new DictionaryViewPagerAdapter(getActivity());
        viewpager.setAdapter(pagerAdapter);

        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            @Override
            public void run() {
                if(currentPage == 5) {
                    currentPage = 0;
                };
                viewpager.setCurrentItem(currentPage++, true);
            }
        };

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, DELAY_MS, PERIOD_MS);

        // 카테고리 목록
        categoryView = v.findViewById(R.id.homecategoryRecyclerview);
        categoryList = new ArrayList<>();
        for(int i=0; i<10; i++) {
            CategoryData categoryData = new CategoryData(R.drawable.design, "사진·영상"+i);
            categoryList.add(categoryData);
        }
        categoryAdapter = new CategoryAdapter(categoryList);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 4);
        categoryView.setLayoutManager(gridLayoutManager);
        categoryView.setAdapter(categoryAdapter);


        // 즐겨찾기 목록
        recyclerView = v.findViewById(R.id.likerecyclerview);
        list = new ArrayList<>();
        for(int i=0; i<10; i++) {
            LikePostData likePostData = new LikePostData(""+i, ""+i, ""+i );
            list.add(likePostData);
        }
        likelistAdapter = new LikelistAdapter(list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(likelistAdapter);

        return v;
    }

    private void startActivity(Class c) {
        Intent intent = new Intent(getActivity(), c);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}


