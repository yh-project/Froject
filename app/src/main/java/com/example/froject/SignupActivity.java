package com.example.froject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.ktx.Firebase;
//kang
import org.jetbrains.annotations.NotNull;

import java.util.Calendar;
import java.util.regex.Pattern;


public class SignupActivity extends AppCompatActivity {
    private static final String TAG = "SignupActivity";
    private FirebaseAuth mAuth;
    String gender = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mAuth = FirebaseAuth.getInstance();

        findViewById(R.id.signUp).setOnClickListener(onClickListener);
        findViewById(R.id.sendMail).setOnClickListener(onClickListener);
        //set_date();
        //set_gender();
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
                case R.id.signUp:
                    sign_Up();
                    break;
                case R.id.sendMail:
                    send_mail();
                    break;
            }
        }
    };

    private void set_date() {
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
    }
    private void set_gender() {
        Button mAn = ((Button)findViewById(R.id.man));
        Button woMan = ((Button)findViewById(R.id.woman));

        mAn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAn.setBackground(getDrawable(R.drawable.borderline_button_5768c3));
                mAn.setTextColor(Color.rgb(255,255,255));
                woMan.setBackground(getDrawable(R.drawable.borderline));
                woMan.setTextColor(Color.rgb(154,188,222));
                gender = "남";
            }
        });
        woMan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                woMan.setBackground(getDrawable(R.drawable.borderline_button_5768c3));
                woMan.setTextColor(Color.rgb(255,255,255));
                mAn.setBackground(getDrawable(R.drawable.borderline));
                mAn.setTextColor(Color.rgb(154,188,222));
                gender = "여";
            }
        });
    }

    private void send_mail() {
        String email = ((EditText) findViewById(R.id.setEmail)).getText().toString();
        String password = ((EditText) findViewById(R.id.setPass)).getText().toString();
        String checkpass = ((EditText) findViewById(R.id.passCheck)).getText().toString();

        if(email.length()>0 && password.length()>0 && checkpass.length()>0) {
            if(!check_email(email)) {
                startToast("대학메일로 가입해주세요 ac..kr");
            }
            else if (password.length() >= 6) {
                if(password.equals(checkpass)) {
                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(this, (task) -> {
                                if(task.isSuccessful()) {
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                                    Info info = new Info("", "", "", "", "", "", "", email);
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
        String pattern2 = "[0-9a-zA-Z]*\\@[0-9a-zA-Z]*\\.edu"; //--@--.edu
        boolean regex = Pattern.matches(pattern, email);

        if (!regex) {
            regex = Pattern.matches(pattern2,email);
        }

        return regex;
    }
}
