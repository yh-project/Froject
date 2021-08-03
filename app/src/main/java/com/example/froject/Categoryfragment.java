package com.example.froject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Categoryfragment extends Fragment {
    private RecyclerView categoryView;
    private CategoryAdapter categoryAdapter;
    private ArrayList<CategoryData> categoryList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_category, container, false);

        // 카테고리 목록
        categoryView = v.findViewById(R.id.categoryRecyclerview);
        categoryList = new ArrayList<>();
        for(int i=0; i<10; i++) {
            CategoryData categoryData = new CategoryData(R.drawable.design, "디자인"+i);
            categoryList.add(categoryData);
        }
        categoryAdapter = new CategoryAdapter(categoryList);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
        categoryView.setLayoutManager(gridLayoutManager);
        ItemHeightSpace itemHeightSpace = new ItemHeightSpace(100);
        categoryView.addItemDecoration(itemHeightSpace);
        categoryView.setAdapter(categoryAdapter);

        return v;
    }
}
