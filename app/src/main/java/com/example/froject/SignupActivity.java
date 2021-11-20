package com.example.froject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.auth.User;
import com.google.firebase.ktx.Firebase;
//kang
import org.jetbrains.annotations.NotNull;

import java.util.Calendar;
import java.util.regex.Pattern;


public class SignupActivity extends AppCompatActivity {
    private static final String TAG = "SignupActivity";
    private FirebaseAuth mAuth;
    private Context mContext = SignupActivity.this;
    private FirebaseFirestore db;
    private EditText etEmail, etPassword, etPasswordCheck;

    FirebaseUser user;

    private boolean is_has_email = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mAuth = FirebaseAuth.getInstance();

        etEmail = findViewById(R.id.setEmail);
        etPassword = findViewById(R.id.setPass);
        etPasswordCheck = findViewById(R.id.passCheck);

        etEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b == false) {
                    check_pass(etEmail.getText().toString());
                }
            }
        });

        etEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b == false) {
                    if (etEmail.getText().equals(etPasswordCheck.getText()))
                        startToast("비밀번호가 일치하지 않습니다");
                }
            }
        });

        findViewById(R.id.nextSignUp).setOnClickListener(onClickListener);
        findViewById(R.id.sendMail).setOnClickListener(onClickListener);
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
            switch (v.getId()) {
                case R.id.nextSignUp:
                    //sign_Up();
                    sign_up2();
                    break;
                case R.id.sendMail:
                    send_email();

                    //send_mail();
                    break;
            }
        }
    };

    /*private void set_date() {
        EditText date = ((EditText)findViewById(R.id.setDate));

        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, R.style.datepicker ,new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                date.setText(year + "년 " + (month+1) + "월 " + dayOfMonth + "일");
            }
        }, year, month, day);

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });
    }*/

    private void send_email() {
        String email = ((EditText) findViewById(R.id.setEmail)).getText().toString();
        if (!check_email(email)) {
            startToast("대학 메일로 가입해주세요\n(ex. abcd@korea.ac.kr / abc@korea.edu");
            return;
        }
        mAuth.createUserWithEmailAndPassword(email,"000000").addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    user = mAuth.getCurrentUser();
                    db = FirebaseFirestore.getInstance();

                    user.sendEmailVerification()
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull @NotNull Task<Void> task) {
                                    if(task.isSuccessful()) {
                                        checkAlert(email);

                                        ((Button)findViewById(R.id.sendMail)).setText("인증확인");

                                        ((Button)findViewById(R.id.sendMail)).setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                user.reload();
                                                if (user.isEmailVerified()) {
                                                    findViewById(R.id.setPass).setVisibility(View.VISIBLE);
                                                    findViewById(R.id.passCheck).setVisibility(View.VISIBLE);
                                                    //background 이상하므로 나중에 수정
                                                    //((Button)findViewById(R.id.sendMail)).setBackgroundColor(R.drawable.box_e0e1ef);
                                                    ((Button)findViewById(R.id.sendMail)).setClickable(false);

                                                    AlertDialog.Builder msgBuilder = new AlertDialog.Builder(SignupActivity.this)
                                                            .setTitle("이메일 인증 완료")
                                                            .setMessage("이메일 인증이 완료되었습니다\n비밀번호를 입력해주세요")
                                                            .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                                                @Override
                                                                public void onClick(DialogInterface dialog, int i) {
                                                                }
                                                            });
                                                    AlertDialog msgDlg = msgBuilder.create();
                                                    msgDlg.show();
                                                    Info info = new Info(email);
                                                    db.collection("users").document(user.getEmail()).set(info);

                                                }
                                                else
                                                    startToast("인증이 완료되지않았습니다.");
                                            }
                                        });

                                    }
                                }
                            });
                } else {
                    if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                        startToast("이메일이 이미 전송되엇습니다");
                    }
                    else if (task.getException() != null) {
                        startToast(task.getException().toString());
                    }
                }
            }
        });
    }


    private void sign_up2() {
        String password = ((EditText) findViewById(R.id.setPass)).getText().toString();
        String checkpass = ((EditText) findViewById(R.id.passCheck)).getText().toString();

        Log.d("123",password+checkpass);

        if(check_pass(password)) { return; }
        else if (!password.equals(checkpass))
            startToast("비밀번호가 일치하지 않습니다");
        else {
            mAuth.confirmPasswordReset(user.getEmail(),password);
            startToast("회원가입이 완료되었습니다.");
            startActivity(UserinfoActivity.class);
        }
    }

    private boolean check_pass(String pass) {
        String pattern = "(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]+";

        if (pass.length() < 6 ) {
            startToast("비밀번호는 6자이상 입력해 주세요.");
            return true;
        }
        else if ( !Pattern.matches(pattern,pass)) {
            startToast("문자와 숫자를 하나이상 사용해주세요.");
            return true;
        }
        return false;
    }

    private void send_mail() {
        String email = ((EditText) findViewById(R.id.setEmail)).getText().toString();
        String password = ((EditText) findViewById(R.id.setPass)).getText().toString();
        String checkpass = ((EditText) findViewById(R.id.passCheck)).getText().toString();

        //이메일 확인 & 인증

        if(email.length()>0 && password.length()>0 && checkpass.length()>0) {
            if(!check_email(email)) {
                startToast("대학메일로 가입해주세요 ac.kr");
            }
            else if (password.length() >= 6) {
                if(password.equals(checkpass)) {
                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(this, (task) -> {
                                if(task.isSuccessful()) {
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                                    Info info = new Info(email);
                                    db.collection("users").document(user.getEmail()).set(info);
                                    user.sendEmailVerification()
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull @NotNull Task<Void> task) {
                                                    if(task.isSuccessful()) {
                                                        checkAlert(email);

                                                    }
                                                }
                                            });
                                } else {
                                    if(task.getException() instanceof FirebaseAuthUserCollisionException) {
                                        startToast("이메일이 이미 전송되엇습니다");
                                    }
                                    else if (task.getException() != null) {
                                        startToast(task.getException().toString());
                                    }
                                }
                            });
                } else {
                    startToast("비밀번호가 일치하지 않습니다.");
                }
            } else {
                startToast("비밀번호는 6자이상 입력해 주세요.");
            }
        } else {
            startToast("입력되지 않은 정보가 있습니다.");
        }
    }
    private void sign_Up() { // 수정필요
        startActivity(LoginActivity.class);
        /*FirebaseUser firebaseUser = mAuth.getCurrentUser();
        if(firebaseUser.isEmailVerified()) {
            startToast("이메일 인증 완료");
            startActivity(UserinfoActivity.class);
        } else {
            startToast("이메일 인증을 완료해주세요.");
        }*/
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
                    }
                });
        AlertDialog msgDlg = msgBuilder.create();
        msgDlg.show();
    }

    private void checkAlert(String email) {
        AlertDialog.Builder msgBuilder = new AlertDialog.Builder(SignupActivity.this)
                .setTitle("이메일 인증")
                .setMessage(email+"\n이메일을 전송했습니다")
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                    }
                });
        AlertDialog msgDlg = msgBuilder.create();
        msgDlg.show();

    }

    private boolean check_email(String email) {
        String pattern = "[0-9a-zA-Z]*\\@[0-9a-zA-Z]*\\.ac\\.kr"; //--@--.ac.kr
        String pattern2 = "[0-9a-zA-Z]*\\@[0-9a-zA-Z]*\\.edu";    //--@--.edu
        boolean regex = Pattern.matches(pattern, email) || Pattern.matches(pattern2,email);

        return regex;
    }
}
