package com.example.froject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static com.google.firebase.firestore.Query.Direction.DESCENDING;

public class Homefragment extends Fragment {
    private static final String TAG = "Homefragment";
    private ViewPager viewpager;
    private DictionaryViewPagerAdapter pagerAdapter;

    private RecyclerView recyclerView;
    private PostAdapter likelistAdapter;
    private ArrayList<PostData> list;

    private RecyclerView categoryView;
    private CategoryAdapter categoryAdapter;
    private ArrayList<CategoryData> categoryList;

    private Boardfragment boardfragment;

    int currentPage = 0;
    Timer timer;
    final long DELAY_MS = 500;
    final long PERIOD_MS = 3000;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        String[] bigcategory = getResources().getStringArray(R.array.Bigcategory);
        String[] Designcategory = getResources().getStringArray(R.array.Designcategory);//7 7
        String[] Developcategory = getResources().getStringArray(R.array.Developcategory);//6 13
        String[] Photocategory = getResources().getStringArray(R.array.Photocategory);//12 25
        String[] Translatecategory = getResources().getStringArray(R.array.Translatecategory);//5 30
        String[] Plancategory = getResources().getStringArray(R.array.Plancategory);//1 31
        String[] Interiorlcategory = getResources().getStringArray(R.array.Interiorlcategory);//1 32

