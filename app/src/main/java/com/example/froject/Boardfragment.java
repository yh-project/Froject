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
        String bigcat="", smallcat="";


        if (this.getArguments() != null)
            bigcat = this.getArguments().getString("bigcat");

        Log.w("omg get bigcat : ",bigcat);
        //whereArrayContains("bigcategory",bigcat)
        db.collectionGroup("Board").orderBy("writetime", DESCENDING).
                whereArrayContains("bigCategory",bigcat).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
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
}