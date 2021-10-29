package com.example.froject;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Arrays;

public class EditprofileActivity extends AppCompatActivity {
    private static final String TAG = "EditprofileActivity";
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    DocumentReference docRref = db.collection("users").document(user.getEmail());
    ArrayList<String> list;

    Info my_info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editprofile);

        Intent intent = getIntent();
        my_info = (Info) intent.getSerializableExtra("my_info");

        findViewById(R.id.changeInfo).setOnClickListener(onClickListener);

        ArrayList<String> levels = new ArrayList<>(5);
        for (int i=0;i<5;i++)
            levels.add((i+1)+"학년");
        list = new ArrayList<>(4);
        list.add(my_info.getFirst_name());
        list.add(my_info.getLast_name());
        list.add(my_info.getmajor());
        list.add(my_info.getuniv());

        TextView originalFirst = ((TextView)findViewById(R.id.firstName));
        TextView originalLast = ((TextView)findViewById(R.id.lastName));
        TextView originalmajor = ((TextView)findViewById(R.id.newMajor));
        final Spinner levelSpinner = ((Spinner)findViewById(R.id.newLevel));
        TextView originaluniv = ((TextView)findViewById(R.id.newUniv));

        // 49~57 editprofile 화면 수정하면서 기존꺼 주석처리
        ((TextView) findViewById(R.id.firstName)).addTextChangedListener(watcher);
        ((TextView) findViewById(R.id.lastName)).addTextChangedListener(watcher);
        ((TextView) findViewById(R.id.newMajor)).addTextChangedListener(watcher);
        ((TextView) findViewById(R.id.newUniv)).addTextChangedListener(watcher);

        ((Spinner) findViewById(R.id.newLevel)).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!my_info.getlevel().equals(levels.get(position))) {
                    ((Button) findViewById(R.id.changeInfo)).setBackgroundResource(R.drawable.button_33529f);
                    ((Button) findViewById(R.id.changeInfo)).setTextColor(Color.rgb(255,255,255));
                    ((Button) findViewById(R.id.changeInfo)).setClickable(true);
                }
                else {
                    ((Button) findViewById(R.id.changeInfo)).setBackgroundResource(R.drawable.button_e0e1ef);
                    ((Button) findViewById(R.id.changeInfo)).setTextColor(Color.rgb(114,114,114));
                    ((Button) findViewById(R.id.changeInfo)).setClickable(false);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                ((Button)findViewById(R.id.changeInfo)).setBackgroundResource(R.drawable.button_e0e1ef);
            }
        });


        ArrayAdapter<String> levelAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, levels);
        levelAdapter.setDropDownViewResource(R.layout.spinner_item);
        levelSpinner.setAdapter(levelAdapter);

        originalFirst.setText(my_info.getFirst_name());
        originalLast.setText(my_info.getLast_name());
        originalmajor.setText(my_info.getmajor());
        for (int i = 0; i < levels.size(); i++) {
            if (my_info.getlevel().equals(levels.get(i))) {
                levelSpinner.setSelection(i);
                break;
            }
        }
        originaluniv.setText(my_info.getuniv());

        final ArrayList<String> originalinfoList = new ArrayList<>();

        docRref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        originalinfoList.add(document.getData().get("first_name").toString());
                        originalFirst.setText(originalinfoList.get(0));
                        originalinfoList.add(document.getData().get("last_name").toString());
                        originalLast.setText(originalinfoList.get(1));
                        originalinfoList.add(document.getData().get("major").toString());
                        originalmajor.setText(originalinfoList.get(2));
                        originalinfoList.add(document.getData().get("level").toString());
                        for (int i = 0; i < levels.size(); i++) {
                            if (originalinfoList.get(3).equals(levels.get(i))) {
                                levelSpinner.setSelection(i);
                                break;
                            }
                        }
                        originalinfoList.add(document.getData().get("univ").toString());
                        originaluniv.setText(originalinfoList.get(4));
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()) {
                case R.id.changeInfo:
                    change_info();
                    break;
            }
        }
    };

    View.OnFocusChangeListener onFocusChangeListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (!hasFocus) {
                switch (v.getId()) {

                }
            }
        }
    };

    TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            for(int i=0;i<4;i++) {
                if (!list.get(i).equals(s.toString())) {
                    ((Button) findViewById(R.id.changeInfo)).setBackgroundResource(R.drawable.button_33529f);
                    ((Button) findViewById(R.id.changeInfo)).setTextColor(Color.rgb(255,255,255));
                    ((Button) findViewById(R.id.changeInfo)).setClickable(true);
                }
                else {
                    ((Button) findViewById(R.id.changeInfo)).setBackgroundResource(R.drawable.button_e0e1ef);
                    ((Button) findViewById(R.id.changeInfo)).setTextColor(Color.rgb(114,114,114));
                    ((Button) findViewById(R.id.changeInfo)).setClickable(false);
                }
            }
        }
    };


    private void change_info() {
        @Nullable String newFirstName = ((EditText)findViewById(R.id.firstName)).getText().toString();
        @Nullable String newLastName = ((EditText)findViewById(R.id.lastName)).getText().toString();
        @Nullable String newmajor = ((EditText)findViewById(R.id.newMajor)).getText().toString();
        @Nullable String newlevel = ((Spinner)findViewById(R.id.newLevel)).getSelectedItem().toString();
        @Nullable String newuniv = ((EditText)findViewById(R.id.newUniv)).getText().toString();

        final ArrayList<String> newinfolist = new ArrayList<String>(Arrays.asList(newFirstName,newLastName, newmajor, newlevel, newuniv));
        final ArrayList<String> keylist = new ArrayList<String>(Arrays.asList("first_name","last_name", "major", "level", "univ"));

        if(newinfolist.get(0).length() == 0 && newinfolist.get(1).length() == 0 && newinfolist.get(2).length() == 0 && newinfolist.get(3).length() == 0 && newinfolist.get(4).length() == 0)  {
            //cancelAlert();
            finish();
        }
        else {
            AlertDialog.Builder msgBuilder = new AlertDialog.Builder(EditprofileActivity.this)
                    .setTitle("프로필수정")
                    .setMessage("수정하시겠습니까?")
                    .setPositiveButton("네", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int i) {
                            if(newinfolist.get(0).length()>0) { my_info.setFirst_name(newinfolist.get(0)); my_info.setname(newinfolist.get(0)+newinfolist.get(1)); }
                            if(newinfolist.get(1).length()>0) { my_info.setLast_name(newinfolist.get(1)); my_info.setname(newinfolist.get(0)+newinfolist.get(1)); }
                            if(newinfolist.get(2).length()>0) { my_info.setmajor(newinfolist.get(2)); }
                            if(newinfolist.get(3).length()>0) { my_info.setlevel(newinfolist.get(3)); }
                            if(newinfolist.get(4).length()>0) { my_info.setuniv(newinfolist.get(4)); }

                            db.collection("users").document(user.getEmail()).set(my_info);

                            //need fix
                            Intent intent = getIntent();
                            intent.setClass(EditprofileActivity.this,MainActivity.class);
                            //Intent intent = new Intent(EditprofileActivity.this, MainActivity.class);
                            intent.putExtra("my_info", my_info);
                            intent.putExtra("data", "editprofile");
                            Log.w(TAG, "shit"+my_info);
                            Log.w(TAG,"Document "+intent.getComponent());
                            //intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
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

    /*@Override
    public void onBackPressed() {
        cancelAlert();
    }*/

    private void startToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    private void startActivity(Class c) {
        Intent intent = new Intent(this, c);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    private void cancelAlert() {
        AlertDialog.Builder msgBuilder = new AlertDialog.Builder(EditprofileActivity.this)
                .setTitle("???")
                .setMessage("프로필화면으로감")
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