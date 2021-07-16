package com.example.froject;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.StringBufferInputStream;
import java.util.ArrayList;

public class CategoryActivity extends AppCompatActivity {
    final static String TAG = "CategoryActivity";
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        Category category = new Category();

        TextView sex = ((TextView)findViewById(R.id.textView));


        int size1 = category.develop.size();
        int size2 = category.design.size();
        int size3 = category.manage.size();



        String sexy ="";



        for(int i=0;i<size1;i++) {
           sexy += (category.develop.get(i).toString() + "\n");
        }

        sex.setText(sexy);
        sex.setGravity(1);

    }
}
