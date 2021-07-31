package com.example.froject;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
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

    int currentPage = 0;
    Timer timer;
    final long DELAY_MS = 500;
    final long PERIOD_MS = 3000;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        viewpager = v.findViewById(R.id.dictionary_banner);
        pagerAdapter = new DictionaryViewPagerAdapter(getActivity());
        viewpager.setAdapter(pagerAdapter);

        recyclerView = v.findViewById(R.id.likerecyclerview);
        list = new ArrayList<>();
        LikePostData likePostData1 = new LikePostData("배", "고", "파");
        LikePostData likePostData2 = new LikePostData("배고", "프다", "니까");
        list.add(likePostData1);
        list.add(likePostData2);
        likelistAdapter = new LikelistAdapter(list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(likelistAdapter);

        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            @Override
            public void run() {
                if(currentPage == 5) {
                    currentPage = 0;
                }
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
        return v;
    }
}
