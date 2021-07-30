package com.example.froject.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.froject.R;
import com.example.froject.databinding.ActivityLoginBinding;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        mAuth = FirebaseAuth.getInstance();

        @SuppressLint("NonConstantResourceId") View.OnClickListener onClickListener = v -> {
            switch(v.getId()) {
                case R.id.logIn:
                    log_In();
                    break;
                case R.id.gotoResetpass:
                    startActivity(PassresetActivity.class);
                    break;
                case R.id.gotoSignup:
                    startActivity(SignupActivity.class);
                    break;
            }
        };
        binding.logIn.setOnClickListener(onClickListener);
        binding.gotoSignup.setOnClickListener(onClickListener);
        binding.gotoResetpass.setOnClickListener(onClickListener);
    }

    private void log_In() {
        String email = (binding.loginEmail).getText().toString();
        String password = (binding.loginPass).getText().toString();

        if(email.length()>0 && password.length()>0) {
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            // 로그인 성공
                            FirebaseUser user = mAuth.getCurrentUser();
                            if(user != null) {
                                if(user.isEmailVerified()) {
                                    startToast("로그인 성공");
                                    //kang
                                    startActivity(MainActivity.class);
                                }else {
                                    startToast("이메일 인증을 완료해주세요.");
                                    FirebaseAuth.getInstance().signOut();
                                }
                            }
                        } else {
                            //로그인 실패
                            if (task.getException() instanceof FirebaseTooManyRequestsException) {
                                //오류시도 다수
                                startToast("최근 로그인 시도가 너무 많습니다.\n잠시 후 다시 시도해주세요.");
                            }
                            else if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                //비밀번호 오류
                                startToast("비밀번호가 틀렸습니다.");
                            }
                            else if (task.getException() instanceof FirebaseAuthInvalidUserException) {
                                //유저 존재 X
                                startToast("회원정보가 존재하지 않습니다.");
                            }
                            else {
                                startToast(task.getException().toString());
                            }

                        }
                    });
        } else {
            startToast("이메일 또는 비밀번호를 입력해주세요.");
        }
    }
    @Override
    public void onBackPressed() {
        finishAlert();
    }
    private void startToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
    private void startActivity(Class c) {
        Intent intent = new Intent(this, c);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
    private void finishAlert() {
        AlertDialog.Builder msgBuilder = new AlertDialog.Builder(LoginActivity.this)
                .setTitle("앱 종료")
                .setMessage("앱을 종료하시겠습니까?")
                .setPositiveButton("네", (dialog, i) -> {
                    moveTaskToBack(true);
                    finishAndRemoveTask();
                    android.os.Process.killProcess(android.os.Process.myPid());
                })
                .setNegativeButton("아니요", (dialog, i) -> startToast("시발 그럼 왜눌러 개새끼야."));
        AlertDialog msgDlg = msgBuilder.create();
        msgDlg.show();
    }
}