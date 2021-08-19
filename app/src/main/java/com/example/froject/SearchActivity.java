package com.example.froject;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {
    private RecyclerView recentrecyclerView;
    private RecyclerView rankrecyclerView;
    private RecentSearchAdapter recentSearchAdapter;
    private SearchRankAdapter searchRankAdapter;
    private ArrayList<RecentSearchData> recentList;
    private ArrayList<SearchRankData> rankList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        recentrecyclerView = findViewById(R.id.recentRecyclerView);
        rankrecyclerView = findViewById(R.id.rankRecyclerView);

        recentList = new ArrayList<>();
        rankList = new ArrayList<>();

        for(int i=0 ; i<10;i++) {
            RecentSearchData recentSearchData = new RecentSearchData("배고파"+(i+1));
            SearchRankData searchRankData = new SearchRankData(""+(i+1), "저녁은 삼겹살"+(i+1));

            recentList.add(recentSearchData);
            rankList.add(searchRankData);
        }
        recentSearchAdapter = new RecentSearchAdapter(recentList);
        searchRankAdapter = new SearchRankAdapter(rankList);

        recentrecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.HORIZONTAL, false));
        rankrecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false));

        recentrecyclerView.setAdapter(recentSearchAdapter);
        rankrecyclerView.setAdapter(searchRankAdapter);
    }
}
