package com.example.froject;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.ServerTimestamp;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import static android.text.InputType.TYPE_TEXT_FLAG_MULTI_LINE;

public class WriteActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private WritingAdapter writingAdapter;
    private ArrayList<WriteData> list;
    private PostData[] postDataArray;
    private WriteHolder writeHolder;
    private int a = 0;
    private String author;
    private String email;


    private static final String TAG = "WriteActivity";
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    DocumentReference docRef = db.collection("users").document(user.getEmail());
    CollectionReference boardRef = docRef.collection("Board");

    //LinearLayout contentslayout;
    PostData new_post;

    ArrayAdapter<CharSequence> Bigadapter, Smalladapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);

        findViewById(R.id.addContents).setOnClickListener(onClickListener);
        findViewById(R.id.finishcontents).setOnClickListener(onClickListener);

        recyclerView = findViewById(R.id.categoryContentRecyclerView);
        list = new ArrayList<>();
        WriteData writeData = new WriteData();
        list.add(writeData);
        writingAdapter = new WritingAdapter(list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        ItemHeightSpace itemHeightSpace = new ItemHeightSpace(50);
        recyclerView.addItemDecoration(itemHeightSpace);
        recyclerView.setAdapter(writingAdapter);

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<DocumentSnapshot> task) {
                    author = task.getResult().getString("name");
                    email = task.getResult().getString("email");
            }
        });


        /*db.collectionGroup("Board").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
                Log.w(TAG,"omg"+task.getResult().getDocuments().size());
            }
        });*/

        //contentslayout = findViewById(R.id.contentsLayout);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()) {
                case R.id.addContents:
                    /*PostData postData1 = list.get(0);
                    Log.w("omg",postData1.getInputSmallCategory());
                    Log.w("omg",postData1.getInputBigCategory());*/
                    WriteData writeData = new WriteData();
                    writingAdapter.additem(writeData);
                    writingAdapter.notifyDataSetChanged();
                    Log.d("개수", ""+list.size());
                    //writingAdapter.getItemCount();
                    if(++a==2) {
                        findViewById(R.id.addContents).setVisibility(View.INVISIBLE);
                        break;
                    }
                    break;
                case R.id.finishcontents:
                    /*PostData postData1 = list.get(0);
                    PostData postData2 = list.get(1);
                    Log.d("하이3", postData1.getInputContent());
                    Log.d("하이3", postData2.getInputContent());*/
                    String title = ((EditText)findViewById(R.id.inputTitle)).getText().toString();
                    String place = ((EditText)findViewById(R.id.inputPlace)).getText().toString();
                    String period = ((EditText)findViewById(R.id.inputPeriod)).getText().toString();
                    String totalcount = ((EditText)findViewById(R.id.totalCount)).getText().toString();
                    String maincontent = ((EditText)findViewById(R.id.inputContent)).getText().toString();

                    new_post = new PostData(title, place, period, maincontent, totalcount);
                    //firebase::database::ServerTimestamp();
                    Date date = new Date(System.currentTimeMillis());
                    Log.d("sex", ""+date);
                    SimpleDateFormat sdate = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
                    sdate.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));

                    //boardRef.get().getResult().getDocuments().size();

                    ArrayList<String> BigCategory = new ArrayList<>();
                    ArrayList<String> SmallCategory = new ArrayList<>();
                    ArrayList<String> CategoryContent = new ArrayList<>();
                    ArrayList<String> CountPeople = new ArrayList<>();

                    int total=0;
                    for(int i=0;i<list.size();i++) {
                        BigCategory.add(list.get(i).getBigCategory());
                        SmallCategory.add(list.get(i).getSmallCategory());
                        CategoryContent.add(list.get(i).getContent());
                        CountPeople.add(list.get(i).getCountPeople());
                        total += Integer.parseInt(list.get(i).getCountPeople());
                    }

                    new_post.setAuthor(author);
                    new_post.setEmail(email);
                    new_post.setBigCategory(BigCategory);
                    new_post.setSmallCategory(SmallCategory);
                    new_post.setCategoryContent(CategoryContent);
                    new_post.setCategoryPeople(CountPeople);
                    new_post.setTotalPeople(""+total);
                    boardRef.document(sdate.format(date)).set(new_post);
                    boardRef.document(sdate.format(date)).update("writetime",date);
                    db.collectionGroup("Board").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
                            boardRef.document(sdate.format(date)).update("postNumber",""+(task.getResult().getDocuments().size()+1));
                        }
                    });

                    Intent intent = getIntent();
                    intent.setClass(WriteActivity.this,MainActivity.class);
                    //intent.putExtra("reload",true);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                    break;
            }
        }
    };


    /*private void add_contents() {
        int id_contentlayout = 1;
        int id_contentfield = 10;
        int id_field1 = 100;
        int id_field2 = 1000;
        int id_contenttext = 10000;

        String personcount = ((EditText)findViewById(R.id.inputCount)).getText().toString();
        int index = Integer.parseInt(personcount);
        for(int i=0 ; i<index; i++) {
            // contentlayout 생성
            LinearLayout contentlayout = new LinearLayout(getApplicationContext());

            LinearLayout.LayoutParams param_contentlayout = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            contentlayout.setOrientation(LinearLayout.VERTICAL);
            contentlayout.setId(id_contentlayout+i);
            param_contentlayout.setMargins(0,0,0,getDp(10));

            contentlayout.setLayoutParams(param_contentlayout);
            contentslayout.addView(contentlayout);

            // contentfield 생성
            LinearLayout contentfield = new LinearLayout(getApplicationContext());

            LinearLayout.LayoutParams param_contentfield = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            contentfield.setOrientation(LinearLayout.HORIZONTAL);
            contentfield.setId(id_contentfield+i);

            contentfield.setLayoutParams(param_contentfield);
            contentlayout.addView(contentfield);

            // field1 생성
            TextView field1 = new TextView(getApplicationContext());

            LinearLayout.LayoutParams param_field1 = new LinearLayout.LayoutParams(getDp(100), LinearLayout.LayoutParams.WRAP_CONTENT);
            field1.setText("hello");
            field1.setTextSize(20);
            field1.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            field1.setId(id_field1+i);

            field1.setLayoutParams(param_field1);
            contentfield.addView(field1);

            // field2 생성
            TextView field2 = new TextView(getApplicationContext());

            LinearLayout.LayoutParams param_field2 = new LinearLayout.LayoutParams(getDp(100), LinearLayout.LayoutParams.WRAP_CONTENT);
            field2.setText("world");
            field2.setTextSize(20);
            field2.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            field2.setId(id_field2+i);

            field2.setLayoutParams(param_field2);
            contentfield.addView(field2);

            // contenttext 생성
            EditText contenttext = new EditText(getApplicationContext());

            LinearLayout.LayoutParams param_contenttext = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            contenttext.setBackgroundResource(R.drawable.borderline);
            contenttext.setGravity(Gravity.START);
            contenttext.setHint("content");
            contenttext.setInputType(InputType.TYPE_CLASS_TEXT | TYPE_TEXT_FLAG_MULTI_LINE);
            contenttext.setLines(7);
            contenttext.setPadding(getDp(5), getDp(5), getDp(5), getDp(5));
            contenttext.setId(id_contenttext+i);

            contenttext.setLayoutParams(param_contenttext);
            contentlayout.addView(contenttext);
        }
    }*/












    private void finish_contents() {

    }




    private int getDp(Integer j) {
        int result = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, j, getResources().getDisplayMetrics());
        return result;
    }
}
