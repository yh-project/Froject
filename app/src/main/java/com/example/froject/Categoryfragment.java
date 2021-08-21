package com.example.froject;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Categoryfragment extends Fragment {
    private static final String TAG = "CategoryFragment";
    private RecyclerView categoryView;
    private CategoryAdapter categoryAdapter;
    private ArrayList<CategoryData> categoryList;
    private Boardfragment boardfragment;
    private String[] a;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_category, container, false);

        a = getResources().getStringArray(R.array.Bigcategory);

        Log.w(TAG,"omg22"+getContext());

        // 카테고리 목록
        categoryView = v.findViewById(R.id.categoryRecyclerview);
        categoryList = new ArrayList<>();
        for(int i=0; i<7; i++) {
            CategoryData categoryData = new CategoryData(R.drawable.design, a[i]);
            categoryList.add(categoryData);
        }
        categoryAdapter = new CategoryAdapter(categoryList);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
        categoryView.setLayoutManager(gridLayoutManager);
        ItemHeightSpace itemHeightSpace = new ItemHeightSpace(100);
        categoryView.addItemDecoration(itemHeightSpace);
        categoryView.setAdapter(categoryAdapter);

        categoryAdapter.setOnItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(CategoryHolder holder, View view, int position) {
                String name = holder.categoryname.getText().toString();
                Bundle bundle = new Bundle();
                bundle.putString("bigcat",name);
                boardfragment = new Boardfragment();
                boardfragment.setArguments(bundle);
                addFragment(boardfragment);
            }
        });

        return v;

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
                List.get(i).setArguments(fragment.getArguments());      //need fix // it need add in Homefragment
                fragmentTransaction.remove(List.get(i));
                break;
            }
            fragmentTransaction.hide(List.get(i));
        }
        fragmentTransaction.add(R.id.main_frame, fragment);
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
}
