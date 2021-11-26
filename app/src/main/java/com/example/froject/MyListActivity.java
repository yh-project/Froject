package com.example.froject;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.ktx.Firebase;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import static com.google.firebase.firestore.Query.Direction.DESCENDING;


public class MyListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private PostAdapter mylistAdapter;
    private ArrayList<PostData> list;
    private ArrayList<DocumentReference> listDoc;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mylist);

        Intent intent = getIntent();
        Info my_info =(Info)intent.getSerializableExtra("my_info");


        recyclerView = findViewById(R.id.mylistRecyclerView);
        list = new ArrayList<>();
        listDoc = new ArrayList<>();
        list.clear();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collectionGroup("Board").orderBy("writeTime", DESCENDING).
                whereEqualTo("email", my_info.getEmail()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
                if (!task.isSuccessful()) {
                    return;
                }
                //postrecyclerView = v.findViewById(R.id.boardRecyclerView);
                int docSize = task.getResult().getDocuments().size();
                Log.w("omg", "" + docSize);
                for (int i = 0; i < docSize; i++) {
                    list.add(task.getResult().getDocuments().get(i).toObject(PostData.class));
                    listDoc.add(task.getResult().getDocuments().get(i).getReference());
                }
                mylistAdapter = new PostAdapter(list);
                mylistAdapter.setListDoc(listDoc);
                mylistAdapter.setUser(user.getEmail());
                recyclerView.setLayoutManager(new LinearLayoutManager(MyListActivity.this, RecyclerView.VERTICAL, false));
                recyclerView.setAdapter(mylistAdapter);
                setDotClickListener(mylistAdapter, task);
            }
        });

        ((ImageView)findViewById(R.id.back)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
;               Dialog dialog = new Dialog(MyListActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.back_dialog_mylist);
                dialog.show();
                TextView okay = dialog.findViewById(R.id.okay);
                okay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MyListActivity.this, MainActivity.class);
                        intent.putExtra("data", "back_mylist");
                        startActivity(MainActivity.class);
                        dialog.dismiss();
                    }
                });
                TextView no = dialog.findViewById(R.id.no);
                no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            }
        });

    }

    public void setDotClickListener(PostAdapter postAdapter, Task<QuerySnapshot> task) {
        postAdapter.setDotClickListener(new DotClickListener() {
            @Override
            public void onDotClick(View view, int position) {
                ImageView imageView = (ImageView) view;
                Dialog dialog = new Dialog(MyListActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.threedot_dialog_mylist);
                if (list.get(position).getEmail().equals(user.getEmail())) {
                    Log.d("ㅎㅇ", "ㅎㅇ");
                    dialog.show();
                    TextView edit = dialog.findViewById(R.id.edit);
                    edit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startToast("수정하겠습니다.");
                            Intent intent = new Intent(MyListActivity.this, WriteActivity.class);
                            intent.putExtra("postData", list.get(position));
                            startActivity(WriteActivity.class);
                            dialog.dismiss();
                        }
                    });
                    TextView delete = dialog.findViewById(R.id.delete);
                    delete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startToast("해당글을 삭제합니다.");
                            dialog.dismiss();
                        }
                    });
                    TextView complete = dialog.findViewById(R.id.complete);
                    complete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startToast("프로젝트가 종료되었습니다.");
                            listDoc.get(position).collection("PostComments").document();
                            FirebaseFirestore.getInstance().collection("rooms").whereEqualTo("title",list.get(position))
                                    .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
                                    /*task.getResult().toObjects()
                                    ArrayList<String> users = (ArrayList<String>)((QuerySnapshot)task.getResult()).
                                    for (String user:users) {
                                        listDoc.get(position).collection("PostComments").document(user);
                                    }*/
                                }
                            });
                            dialog.dismiss();
                        }
                    });
                }
            }
        });
    }

    private void startToast(String msg) {
        Toast.makeText(MyListActivity.this, msg, Toast.LENGTH_SHORT).show();
    }

    private void startActivity(Class c) {
        Intent intent = new Intent(this, c);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

}

