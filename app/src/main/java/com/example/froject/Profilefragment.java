package com.example.froject;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class Profilefragment extends Fragment {
    private static final String TAG = "ProfileFragment";
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    final ArrayList<String> userinfoList = new ArrayList<>();
    Info my_info;
    Intent intent;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    DocumentReference docRef = db.collection("users").document(user.getEmail());
    DocumentReference boardRef = docRef.collection("Board").document("MyBoard");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile, container, false);

        //test for doc
        boardRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                boardRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if(document != null) {
                                    Log.d(TAG, "No such document");
                                } else {
                                    Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                                }
                            }
                        else {
                            Log.d(TAG, "get failed with ", task.getException());
                        }
                    }
                });
            }
        });

        if(this.getArguments() != null) {
            my_info = (Info)this.getArguments().getSerializable("my_info");

            TextView username = ((TextView)v.findViewById(R.id.userName));
            TextView usermajor = ((TextView)v.findViewById(R.id.userMajor));
            TextView userlevel = ((TextView)v.findViewById(R.id.userLevel));
            TextView useruniv = ((TextView)v.findViewById(R.id.userUniv));

            username.setText(my_info.getname());
            usermajor.setText(my_info.getmajor());
            userlevel.setText(my_info.getlevel());
            useruniv.setText(my_info.getuniv());
        }

        v.findViewById(R.id.editProfile).setOnClickListener(onClickListener);
        v.findViewById(R.id.logout).setOnClickListener(onClickListener);
        v.findViewById(R.id.gotoLikeList).setOnClickListener(onClickListener);
        v.findViewById(R.id.gotoMyPost).setOnClickListener(onClickListener);
        v.findViewById(R.id.gotoFinishList).setOnClickListener(onClickListener);

        return v;
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()) {
                case R.id.editProfile:
                    intent = new Intent(getActivity(), EditprofileActivity.class);
                    intent.putExtra("my_info", my_info);
                    Log.w(TAG,"ha.."+my_info);
                    startActivity(intent);
                    break;
                case R.id.logout:
                    logoutAlert();
                    break;
                case R.id.gotoLikeList:
                    intent = new Intent(getActivity(), LikeListActivity.class);
                    intent.putExtra("my_info",my_info);
                    startActivity(intent);
                    break;
                case R.id.gotoMyPost:
                    intent = new Intent(getActivity(), MyListActivity.class);
                    intent.putExtra("my_info",my_info);
                    startActivity(intent);
                    break;
                case R.id.gotoFinishList:
                    intent = new Intent(getActivity(),FinishActivity.class);
                    intent.putExtra("my_info",my_info);
                    startActivity(intent);
                    break;
            }
        }
    };

    public void onBackPressed() {
        backAlert();
    }

    private void startToast(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    private void startActivity(Class c) {
        Intent intent = new Intent(getActivity(), c);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    private void backAlert() {
        AlertDialog.Builder msgBuilder = new AlertDialog.Builder(getActivity())
                .setTitle("나가기")
                .setMessage("메인화면으로감")
                .setPositiveButton("네", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        startActivity(MainActivity.class);
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

    private void logoutAlert() {
        AlertDialog.Builder msgBuilder = new AlertDialog.Builder(getActivity())
                .setTitle("로그아웃")
                .setMessage("로그아웃 하시겠습니까?")
                .setPositiveButton("네", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        FirebaseAuth.getInstance().signOut();
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
}