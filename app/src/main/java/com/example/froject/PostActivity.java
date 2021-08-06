package com.example.froject;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PostActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private DetailPostAdapter detailPostAdapter;
    private ArrayList<DetailPostData> list;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        recyclerView = findViewById(R.id.postContentRecyclerView);

        list = new ArrayList<>();
        DetailPostData detailPostData1  = new DetailPostData("모집 내용", "아 ㅁㅊ 컴퓨터는 9시에 켜놓고 4시간동안 유튜브봤네... 왜 새벽2시인건데......하....");
        list.add(detailPostData1);

        for(int i=0; i<3; i++) {
            DetailPostData detailPostData2 = new DetailPostData("내용", "대분야", "소분야", "구인 수:"+i);
            list.add(detailPostData2);
        }

        detailPostAdapter = new DetailPostAdapter(list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(detailPostAdapter);
    }
}
