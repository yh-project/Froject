package com.example.froject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignupActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mAuth = FirebaseAuth.getInstance();

        findViewById(R.id.signUp).setOnClickListener(onClickListener);
        findViewById(R.id.gotoLogin).setOnClickListener(onClickListener);
        findViewById(R.id.back).setOnClickListener(onClickListener);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    @Override
    public void onBackPressed() {
        backAlert();
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()) {
                case R.id.signUp:
                    sign_Up();
                    break;
                case R.id.gotoLogin:
                    startActivity(LoginActivity.class);
                    break;
                case R.id.back:
                    backAlert();
                    break;
            }
        }
    };

    private void sign_Up() {
        String email = ((EditText)findViewById(R.id.setEmail)).getText().toString();
        String password = ((EditText)findViewById(R.id.setPass)).getText().toString();
        String checkpass = ((EditText)findViewById(R.id.passCheck)).getText().toString();

        if(email.length()>0 && password.length()>0 && checkpass.length()>0) {
            if(password.length() >= 6) {
                if(password.equals(checkpass)) {
                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(this, (task) ->  {
                                if (task.isSuccessful()) {
                                    // 회원가입 성공시
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    startToast("회원가입을 성공했습니다.");
                                    startActivity(MainActivity.class);
                                } else {
                                    // 회원가입실패시
                                    if(task.getException() != null) {
                                        startToast(task.getException().toString());
                                    }
                                }
                            });
                } else {
                    startToast("비밀번호 똑바로 입력했냐...");
                }
            } else {
                startToast("비밀번호 6자 이상이다.");
            }
        } else {
            startToast("이메일 또는 비밀번호를 입력해 주세요.");
        }
    }

    private void startToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    private void startActivity(Class c) {
        Intent intent = new Intent(this, c);
        startActivity(intent);
    }

    private void backAlert() {
        AlertDialog.Builder msgBuilder = new AlertDialog.Builder(SignupActivity.this)
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