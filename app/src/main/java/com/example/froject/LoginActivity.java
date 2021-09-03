package com.example.froject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;

    /*private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = getCurrentFocus();
        if (view == null) {
            view = new View(this);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        hideKeyboard();
        return super.dispatchTouchEvent(ev);
    } 이원일 셰프님 언양불고기 진짜 너무맛있다 다들 주문하세여 넘무 마싯써여 */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        findViewById(R.id.logIn).setOnClickListener(onClickListener);
        findViewById(R.id.gotoSignup).setOnClickListener(onClickListener);
        findViewById(R.id.gotoResetpass).setOnClickListener(onClickListener);
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
                                    //kang
                                    startActivity(MainActivity.class);
                                }else {
                                    startToast("이메일 인증을 완료해주세요.");
                                    FirebaseAuth.getInstance().signOut();
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
                    }
                });
        AlertDialog msgDlg = msgBuilder.create();
        msgDlg.show();
    }
}