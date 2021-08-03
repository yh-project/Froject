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
import androidx.viewpager.widget.PagerAdapter;

public class DictionaryViewPagerAdapter extends PagerAdapter {
    private static final String TAG = "PagerAdapter";
    private Context context = null;
    private String[] bigcat;
    private String[] smallcat;

    public static final String randomcategory[] = {"디자인", "개발", "사진·영상", "번역·통역", "기획", "인테리어"};

    public DictionaryViewPagerAdapter(Context context,String[] bigcat, String[] smallcat) {
        this.context = context;
        this.bigcat = bigcat;
        this.smallcat = smallcat;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = null;

        Log.w(TAG,"OMG"+context.toString());

        if (context != null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.dictionary_banner_page, container, false);

            TextView title = view.findViewById(R.id.category);
            TextView explain = view.findViewById(R.id.detailCategory);

            title.setText(bigcat[position]);
            explain.setText(smallcat[position]);

            TextView dictionary = view.findViewById(R.id.gotoDictionary);
            dictionary.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent nowcategory = new Intent(context, DictionaryActivity.class);
                    nowcategory.putExtra("contact_title", title.getText().toString());
                    nowcategory.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
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
        return 5;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }



}