package com.example.froject;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
//
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.RelativeLayout;
import android.widget.Toast;


public class BoardActivity extends AppCompatActivity {

    public CardView mCardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);
        mCardView = (CardView) findViewById(R.id.Board);
    }

    public void btnClick(View v) {
        CardView myCardView = new CardView(this);
        CardView.LayoutParams mParams = new CardView.LayoutParams(
                CardView.LayoutParams.WRAP_CONTENT,
                CardView.LayoutParams.WRAP_CONTENT);
        mParams.topMargin = 400;
        myCardView.setLayoutParams(mParams);

        //Button btn = new Button(this);
        //btn.setText("버튼");
        //btn.setBackgroundColor(Color.YELLOW);
        //btn.setLayoutParams(mParams);
        //myCardView.addView(btn);

        //LinearLayout.LayoutParams tv_params = new LinearLayout.LayoutParams(
        //        LinearLayout.LayoutParams.WRAP_CONTENT,
        //        LinearLayout.LayoutParams.WRAP_CONTENT);

        //final RelativeLayout textView = new RelativeLayout(this);
        //tv_params.gravity = Gravity.CENTER;
        //tv_params.setMargins(0, 200, 0, 0);
        //textView.setLayoutParams(tv_params);
        //myCardView.addView(textView);


        mCardView.addView(myCardView);

        //btn.setOnClickListener(view -> textView.setText(editText.getText().toString()));
    }



    //
    @Override
    public void onBackPressed() {
        backAlert();
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()) {
                case R.id.logout:
                    FirebaseAuth.getInstance().signOut();
                    startActivity(LoginActivity.class);
                    break;
                case R.id.back:
                    backAlert();
                    break;
            }
        }
    };


    private void startToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    private void startActivity(Class c) {
        Intent intent = new Intent(this, c);
        startActivity(intent);
    }
    private void backAlert() {
        AlertDialog.Builder msgBuilder = new AlertDialog.Builder(BoardActivity.this)
                .setTitle("나가기")
                .setMessage("입력하신 내용이 사라집니다. \n정말 이 창을 나가시겠습니까?")
                .setPositiveButton("네", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        startActivity(LoginActivity.class);
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


