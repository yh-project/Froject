package com.example.froject;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class PostActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private DetailPostAdapter detailPostAdapter;
    private ArrayList<PostData> list = new ArrayList<>();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        PostData postdata = (PostData)getIntent().getSerializableExtra("PostData");
        TextView titleView = findViewById(R.id.postTitle);
        TextView nameView = findViewById(R.id.userId);

        titleView.setText(postdata.getTitle());
        nameView.setText(postdata.getAuthor());
        list.add(postdata);

        recyclerView = findViewById(R.id.postContentRecyclerView);

        for(int i=0; i< postdata.getBigCategory().size(); i++) {
            list.add(postdata);
        }

        detailPostAdapter = new DetailPostAdapter(list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(detailPostAdapter);
    }
}
