package com.example.froject;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.firebase.firestore.FirebaseFirestore;

public class WriteReviewActivity extends AppCompatActivity {

    ImageView[] total_star = new ImageView[5];
    ImageView[] a_star = new ImageView[5];
    ImageView[] b_star = new ImageView[5];
    ImageView[] c_star = new ImageView[5];
    int[] drawables = new int[5];
    int[] drawables2 = new int[5];
    int[] score = new int[4];
    EditText comment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_writereview);

        Intent intent = getIntent();
        String commender = intent.getStringExtra("commender");
        String commended = intent.getStringExtra("commended");
        String postNumber = intent.getStringExtra("postNumber");
        PostComments postComments = new PostComments("",postNumber,commender,commended);

        comment = findViewById(R.id.inputReview);
         findViewById(R.id.finishBtn).setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 postComments.setComment(comment.getText().toString());
                 postComments.setScore(score);

                 FirebaseFirestore.getInstance().collection("post_comments").document().set(postComments);

                 Intent intent2 = new Intent(WriteReviewActivity.this,FinishActivity.class);
                 startActivity(intent2);
             }
         });

        drawables[0] = R.drawable.clicked_very_bad;
        drawables[1] = R.drawable.clicked_bad;
        drawables[2] = R.drawable.clicked_normal;
        drawables[3] = R.drawable.clicked_good;
        drawables[4] = R.drawable.clicked_very_good;

        drawables2[0] = R.drawable.very_bad;
        drawables2[1] = R.drawable.bad;
        drawables2[2] = R.drawable.normal;
        drawables2[3] = R.drawable.good;
        drawables2[4] = R.drawable.very_good;

        total_star[0]=findViewById(R.id.total_1);
        a_star[0]=findViewById(R.id.first_1);
        b_star[0]=findViewById(R.id.second_1);
        c_star[0]=findViewById(R.id.third_1);

        total_star[1]=findViewById(R.id.total_2);
        a_star[1]=findViewById(R.id.first_2);
        b_star[1]=findViewById(R.id.second_2);
        c_star[1]=findViewById(R.id.third_2);

        total_star[2]=findViewById(R.id.total_3);
        a_star[2]=findViewById(R.id.first_3);
        b_star[2]=findViewById(R.id.second_3);
        c_star[2]=findViewById(R.id.third_3);

        total_star[3]=findViewById(R.id.total_4);
        a_star[3]=findViewById(R.id.first_4);
        b_star[3]=findViewById(R.id.second_4);
        c_star[3]=findViewById(R.id.third_4);

        total_star[4]=findViewById(R.id.total_5);
        a_star[4]=findViewById(R.id.first_5);
        b_star[4]=findViewById(R.id.second_5);
        c_star[4]=findViewById(R.id.third_5);

    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            for(int i=0;i<5;i++) {
                if ( v.getId() == total_star[i].getId() ) {
                    total_star[i].setBackgroundResource(drawables[i]);
                    score[0] = i;
                }
                else
                    total_star[i].setBackgroundResource(drawables2[i]);
            }
        }
    };
    View.OnClickListener onClickListener2 = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            for(int i=0;i<5;i++) {
                if ( v.getId() == a_star[i].getId() ) {
                    a_star[i].setBackgroundResource(drawables[i]);
                    score[1] = i;
                }
                else
                    a_star[i].setBackgroundResource(drawables2[i]);
            }
        }
    };
    View.OnClickListener onClickListener3 = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            for(int i=0;i<5;i++) {
                if ( v.getId() == b_star[i].getId() ) {
                    b_star[i].setBackgroundResource(drawables[i]);
                    score[2] = i;
                }
                else
                    b_star[i].setBackgroundResource(drawables2[i]);
            }
        }
    };
    View.OnClickListener onClickListener4 = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            for(int i=0;i<5;i++) {
                if ( v.getId() == c_star[i].getId() ) {
                    c_star[i].setBackgroundResource(drawables[i]);
                    score[3] = i;
                }
                else
                    c_star[i].setBackgroundResource(drawables2[i]);
            }
        }
    };

}
