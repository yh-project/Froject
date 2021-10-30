package com.example.froject;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

public class TutorialActivity extends AppCompatActivity {
    private ViewPager tutorialshow;
    private PagerAdapter pagerAdapter;
    private int[] pages;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        setContentView(R.layout.activity_tutorial);

        tutorialshow = findViewById(R.id.showTutorial);

        /*pages = new int[] {
                R.layout.tutorial_1,
                R.layout.tutorial_2,
                R.layout.tutorial_3,
                R.layout.tutorial_4
        };*/
        pagerAdapter = new PagerAdapter();
        tutorialshow.setAdapter(pagerAdapter);    }

    public class PagerAdapter extends androidx.viewpager.widget.PagerAdapter {
        private LayoutInflater layoutInflater;

        public PagerAdapter() {}

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view = layoutInflater.inflate(pages[position], container, false);
            container.addView(view);

            return view;
        }

        @Override
        public int getCount() {
            return pages.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }
}
