package com.example.froject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        findViewById(R.id.logIn).setOnClickListener(onClickListener);
        findViewById(R.id.gotoSignup).setOnClickListener(onClickListener);
        findViewById(R.id.gotoResetpass).setOnClickListener(onClickListener);
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
        finishAlert();
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
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
                case R.id.back:
                    finishAlert();
                    break;
            }
        }
    };

    private void log_In() {
        String email = ((EditText)findViewById(R.id.loginEmail)).getText().toString();
        String password = ((EditText)findViewById(R.id.loginPass)).getText().toString();


        if(email.length()>0 && password.length()>0) {
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // 로그인 성공
                                FirebaseUser user = mAuth.getCurrentUser();
                                if(user.isEmailVerified()==true) {
                                    startToast("로그인 성공");
                                    startActivity(MainActivity.class);
                                }else {
                                    startToast("이메일 인증을 완료해주세요.");
                                    FirebaseAuth.getInstance().signOut();
                                }
                            } else {
                                //로그인 실패
                                if(task.getException()!=null) {
                                    startToast(task.getException().toString());
                                }
                            }
                        }
                    });
        } else {
            startToast("이메일 또는 비밀번호를 입력해주세요.");
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

    private void finishAlert() {
        AlertDialog.Builder msgBuilder = new AlertDialog.Builder(LoginActivity.this)
                .setTitle("앱 종료")
                .setMessage("앱을 종료하시겠습니까?")
                .setPositiveButton("네", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        moveTaskToBack(true);
                        finishAndRemoveTask();
                        android.os.Process.killProcess(android.os.Process.myPid());
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
//주석으로 암거나 쳐봐