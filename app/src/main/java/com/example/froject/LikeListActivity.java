package com.example.froject;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import static com.google.firebase.firestore.Query.Direction.DESCENDING;

public class LikeListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private PostAdapter likelistAdapter;
    private ArrayList<PostData> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_likelist);

        recyclerView = findViewById(R.id.likeRecyclerView);
        list = new ArrayList<>();
        ArrayList<DocumentReference> listDoc = new ArrayList<>();
        list.clear();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collectionGroup("Board").orderBy("writeTime", DESCENDING).
                whereArrayContains("star",user.getEmail()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
                if (!task.isSuccessful()) {
                    return;
                }
                //postrecyclerView = v.findViewById(R.id.boardRecyclerView);
                int docSize = task.getResult().getDocuments().size();
                Log.w("omg",""+docSize);
                for (int i = 0; i < docSize; i++) {
                    list.add(task.getResult().getDocuments().get(i).toObject(PostData.class));
                    listDoc.add(task.getResult().getDocuments().get(i).getReference());
                }
                likelistAdapter = new PostAdapter(list);
                likelistAdapter.setListDoc(listDoc);
                likelistAdapter.setUser(user.getEmail());
                recyclerView.setLayoutManager(new LinearLayoutManager(LikeListActivity.this, RecyclerView.VERTICAL, false));
                recyclerView.setAdapter(likelistAdapter);
            }
        });

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }


}
