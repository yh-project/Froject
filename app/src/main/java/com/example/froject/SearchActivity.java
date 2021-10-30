package com.example.froject;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;

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
import java.util.regex.Pattern;

import static com.google.firebase.firestore.Query.Direction.DESCENDING;

public class SearchActivity extends AppCompatActivity {
    private RecyclerView recentrecyclerView;
    private RecyclerView rankrecyclerView;
    private RecentSearchAdapter recentSearchAdapter;
    private SearchRankAdapter searchRankAdapter;
    private ArrayList<RecentSearchData> recentList;
    private ArrayList<SearchRankData> rankList;
    private EditText editsearch;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        recentrecyclerView = findViewById(R.id.recentRecyclerView);
        rankrecyclerView = findViewById(R.id.rankRecyclerView);

        recentList = new ArrayList<>();
        rankList = new ArrayList<>();

        editsearch = (EditText) findViewById(R.id.searchdata);

        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);

        editsearch.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                String searchdata;
                switch (keyCode) {
                    case KeyEvent.KEYCODE_ENTER: //엔터키 눌렸을 때
                        if (EtNullCheck(editsearch) == 0) {
                            break;
                        }
                        searchdata = editsearch.getText().toString();//검색한거 스트링으로 저장

                        editsearch.clearFocus();//엔터키 누르면 커서 제거
                        imm.hideSoftInputFromWindow(editsearch.getWindowToken(), 0); //키보드 내려줌.


                        searching(searchdata);

                        editsearch.setText("");

                        break;
                    default:
                        return false;
                }
                return true;
            }
        });

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

    public int EtNullCheck(EditText a) {
        return a.getText().toString().length();
    }

    public void searching(String search) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        ArrayList<PostData> list = new ArrayList<>();
        ArrayList<DocumentReference> listDoc = new ArrayList<>();
        RecyclerView searchRecyclerView = findViewById(R.id.rankRecyclerView);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        db.collectionGroup("Board").orderBy("writeTime", DESCENDING).
                get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {

                if (!task.isSuccessful())
                    return;
                int docSize = task.getResult().getDocuments().size();
                PostAdapter postAdapter;

                Log.d("data",docSize+"");

                String[] search2 = search.split("\n");


                String pattern = "*"+search2[0]+"*";
                for (int i = 0; i < docSize; i++) {
                    try {
                        PostData data = task.getResult().getDocuments().get(i).toObject(PostData.class);
                        if (data.getSearchData().matches(pattern)) {
                            list.add(task.getResult().getDocuments().get(i).toObject(PostData.class));
                            listDoc.add(task.getResult().getDocuments().get(i).getReference());
                        }
                    }
                    catch (Exception e) {
                        break;
                    }

                }
                postAdapter = new PostAdapter(list);
                postAdapter.setListDoc(listDoc);
                postAdapter.setUser(user.getEmail());
                searchRecyclerView.setLayoutManager(new LinearLayoutManager(SearchActivity.this, RecyclerView.VERTICAL, false));
                searchRecyclerView.setAdapter(postAdapter);
                //setLikeClickListener(postAdapter,task);
            }
        });

        /*public void setLikeClickListener(PostAdapter postAdapter, Task<QuerySnapshot> task){
            postAdapter.setLikeClickListener(new LikeClickListener() {
                @Override
                public void onStarClick(View view, int position) {
                    Boolean isStar = false;
                    ImageView imageView = (ImageView) view;
                    isStar = list.get(position).getStar().contains(user.getEmail());
                    if (!list.get(position).getEmail().equals(user.getEmail())) {
                        if (isStar) {
                            imageView.setImageResource(R.drawable.ic_baseline_star_border_24);
                            list.get(position).getStar().remove(user.getEmail());
                            task.getResult().getDocuments().get(position).getReference().update("star", list.get(position).getStar());
                        } else {
                            imageView.setImageResource(R.drawable.ic_baseline_star_24);
                            list.get(position).getStar().add(user.getEmail());
                            task.getResult().getDocuments().get(position).getReference().update("star", list.get(position).getStar());
                        }
                    }

                }
            });
        }*/
    }

}
