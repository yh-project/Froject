package com.example.froject.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.froject.activity.EditprofileActivity;
import com.example.froject.datafile.Info;
import com.example.froject.activity.LoginActivity;
import com.example.froject.activity.MainActivity;
import com.example.froject.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class Profilefragment extends Fragment {
    private static final String TAG = "ProfileFragment";
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    final ArrayList<String> userinfoList = new ArrayList<>();
    Info my_info;
    Intent intent;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    DocumentReference docRref = db.collection("users").document(user.getEmail());

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile, container, false);

        //Log.w(TAG,"shit"+getActivity().getIntent().toString());

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

        return v;
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()) {
                case R.id.editProfile:
                    intent = new Intent(getActivity(), EditprofileActivity.class);
                    intent.putExtra("my_info", my_info);
                    Log.w(TAG,"shit"+my_info);
                    startActivity(intent);
                    break;
                case R.id.logout:
                    FirebaseAuth.getInstance().signOut();
                    startActivity(LoginActivity.class);
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
                        startToast("시발 그럼 왜눌러 개새끼야.");
                    }
                });
        AlertDialog msgDlg = msgBuilder.create();
        msgDlg.show();
    }
}