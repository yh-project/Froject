package com.example.froject;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.core.app.ComponentActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.firestore.Query.Direction;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.core.OrderBy;
import com.squareup.okhttp.internal.DiskLruCache;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Document;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.google.firebase.firestore.Query.Direction.DESCENDING;


public class Boardfragment extends Fragment {
    private static final String TAG = "Boardfragment";
    private RecyclerView postrecyclerView;
    private PostAdapter postAdapter;

    private RecyclerView bigcategoryrecyclerview;
    private BigCategoryAdapter bigCategoryAdapter;
    private String[] bigcategorylist;
    private ClickCallbackListener callbackListener;

    private RecyclerView smallcategoryrecyclerview;
    private SmaillCategoryAdapter smaillCategoryAdapter;
    private String[] smallcategorylist;

    private DocumentSnapshot snaplist;
    private ArrayList<PostData> list = new ArrayList<>();
    private PostData[] lists;
    private int docSize;

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    DocumentReference docRef = db.collection("users").document(user.getEmail());
    CollectionReference boardRef = docRef.collection("Board");
    PostData tmp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_board, container, false);
        String bigcat="";

        if (this.getArguments() != null)
            bigcat = this.getArguments().getString("bigcat");

        db.collectionGroup("Board").orderBy("writetime", DESCENDING).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
                postrecyclerView = v.findViewById(R.id.boardRecyclerView);
                docSize = task.getResult().getDocuments().size();
                Log.w(TAG, "omg" + docSize);
                for (int i = 0; i < docSize; i++) {
                    list.add(task.getResult().getDocuments().get(i).toObject(PostData.class));
                }
                postAdapter = new PostAdapter(list);
                postrecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
                postrecyclerView.setAdapter(postAdapter);
            }
        });
        // 큰카테고리 뷰에따른 작은카테고리
        bigcategoryrecyclerview = v.findViewById(R.id.bigcategoryRecyclerView);
        bigcategorylist = getResources().getStringArray(R.array.Bigcategory);
        bigCategoryAdapter = new BigCategoryAdapter(bigcategorylist);

        //이전 카테고리뷰에서 누른 카테고리의 하부 카테고리 기본 생성
        switch(bigcat) {
            case "디자인":
                smallcategorylist = getResources().getStringArray(R.array.Designcategory);
                setRecyclerView(v, smallcategorylist);
                break;
            case "개발":
                smallcategorylist = getResources().getStringArray(R.array.Developcategory);
                setRecyclerView(v, smallcategorylist);
                break;
            case "사진·영상":
                smallcategorylist = getResources().getStringArray(R.array.Photocategory);
                setRecyclerView(v, smallcategorylist);
                break;
            case "번역·통역":
                smallcategorylist = getResources().getStringArray(R.array.Translatecategory);
                setRecyclerView(v, smallcategorylist);
                break;
            case "기획":
                smallcategorylist = getResources().getStringArray(R.array.Plancategory);
                setRecyclerView(v, smallcategorylist);
                break;
            case "인테리어":
                smallcategorylist = getResources().getStringArray(R.array.Interiorlcategory);
                setRecyclerView(v, smallcategorylist);
                break;
            case "대외활동":
                smallcategorylist = getResources().getStringArray(R.array.Extracategory);
                setRecyclerView(v, smallcategorylist);
                break;
        }

        //큰카테고리 선택시 작은카테고리 생성
        callbackListener = new ClickCallbackListener() {
            @Override
            public void callBack(String name) {
                switch(name) {
                    case "디자인":
                        smallcategorylist = getResources().getStringArray(R.array.Designcategory);
                        setRecyclerView(v, smallcategorylist);
                        break;
                    case "개발":
                        smallcategorylist = getResources().getStringArray(R.array.Developcategory);
                        setRecyclerView(v, smallcategorylist);
                        break;
                    case "사진·영상":
                        smallcategorylist = getResources().getStringArray(R.array.Photocategory);
                        setRecyclerView(v, smallcategorylist);
                        break;
                    case "번역·통역":
                        smallcategorylist = getResources().getStringArray(R.array.Translatecategory);
                        setRecyclerView(v, smallcategorylist);
                        break;
                    case "기획":
                        smallcategorylist = getResources().getStringArray(R.array.Plancategory);
                        setRecyclerView(v, smallcategorylist);
                        break;
                    case "인테리어":
                        smallcategorylist = getResources().getStringArray(R.array.Interiorlcategory);
                        setRecyclerView(v, smallcategorylist);
                        break;
                    case "대외활동":
                        smallcategorylist = getResources().getStringArray(R.array.Extracategory);
                        setRecyclerView(v, smallcategorylist);
                        break;
                }
            }
        };
        bigCategoryAdapter.setCallbackListener(callbackListener);
        bigcategoryrecyclerview.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        bigcategoryrecyclerview.setAdapter(bigCategoryAdapter);
        return v;
    }

    public void setRecyclerView(View v, String[] smallcategorylist) {
        smallcategoryrecyclerview = v.findViewById(R.id.smallcategoryRecyclerView);
        smaillCategoryAdapter = new SmaillCategoryAdapter(smallcategorylist);
        smallcategoryrecyclerview.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        smallcategoryrecyclerview.setAdapter(smaillCategoryAdapter);
    }


    //old data
    /*LinearLayout postlist;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_board, container, false);

        postlist = v.findViewById(R.id.postList);

        v.findViewById(R.id.addBtn).setOnClickListener(onClickListener);

        return v;
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()) {
                case R.id.addBtn:
                    add_post();
                    break;
            }
        }
    };

    private void add_post() {
        // 레이아웃 생성, 설정
        LinearLayout post = new LinearLayout(getActivity().getApplicationContext());

        final int height_post = getDp(100);
        final int top_post = getDp(10);
        final int left_post = getDp(10);
        final int bottom_post = getDp(10);
        final int right_post = getDp(10);

        LinearLayout.LayoutParams param_post = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, height_post);
        param_post.setMargins(left_post, top_post, right_post, bottom_post);

        post.setBackgroundColor(Color.rgb(255,255,255));
        post.setOrientation(LinearLayout.HORIZONTAL);
        post.setGravity(Gravity.CENTER_VERTICAL);

        post.setLayoutParams(param_post);
        postlist.addView(post);


        // 이미지뷰 생성, 설정
        ImageView thumbnail = new ImageView(getActivity().getApplicationContext());

        final int width_thumbnail = getDp(52);
        final int height_thumbnail = getDp(52);
        final int left_thumbnail = getDp(20);

        LinearLayout.LayoutParams param_thumbnail = new LinearLayout.LayoutParams(width_thumbnail, height_thumbnail);
        param_thumbnail.leftMargin = left_thumbnail;

        thumbnail.setBackgroundResource(R.mipmap.ic_launcher);

        thumbnail.setLayoutParams(param_thumbnail);
        post.addView(thumbnail);

        // 콘텐츠 레이아웃 생성, 설정
        LinearLayout content = new LinearLayout(getActivity().getApplicationContext());

        final int left_content = getDp(30);

        LinearLayout.LayoutParams param_content = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        param_content.leftMargin = left_content;

        content.setOrientation(LinearLayout.VERTICAL);
        content.setGravity(Gravity.CENTER_VERTICAL);

        content.setLayoutParams(param_content);
        post.addView(content);

        // 텍스트뷰1 생성, 설정
        TextView text1 = new TextView(getActivity().getApplicationContext());

        LinearLayout.LayoutParams param_text1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        text1.setText("사람찾습니다");
        text1.setTextSize(30);

        text1.setLayoutParams(param_text1);
        content.addView(text1);

        // 텍스트뷰2 생성, 설정
        TextView text2 = new TextView(getActivity().getApplicationContext());

        final int top_text2 = getDp(10);
        LinearLayout.LayoutParams param_text2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        param_text2.topMargin = top_text2;
        text2.setText("도와주세요");
        text2.setTextSize(15);

        text2.setLayoutParams(param_text2);
        content.addView(text2);
    }

    private int getDp(Integer i) {
        int result = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, i, getResources().getDisplayMetrics());
        return result;
    }

    public void onBackPressed() {
        backAlert();
    }

    private void startToast(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    private void startActivity(Class c) {
        Intent intent = new Intent(getActivity(), c);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    private void backAlert() {
        AlertDialog.Builder msgBuilder = new AlertDialog.Builder(getActivity())
                .setTitle("나가기")
                .setMessage("메인으로감")
                .setPositiveButton("네", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        startActivity(MainActivity.class);
                    }
                })
                .setNegativeButton("아니요", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        startToast("시발 그럼 왜눌러 개새끼야.");
                    }
                });
        AlertDialog msgDlg = msgBuilder.create();
        msgDlg.show();
    }*/
}