        ImageButton search = v.findViewById(R.id.search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(SearchActivity.class);
            }
        });

        int[] rand = make_num();
        String[] smallcat = new String[5];
        String[] bigcat = new String[5];

        for(int i=0;i<5;i++) {
            bigcat[i] = bigcategory[rand[i]];
            switch(rand[i]) {
                case 0:
                    smallcat[i]=Designcategory[rand[i+5]];
                    break;
                case 1:
                    smallcat[i]=Developcategory[rand[i+5]];
                    break;
                case 2:
                    smallcat[i]=Photocategory[rand[i+5]];
                    break;
                case 3:
                    smallcat[i]=Translatecategory[rand[i+5]];
                    break;
                case 4:
                    smallcat[i]=Plancategory[rand[i+5]];
                    break;
                case 5:
                    smallcat[i]=Interiorlcategory[rand[i+5]];
                    break;
            }
        }

        // 분야사전 배너
        viewpager = v.findViewById(R.id.dictionary_banner);
        pagerAdapter = new DictionaryViewPagerAdapter(getActivity(),bigcat,smallcat);
        viewpager.setClipToPadding(false);
        int dpValue = 36;
        float d = getResources().getDisplayMetrics().density;
        int margin = (int) (dpValue*d);
        viewpager.setPadding(margin, 0, margin, 0);
        viewpager.setPageMargin(margin/2);
        viewpager.setAdapter(pagerAdapter);
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            @Override
            public void run() {
                if(currentPage == 5) {
                    currentPage = 0;
                };
                viewpager.setCurrentItem(currentPage++, true);
            }
        };

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, DELAY_MS, PERIOD_MS);

        // 카테고리 목록
        bigcategory = getResources().getStringArray(R.array.Bigcategory);
        categoryView = v.findViewById(R.id.homecategoryRecyclerview);
        categoryList = new ArrayList<>();
        for(int i=0; i<7; i++) {
            CategoryData categoryData = new CategoryData(R.drawable.design, bigcategory[i]);
            categoryList.add(categoryData);
        }
        categoryAdapter = new CategoryAdapter(categoryList);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 4);
        categoryView.setLayoutManager(gridLayoutManager);
        categoryView.setAdapter(categoryAdapter);

        categoryAdapter.setOnItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(CategoryHolder holder, View view, int position) {
                BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.bottomNavi);
                bottomNavigationView.setSelectedItemId(R.id.item_fragment1);
                String name = holder.categoryname.getText().toString();
                Bundle bundle = new Bundle();
                bundle.putString("bigcat",name);
                boardfragment = new Boardfragment();
                boardfragment.setArguments(bundle);
                addFragment(boardfragment);
            }
        });


        // 즐겨찾기 목록
        recyclerView = v.findViewById(R.id.likerecyclerview);
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
                Log.w(TAG, "omg" + docSize);
                for (int i = 0; i < docSize; i++) {
                    list.add(task.getResult().getDocuments().get(i).toObject(PostData.class));
                    listDoc.add(task.getResult().getDocuments().get(i).getReference());
                }
                likelistAdapter = new PostAdapter(list);
                likelistAdapter.setListDoc(listDoc);
                likelistAdapter.setUser(user.getEmail());
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
                recyclerView.setAdapter(likelistAdapter);
            }
        });

        return v;
    }

    private void startActivity(Class c) {
        Intent intent = new Intent(getActivity(), c);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public int[] make_num() {
        int[] numbers = new int[5];
        int[] a = new int[10];
        int size = getResources().getStringArray(R.array.Designcategory).length;//7 7
        size += getResources().getStringArray(R.array.Developcategory).length;//6 13
        size += getResources().getStringArray(R.array.Interiorlcategory).length;//12 25
        size += getResources().getStringArray(R.array.Photocategory).length - 1;//5 30
        size += getResources().getStringArray(R.array.Plancategory).length;//1 31
        size += getResources().getStringArray(R.array.Translatecategory).length;//1 32

        for (int insertCur = 0; insertCur < numbers.length; insertCur++) {
            numbers[insertCur] = (int) (Math.random() * size);
            for (int searchCur = 0; searchCur < insertCur; searchCur++) {
                if (numbers[insertCur] == numbers[searchCur]) {
                    insertCur--;
                }
            }
        }
        for (int i = 0; i < numbers.length; i++) {
            switch (numbers[i]) {       //분류별 값 넣기
                case 0:                 //0~numbers.length-1 에는 대분류
                case 1:                 //numbers.length~numbers.length*2-1 에는 소분류 넣음
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                    a[i] = 0;
                    a[i + numbers.length] = numbers[i];
                    break;
                case 7:
                case 8:
                case 9:
                case 10:
                case 11:
                case 12:
                    a[i] = 1;
                    a[i + numbers.length] = numbers[i] - 7;
                    break;
                case 13:
                case 14:
                case 15:
                case 16:
                case 17:
                case 18:
                case 19:
                case 20:
                case 21:
                case 22:
                case 23:
                case 24:
                    a[i] = 2;
                    a[i + numbers.length] = numbers[i] - 13;
                    break;
                case 25:
                case 26:
                case 27:
                case 28:
                case 29:
                    a[i] = 3;
                    a[i + numbers.length] = numbers[i] - 25;
                    break;
                case 30:
                    a[i] = 4;
                    a[i + numbers.length] = numbers[i] - 30;
                    break;
                case 31:
                    a[i] = 5;
                    a[i + numbers.length] = numbers[i] - 31;
                    break;
            }
        }
        return a;
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, fragment);
        fragmentTransaction.commit();
    }

    private void addFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        List<Fragment> List = fragmentManager.getFragments();
        int listsize = List.size();
        Log.w(TAG, "fragment data : " + listsize + List.toString());

        for(int i=0;i<listsize;i++) {
            if (List.get(i).toString().indexOf("Boardfragment")!= -1) {
                Log.w("omg","wow");
                List.get(i).setArguments(fragment.getArguments());
                fragmentTransaction.remove(List.get(i));
                break;
            }
            fragmentTransaction.hide(List.get(i));
        }
        fragmentTransaction.add(R.id.main_frame, fragment,fragment.getClass().getSimpleName());
        fragmentTransaction.commit();
    }

    private void showFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        List<Fragment> List = fragmentManager.getFragments();
        int listsize = List.size();
        Log.w(TAG, "fragment data : "+listsize+List.toString());

        for(int i=0;i<listsize;i++) {
            if(!(fragment.equals(List.get(i)))) {
                fragmentTransaction.hide(List.get(i));
            }
        }
        fragmentTransaction.show(fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        Log.w("omg","hidden"+hidden+isHidden());
    }
}


