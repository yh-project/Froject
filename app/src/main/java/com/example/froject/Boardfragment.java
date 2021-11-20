package com.example.froject;

import android.app.Dialog;
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
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

import java.lang.reflect.Array;
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
    private ClickCallbackListener2 callbackListener2;

    private RecyclerView smallcategoryrecyclerview;
    private SmaillCategoryAdapter smaillCategoryAdapter;
    private String[] smallcategorylist;

    private ArrayList<PostData> list = new ArrayList<>();
    private ArrayList<DocumentReference> listDoc = new ArrayList<>();
    private PostData[] lists;
    private int docSize;

    private int prev=-1, prev_big=-1;

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    DocumentReference docRef = db.collection("users").document(user.getEmail());
    CollectionReference boardRef = docRef.collection("Board");
    PostData tmp;
    String bigcat="";
    boolean[] is_checked = new boolean[10];
    boolean[] is_checked_big = new boolean[7];

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_board, container, false);

        for(int i=0;i<10;i++)
            is_checked[i]=false;
        for(int i=0;i<7;i++)
            is_checked_big[i]=false;

        if (this.getArguments() != null)
            bigcat = this.getArguments().getString("bigcat");

        Log.w("omg get bigcat : ",bigcat);
        //whereArrayContains("bigCategory",bigcat)
        db.collectionGroup("Board").orderBy("writeTime", DESCENDING).
                whereArrayContains("bigCategory",bigcat).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
                postrecyclerView = v.findViewById(R.id.boardRecyclerView);
                docSize = task.getResult().getDocuments().size();
                Log.w(TAG, "omg" + docSize);
                for (int i = 0; i < docSize; i++) {
                    list.add(task.getResult().getDocuments().get(i).toObject(PostData.class));
                    listDoc.add(task.getResult().getDocuments().get(i).getReference());
                }
                postAdapter = new PostAdapter(list);
                postAdapter.setListDoc(listDoc);
                postAdapter.setUser(user.getEmail());
                postrecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
                postrecyclerView.setAdapter(postAdapter);
                setLikeClickListener(postAdapter,task);
                setDotClickListener(postAdapter ,task);
            }
        });
        // 큰카테고리 뷰에따른 작은카테고리
        bigcategoryrecyclerview = v.findViewById(R.id.bigcategoryRecyclerView);
        bigcategorylist = getResources().getStringArray(R.array.Bigcategory);
        int cnt=0;
        for(String cat:bigcategorylist) {
            if(cat.equals(bigcat)) {
                prev_big = cnt;
                is_checked_big[cnt]=true;
                break;
            }
            cnt++;
        }
        bigCategoryAdapter = new BigCategoryAdapter(bigcategorylist, is_checked_big);

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
            public void callBack(String name, int a) {
                for(int i=0;i<10;i++)
                    is_checked[i]=false;
                is_checked_big[a]=true;
                is_checked_big[prev_big]=false;
                bigCategoryAdapter.notifyItemChanged(a);
                bigCategoryAdapter.notifyItemChanged(prev_big);
                prev_big = a;
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
                getDBWithSort(name,v);
            }
        };
        bigCategoryAdapter.setCallbackListener(callbackListener);
        bigcategoryrecyclerview.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        bigcategoryrecyclerview.setAdapter(bigCategoryAdapter);
        return v;
    }

    public void setRecyclerView(View v, String[] smallcategorylist) {
        callbackListener2 = new ClickCallbackListener2() {
            @Override
            public void callBack(String name, int a) {
                getDBWithSort2(name,v);
                if (prev != -1)
                    is_checked[prev]=false;
                is_checked[a]=true;
                smaillCategoryAdapter.notifyItemChanged(a);
                smaillCategoryAdapter.notifyItemChanged(prev);
                prev = a;

            }
        };
        smallcategoryrecyclerview = v.findViewById(R.id.smallcategoryRecyclerView);
        smaillCategoryAdapter = new SmaillCategoryAdapter(smallcategorylist, is_checked);
        smallcategoryrecyclerview.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        smaillCategoryAdapter.setCallbackListener2(callbackListener2);
        smallcategoryrecyclerview.setAdapter(smaillCategoryAdapter);
    }

    public void getDBWithSort(String criteria, View v) {
        list.clear();
        db.collectionGroup("Board").orderBy("writeTime", DESCENDING).
                whereArrayContains("bigCategory",criteria).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
                if (!task.isSuccessful()) {

                    return;
                }
                //postrecyclerView = v.findViewById(R.id.boardRecyclerView);
                docSize = task.getResult().getDocuments().size();
                Log.w(TAG, "omg" + docSize);
                for (int i = 0; i < docSize; i++) {
                    list.add(task.getResult().getDocuments().get(i).toObject(PostData.class));
                    listDoc.add(task.getResult().getDocuments().get(i).getReference());
                }
                postAdapter = new PostAdapter(list);
                postAdapter.setListDoc(listDoc);
                postAdapter.setUser(user.getEmail());
                postrecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
                postrecyclerView.setAdapter(postAdapter);
                setLikeClickListener(postAdapter,task);
            }
        });
    }

    public void getDBWithSort2(String criteria, View v) {
        list.clear();
        db.collectionGroup("Board").orderBy("writeTime", DESCENDING).
                whereArrayContains("smallCategory",criteria).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
                if (!task.isSuccessful()) {
                    return;
                }
                //postrecyclerView = v.findViewById(R.id.boardRecyclerView);
                docSize = task.getResult().getDocuments().size();
                Log.w(TAG, "omg" + docSize);
                for (int i = 0; i < docSize; i++) {
                    list.add(task.getResult().getDocuments().get(i).toObject(PostData.class));
                    listDoc.add(task.getResult().getDocuments().get(i).getReference());
                }
                postAdapter = new PostAdapter(list);
                postAdapter.setListDoc(listDoc);
                postAdapter.setUser(user.getEmail());
                postrecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
                postrecyclerView.setAdapter(postAdapter);
                setLikeClickListener(postAdapter,task);
            }
        });
    }

    public void setLikeClickListener(PostAdapter postAdapter, Task<QuerySnapshot> task) {
        postAdapter.setLikeClickListener(new LikeClickListener() {
            @Override
            public void onStarClick(View view, int position) {
                Boolean isStar = false;
                ImageView imageView =(ImageView)view;
                isStar = list.get(position).getStar().contains(user.getEmail());
                if (!list.get(position).getEmail().equals(user.getEmail())) {
                    if (isStar) {
                        imageView.setImageResource(R.drawable.unstar);
                        list.get(position).getStar().remove(user.getEmail());
                        task.getResult().getDocuments().get(position).getReference().update("star", list.get(position).getStar());
                    } else {
                        imageView.setImageResource(R.drawable.star);
                        list.get(position).getStar().add(user.getEmail());
                        task.getResult().getDocuments().get(position).getReference().update("star", list.get(position).getStar());
                    }
                }
            }
        });
    }
    public void setDotClickListener(PostAdapter postAdapter, Task<QuerySnapshot> task) {
        postAdapter.setDotClickListener(new DotClickListener() {
            @Override
            public void onDotClick(View view, int position) {
                ImageView imageView = (ImageView) view;
                Dialog dialog = new Dialog(getContext());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.threedot_dialog_board);
                if (!list.get(position).getEmail().equals(user.getEmail())) {
                    dialog.show();
                    TextView warn = dialog.findViewById(R.id.warn);
                    warn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startToast("신고할게요");
                            dialog.dismiss();
                        }
                    });
                    TextView black = dialog.findViewById(R.id.black);
                    black.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startToast("차단할게요");
                            dialog.dismiss();
                        }
                    });
                    TextView bye = dialog.findViewById(R.id.bye);
                    bye.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startToast("더이상 보지않아요");
                            dialog.dismiss();
                        }
                    });
                }
            }
        });
    }

    private void startToast(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }
}