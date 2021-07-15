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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Map;

public class EditprofileActivity extends AppCompatActivity {
    private static final String TAG = "EditprofileActivity";
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    DocumentReference docRref = db.collection("users").document(user.getUid());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editprofile);

        findViewById(R.id.newPass).setOnClickListener(onClickListener);
        findViewById(R.id.changeInfo).setOnClickListener(onClickListener);


        TextView originalname = ((TextView)findViewById(R.id.originalName));
        TextView originalmajor = ((TextView)findViewById(R.id.originalMajor));
        TextView originallevel = ((TextView)findViewById(R.id.originalLevel));
        TextView originaluniv = ((TextView)findViewById(R.id.originalUniv));

        final ArrayList<String> originalinfoList = new ArrayList<>();

        docRref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        originalinfoList.add(document.getData().get("name").toString());
                        originalname.setText(originalinfoList.get(0));
                        originalinfoList.add(document.getData().get("major").toString());
                        originalmajor.setText(originalinfoList.get(1));
                        originalinfoList.add(document.getData().get("level").toString());
                        originallevel.setText(originalinfoList.get(2));
                        originalinfoList.add(document.getData().get("univ").toString());
                        originaluniv.setText(originalinfoList.get(3));
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

        if(newname.length() == 0 && newmajor.length() == 0 && newlevel.length() == 0 && newuniv.length() ==0) {
            checkAlert();
        } else {
            db.collection("users").document(user.getUid())
                    .update(
                            "name", newname,
                            "major", newmajor,
                            "level", newlevel,
                            "univ", newuniv
                    );
        }
    }

    @Override
    public void onBackPressed() {
        backAlert();
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
        AlertDialog.Builder msgBuilder = new AlertDialog.Builder(EditprofileActivity.this)
                .setTitle("나가기")
                .setMessage("프로필화면으로감")
                .setPositiveButton("네", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        startActivity(ProfileActivity.class);
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

    private void checkAlert() {
        AlertDialog.Builder msgBuilder = new AlertDialog.Builder(EditprofileActivity.this)
                .setTitle("나가기")
                .setMessage("수정안함?")
                .setPositiveButton("네", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        startActivity(ProfileActivity.class);
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