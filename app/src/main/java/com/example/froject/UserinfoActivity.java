package com.example.froject;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class UserinfoActivity extends AppCompatActivity {
    private static final String TAG = "UserinfoActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userinfo);

        findViewById(R.id.back).setOnClickListener(onClickListener);
        findViewById(R.id.checkInfo).setOnClickListener(onClickListener);
    }

    @Override
    public void onBackPressed() {
        backAlert();
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()) {
                case R.id.back:
                    backAlert();
                    break;
                case R.id.checkInfo:
                    user_info();
                    break;
            }
        }
    };

    private void user_info() {
        String name = ((EditText)findViewById(R.id.setName)).getText().toString();
        String number = ((EditText)findViewById(R.id.setNumber)).getText().toString();
        String date = ((EditText)findViewById(R.id.setDate)).getText().toString();
        String univ = ((EditText)findViewById(R.id.setUniv)).getText().toString();
        String level = ((EditText)findViewById(R.id.setLevel)).getText().toString();
        String major = ((EditText)findViewById(R.id.setMajor)).getText().toString();

        if(name.length()>0 && number.length() > 9 && date.length() > 5 && univ.length() > 0 && level.length() > 0 && major.length() > 0) {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            FirebaseFirestore db = FirebaseFirestore.getInstance();

            Info info = new Info(name, number, date, univ, level, major);

            if(user != null) {
                db.collection("users").document(user.getUid()).set(info)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                startToast("회원정보를 등록했습니다.");
                                finish();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                startToast("등록을 실패하였습니다.");
                                Log.w(TAG, "Error writing document", e);
                            }
                        });
            }

        } else {
            startToast("회원정보를 입력해주세요.");
        }
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
        AlertDialog.Builder msgBuilder = new AlertDialog.Builder(UserinfoActivity.this)
                .setTitle("나가기")
                .setMessage("프로필을 등록하지 않으면 대부분의 기능을 사용하실 수 없습니다. \n정말 나가시겠습니까?")
                .setPositiveButton("네", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        finish();
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