package com.example.froject;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.Calendar;

public class UserinfoActivity extends AppCompatActivity {
    private static final String TAG = "UserinfoActivity";
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    DocumentReference documentReference = db.collection("users").document(user.getEmail());
    String level;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userinfo);

        //findViewById(R.id.back).setOnClickListener(onClickListener);
        findViewById(R.id.checkInfo).setOnClickListener(onClickListener);
        final Spinner levelSpinner = ((Spinner)findViewById(R.id.setLevel));
        ArrayList<String> levels = new ArrayList<>(5);
        for (int i=0;i<5;i++)
            levels.add((i+1)+"학년");
        ArrayAdapter<String> levelAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, levels);
        levelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        levelSpinner.setAdapter(levelAdapter);

        ((Spinner) findViewById(R.id.setLevel)).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               level = levels.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        /*Button mAn = ((Button)findViewById(R.id.man));
        Button woMan = ((Button)findViewById(R.id.woman));*/

        /*mAn.setBackground(getDrawable(R.drawable.borderline));
        mAn.setTextColor(Color.rgb(154, 188, 222));
        woMan.setBackground(getDrawable(R.drawable.borderline));
        woMan.setTextColor(Color.rgb(154, 188, 222));*/

        //set_date();
        /*set_gender(mAn, woMan);
        set_preview(mAn, woMan);*/
    }

    @Override
    public void onBackPressed() {
        backAlert();
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()) {
                /*case R.id.back:
                    backAlert();
                    break;*/
                case R.id.checkInfo:
                    user_info();
                    break;
            }
        }
    };


    private void user_info() {
        String first_name = ((EditText)findViewById(R.id.setName)).getText().toString();
        String last_name = ((EditText)findViewById(R.id.setName2)).getText().toString();
        String number = ((EditText)findViewById(R.id.setNumber)).getText().toString();
        //String date = ((EditText)findViewById(R.id.setDate)).getText().toString();
        String univ = ((EditText)findViewById(R.id.setUniv)).getText().toString();
        //String level = ((EditText)findViewById(R.id.setLevel)).getText().toString();
        String major = ((EditText)findViewById(R.id.setMajor)).getText().toString();
        Info info = new Info(first_name,last_name, number, univ, level, major, user.getEmail());

        Log.w(TAG,"omg"+info.getname());
        Log.w(TAG,"omg"+info.getnumber());
        //Log.w(TAG,"omg"+info.getdate());
        Log.w(TAG,"omg"+info.getuniv());
        Log.w(TAG,"omg"+info.getlevel());
        Log.w(TAG,"omg"+info.getmajor());
        Log.w(TAG,"omg"+info.getClass().toString());

        if(!info.checkNull()) {
            if(user != null) {
                db.collection("users").document(user.getEmail()).set(info, SetOptions.merge())
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                startToast("회원정보를 등록했습니다.");
                                Intent intent = new Intent(UserinfoActivity.this, MainActivity.class);
                                intent.putExtra("my_info", info);
                                intent.putExtra("data","none");
                                startActivity(intent);
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

                    }
                });
        AlertDialog msgDlg = msgBuilder.create();
        msgDlg.show();
    }
}