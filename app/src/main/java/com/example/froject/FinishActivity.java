package com.example.froject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class FinishActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<PostData> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish);

        Intent intent = getIntent();
        Info my_info = (Info)intent.getSerializableExtra("my_info");

        FirebaseFirestore.getInstance().collection("users").document(my_info.getEmail()).collection("Board").whereEqualTo("done",true).orderBy("writeTime").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
                if (!task.isSuccessful()) {
                    return;
                }

                int docSize = task.getResult().getDocuments().size();
                Log.w("omg", "" + docSize);
                for (int i = 0; i < docSize; i++) {
                    list.add(task.getResult().getDocuments().get(i).toObject(PostData.class));
                    //listDoc.add(task.getResult().getDocuments().get(i).getReference());
                }
                FinishAdapter mylistAdapter = new FinishAdapter(list);
                //mylistAdapter.setListDoc(listDoc);
                //mylistAdapter.setUser(user.getEmail());
                recyclerView.setLayoutManager(new LinearLayoutManager(FinishActivity.this, RecyclerView.VERTICAL, false));
                recyclerView.setAdapter(mylistAdapter);
            }
        });

    }
}
