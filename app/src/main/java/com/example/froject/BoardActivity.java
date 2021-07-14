package com.example.froject;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Toast;


public class BoardActivity extends AppCompatActivity {
    LinearLayout postlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);

        postlist = findViewById(R.id.postList);

        findViewById(R.id.addBtn).setOnClickListener(onClickListener);
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
        LinearLayout post = new LinearLayout(getApplicationContext());

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
        ImageView thumbnail = new ImageView(getApplicationContext());

        final int width_thumbnail = getDp(52);
        final int height_thumbnail = getDp(52);
        final int left_thumbnail = getDp(20);

        LinearLayout.LayoutParams param_thumbnail = new LinearLayout.LayoutParams(width_thumbnail, height_thumbnail);
        param_thumbnail.leftMargin = left_thumbnail;

        thumbnail.setBackgroundResource(R.mipmap.ic_launcher);

        thumbnail.setLayoutParams(param_thumbnail);
        post.addView(thumbnail);

        // 콘텐츠 레이아웃 생성, 설정
        LinearLayout content = new LinearLayout(getApplicationContext());

        final int left_content = getDp(30);

        LinearLayout.LayoutParams param_content = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        param_content.leftMargin = left_content;

        content.setOrientation(LinearLayout.VERTICAL);
        content.setGravity(Gravity.CENTER_VERTICAL);

        content.setLayoutParams(param_content);
        post.addView(content);

        // 텍스트뷰1 생성, 설정
        TextView text1 = new TextView(getApplicationContext());

        LinearLayout.LayoutParams param_text1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        text1.setText("사람찾습니다");
        text1.setTextSize(30);

        text1.setLayoutParams(param_text1);
        content.addView(text1);

        // 텍스트뷰2 생성, 설정
        TextView text2 = new TextView(getApplicationContext());

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



    //
    @Override
    public void onBackPressed() {
        backAlert();
    }

    private void startToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    private void startActivity(Class c) {
        Intent intent = new Intent(this, c);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
    private void backAlert() {
        AlertDialog.Builder msgBuilder = new AlertDialog.Builder(BoardActivity.this)
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
    }
}