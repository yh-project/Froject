package com.example.froject;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PostActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private DetailPostAdapter detailPostAdapter;
    private ArrayList<PostData> list;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        recyclerView = findViewById(R.id.postContentRecyclerView);

        list = new ArrayList<>();
        PostData postData1  = new PostData("모집 내용", "내용", "장소", "기간", "n명 ");
        list.add(postData1);

        for(int i=0; i<10; i++) {
            PostData postData2 = new PostData("내용", "대분야", "소분야", "구인 수:"+i);
            list.add(postData2);
        }

        detailPostAdapter = new DetailPostAdapter(list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(detailPostAdapter);
    }
}
