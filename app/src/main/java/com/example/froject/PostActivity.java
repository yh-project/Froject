package com.example.froject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

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
        String docRef = getIntent().getStringExtra("DocRef");
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        TextView titleView = findViewById(R.id.postTitle);
        TextView nameView = findViewById(R.id.userId);
        Button chatButton = findViewById(R.id.sendChatting);
        ImageView like = findViewById(R.id.pickLike);

        titleView.setText(postdata.getTitle());
        nameView.setText(postdata.getAuthor());
        list.add(postdata);

        postdata.getStar();

        Boolean isStar = postdata.getStar().contains(user.getEmail());
        if (isStar) {
            like.setImageResource(R.drawable.star);
        }
        else {
            like.setImageResource(R.drawable.unstar);
        }

        recyclerView = findViewById(R.id.postContentRecyclerView);

        for(int i=0; i< postdata.getBigCategory().size(); i++) {
            list.add(postdata);
        }

        detailPostAdapter = new DetailPostAdapter(list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(detailPostAdapter);

        if (postdata.getEmail().equals(user.getEmail())) {
            //findViewById(R.id.sendChatting).setVisibility(View.INVISIBLE);
            findViewById(R.id.pickLike).setVisibility(View.INVISIBLE);
            findViewById(R.id.deletepost).setVisibility(View.VISIBLE);
            TextView deletepost = findViewById(R.id.deletepost);
            chatButton.setText("수정하기");
            chatButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(PostActivity.this, WriteActivity.class);
                    intent.putExtra("you_email", postdata.Email);
                    intent.putExtra("postData",postdata);
                    intent.putExtra("DocRef",docRef);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            });
            deletepost.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.w("omg","delete");
                    FirebaseFirestore.getInstance().collection("users").document(postdata.getEmail()).collection("Board").document(docRef).delete();
                }
            });

        }
        else {
            like.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Boolean isStar = postdata.getStar().contains(user.getEmail());
                    if (isStar) {
                        like.setImageResource(R.drawable.unstar);
                        postdata.getStar().remove(user.getEmail());
                        FirebaseFirestore.getInstance().collection("users").document(postdata.getEmail()).collection("Board").document(docRef).update("star",postdata.getStar());
                    }
                    else {
                        like.setImageResource(R.drawable.star);
                        postdata.getStar().add(user.getEmail());
                        FirebaseFirestore.getInstance().collection("users").document(postdata.getEmail()).collection("Board").document(docRef).update("star",postdata.getStar());
                    }
                }
            });

            chatButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(PostActivity.this, ChatActivity.class);
                    intent.putExtra("you_email", postdata.Email);
                    intent.putExtra("title",postdata.title);
                    intent.putExtra("rName",postdata.author);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            });
        }
    }
}
