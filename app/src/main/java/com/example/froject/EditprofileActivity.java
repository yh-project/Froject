package com.example.froject;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

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

    Info my_info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editprofile);

        Intent intent = getIntent();
        my_info = (Info) intent.getSerializableExtra("my_info");

        findViewById(R.id.newPass).setOnClickListener(onClickListener);
        findViewById(R.id.changeInfo).setOnClickListener(onClickListener);

        TextView originalname = ((TextView)findViewById(R.id.originalName));
        TextView originalmajor = ((TextView)findViewById(R.id.originalMajor));
        TextView originallevel = ((TextView)findViewById(R.id.originalLevel));
        TextView originaluniv = ((TextView)findViewById(R.id.originalUniv));

        originalname.setText(my_info.getname());
        originalmajor.setText(my_info.getmajor());
        originallevel.setText(my_info.getlevel());
        originaluniv.setText(my_info.getuniv());


        //final ArrayList<String> originalinfoList = new ArrayList<>();

        /*docRref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        *//*originalinfoList.add(document.getData().get("name").toString());
                        originalname.setText(originalinfoList.get(0));
                        originalinfoList.add(document.getData().get("major").toString());
                        originalmajor.setText(originalinfoList.get(1));
                        originalinfoList.add(document.getData().get("level").toString());
                        originallevel.setText(originalinfoList.get(2));
                        originalinfoList.add(document.getData().get("univ").toString());
                        originaluniv.setText(originalinfoList.get(3));*//*
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });*/
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()) {
                case R.id.newPass:
                    startActivity(PassresetActivity.class);
                    break;
                case R.id.changeInfo:
                    change_info();
                    break;
            }
        }
    };

    private void change_info() {
        @Nullable String newname = ((EditText)findViewById(R.id.newName)).getText().toString();
        @Nullable String newmajor = ((EditText)findViewById(R.id.newMajor)).getText().toString();
        @Nullable String newlevel = ((EditText)findViewById(R.id.newLevel)).getText().toString();
        @Nullable String newuniv = ((EditText)findViewById(R.id.newUniv)).getText().toString();
        String newlist = " ";


        final ArrayList<String> newinfolist = new ArrayList<String>(Arrays.asList(newname, newmajor, newlevel, newuniv));
        final ArrayList<String> keylist = new ArrayList<String>(Arrays.asList("name", "major", "level", "univ"));

        if(newinfolist.get(0).length() == 0 && newinfolist.get(1).length() == 0 && newinfolist.get(2).length() == 0 && newinfolist.get(3).length() == 0) {
            cancelAlert();
        } else {
            for(int i=0;i<4;i++) {
                if(newinfolist.get(i).length() > 0) { newlist += (newinfolist.get(i) + " "); }
            }

            AlertDialog.Builder msgBuilder = new AlertDialog.Builder(EditprofileActivity.this)
                    .setTitle("???")
                    .setMessage(newlist + "\n이대로 바꿔?")
                    .setPositiveButton("네", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int i) {
                            for(int j=0;j<4;j++){
                                if(newinfolist.get(j).length() > 0){ db.collection("users").document(user.getEmail()).update(keylist.get(j), newinfolist.get(j)); }
                            }
                            if(newinfolist.get(0).length() > 0)
                                my_info.setname(newinfolist.get(0));
                            if(newinfolist.get(1).length() > 0)
                                my_info.setmajor(newinfolist.get(1));
                            if(newinfolist.get(2).length() > 0)
                                my_info.setlevel(newinfolist.get(2));
                            if(newinfolist.get(3).length() > 0)
                                my_info.setuniv(newinfolist.get(3));

                            db.collection("users").document(user.getEmail()).set(my_info);

                            Log.w(TAG, "name: " + my_info.getname());
                            Log.w(TAG, "major: " + my_info.getmajor());
                            Log.w(TAG, "level: " + my_info.getlevel());
                            Log.w(TAG, "univ: " + my_info.getuniv());


                            Intent intent = new Intent(EditprofileActivity.this, MainActivity.class);
                            intent.putExtra("my_info",my_info);
                            intent.putExtra("data","editprofile");
                            Log.w(TAG,"shit"+my_info);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);

                            /*Bundle bundle = new Bundle();
                            bundle.putSerializable("my_info",my_info);
                            Profilefragment profilefragment = new Profilefragment();
                            profilefragment.setArguments(bundle);
                            getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, profilefragment).commit();*/


                            /*Intent intent = new Intent(EditprofileActivity.this, Profilefragment.class);
                            intent.putExtra("my_info",my_info);
                            Log.w(TAG,"shit"+my_info);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);*/

                            //Intent intent = new Intent(EditprofileActivity.this, MainActivity.class);
                            //intent.putExtra("data", "editprofile");
                            //startActivity(intent);
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

    @Override
    public void onBackPressed() {
        cancelAlert();
    }

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
                        Intent intent = new Intent(EditprofileActivity.this, MainActivity.class);
                        intent.putExtra("my_info",my_info);
                        intent.putExtra("data","editprofile");
                        Log.w(TAG,"shit"+my_info);
                        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        startActivity(intent);
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