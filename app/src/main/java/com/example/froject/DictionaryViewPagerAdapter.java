package com.example.froject;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.Switch;
import android.widget.TextView;

import java.util.List;
import java.util.Random;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;

public class DictionaryViewPagerAdapter extends PagerAdapter {
    private static final String TAG = "PagerAdapter";
    private Context context = null;
    private String[] bigcat;
    private String[] smallcat;
    int i = 0;

    public DictionaryViewPagerAdapter(Context context,String[] bigcat, String[] smallcat) {
        this.context = context;
        this.bigcat = bigcat;
        this.smallcat = smallcat;
    }

    public DictionaryViewPagerAdapter(Context context, String[] bigcat) {
        this.context = context;
        this.bigcat = bigcat;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = null;

        if (context != null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.dictionary_banner_page, container, false);
            ConstraintLayout layout = view.findViewById(R.id.imageLayout);
            TextView title = view.findViewById(R.id.category);
            TextView explain = view.findViewById(R.id.detailCategory);

            if(i==0) {
                layout.setBackgroundResource(R.drawable.design_second);
                explain.setText("디자인계열에서는 인간생활의 편리함과 아름다움을 추구하는 디자인 전반에 대한 지식과 이론을 습득하고 실기를 합니다.");
                i++;
            }
            else if(i==1) {
                layout.setBackgroundResource(R.drawable.develop_second);
                explain.setText("개발계열에서는 살아가는데에 있어 편의와 삶의 안정성을 제공해주는 다양한 기술, 실습과 이론들을 학습합니다.");
                i++;
            }
            else if(i>=2) {
                i = 0;
            }

            title.setText(bigcat[position]);

            TextView dictionary = view.findViewById(R.id.gotoDictionary);
            dictionary.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent nowcategory = new Intent(context, DictionaryActivity.class);
                    nowcategory.putExtra("contact_big", title.getText().toString());
                    //nowcategory.putExtra("contact_small", smallcat[position]);
                    nowcategory.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.startActivity(nowcategory);
                }
            });
        }
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return 7;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }



}