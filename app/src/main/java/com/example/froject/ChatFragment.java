package com.example.froject;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class ChatFragment extends Fragment {

    private static final String TAG = "ChatFragment";
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    final ArrayList<String> chat = new ArrayList<>();
    Info my_info;
    Intent intent;
    String name;
    String you;
    String msg;
    ListView listView;

    ArrayList<MsgData> messageItems = new ArrayList<>();
    MsgAdapter adapter;
    DocumentReference chatRef2;
    DocumentReference youchatRef;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null) {
            Info my_info = (Info)getArguments().getSerializable("my_info");
            name = my_info.getname();
            you= getArguments().getString("you_email");
            Log.w(TAG,"OMG"+name);
            Log.w(TAG,"omg"+you);
        }

    }

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    DocumentReference docRef = db.collection("users").document(user.getEmail());
    CollectionReference chatRef = docRef.collection("Chat");
    DocumentReference chat2Ref = docRef.collection("Chat").document("MyBoard");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_chat,container,false);

        v.findViewById(R.id.btn_send).setOnClickListener(onClickListener);
        msg = v.findViewById(R.id.et_chatting).toString();
        you="yoha6865@yu.ac.kr";
        chatRef2 = chatRef.document(you);
        youchatRef =db.collection("users").document(you);
        chatRef2.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                chatRef2.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<DocumentSnapshot> task) {
                        Log.w(TAG,"omg"+chatRef2.get().toString());

                        youchatRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull @NotNull Task<DocumentSnapshot> task) {
                                if(task.isSuccessful()) {
                                    name = task.getResult().get("name").toString();
                                }
                            }
                        });

                    }
                });
            }
        });

        return v;
    }
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()) {
                case R.id.btn_send:
                    Log.w(TAG,"omg btn_send");
                    Date date = new Date(System.currentTimeMillis());
                    SimpleDateFormat sdate = new SimpleDateFormat("yy-MM-dd hh:mm");
                    String time = sdate.format(date);
                    MsgData msgData = new MsgData(name,msg,time);
                    chatRef2.set(msgData);
                    youchatRef.set(msgData);
                    break;
            }
        }
    };
}