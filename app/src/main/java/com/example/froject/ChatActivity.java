package com.example.froject;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ChatActivity extends AppCompatActivity {

    private static final String TAG = "ChatActivity";
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    final ArrayList<String> chat = new ArrayList<>();
    Info my_info;
    Intent intent;
    String name;
    String you;
    @Nullable String msg;
    ListView listView;

    ArrayList<MsgData> messageItems = new ArrayList<>();
    MsgAdapter adapter;
    DocumentReference chatRef2;
    CollectionReference youchatRef;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    DocumentReference docRef = db.collection("users").document(user.getEmail());
    CollectionReference chatRef = docRef.collection("Chat");
    DocumentReference chat2Ref = docRef.collection("Chat").document("MyBoard");

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        intent = getIntent();
        if(intent != null) {
            Info my_info = (Info)intent.getSerializableExtra("my_info");
            name = my_info.getname();
            you= intent.getStringExtra("you_email");
            Log.w(TAG,"OMG"+name);
            Log.w(TAG,"omg"+you);
            findViewById(R.id.btn_send).setOnClickListener(onClickListener);
            you="yoha6865@yu.ac.kr";
            chatRef2 = chatRef.document(you);
            DocumentReference youRef =db.collection("users").document(you);
            youchatRef = youRef.collection("Chat");

            chatRef2.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    chatRef2.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull @NotNull Task<DocumentSnapshot> task) {

                            youRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
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
        }

    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()) {
                case R.id.btn_send:
                    msg = ((EditText)findViewById(R.id.et_chatting)).getText().toString();
                    Log.w(TAG,"omg btn_send");
                    Date date = new Date(System.currentTimeMillis());
                    SimpleDateFormat sdate = new SimpleDateFormat("yy-MM-dd hh:mm:ss");
                    String time = sdate.format(date);
                    MsgData msgData = new MsgData(name,msg,time);
                    chatRef2.set(msgData);
                    youchatRef.document(user.getEmail()).set(msgData);
                    break;
            }
        }
    };
}

/*
package com.example.froject;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Chat2Activity extends AppCompatActivity {

    private static final String TAG = "Chat2Activity";

    final ArrayList<String> chat = new ArrayList<>();
    Info my_info, you_info;
    Intent intent;
    String name;
    String you;
    String title="";
    @Nullable
    String msg;

    ArrayList<MsgData> messageItems = new ArrayList<>();
    MsgAdapter adapter;

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    DocumentReference myRef = db.collection("users").document(user.getEmail());
    CollectionReference mychatRef = myRef.collection("Chat");
    DocumentReference youRef;
    CollectionReference youchatRef;

    EditText msg_input;
    Button send_btn;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        if (getIntent() != null) {
            Info my_info = (Info) getIntent().getSerializableExtra("my_info");
            name = my_info.getname();
            you = getIntent().getStringExtra("you_email");
            title = getIntent().getStringExtra("title");
        }

        Log.w(TAG, "OMG" + name);
        Log.w(TAG, "omg" + you);

        youRef = db.collection("users").document(you);
        youchatRef = youRef.collection("Chat");
        //End setting
        //--------------------------------------------------------------

        msg_input=findViewById(R.id.et_chatting);
        send_btn=findViewById(R.id.btn_send);
        send_btn.setOnClickListener(onClickListener);

        //get your Info
        youRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<DocumentSnapshot> task) {
                if (!task.isSuccessful()) {return;}

                you_info = task.getResult().toObject(Info.class);
            }
        });
        //get my Info
        myRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<DocumentSnapshot> task) {
                if (!task.isSuccessful()) {return;}

                my_info = task.getResult().toObject(Info.class);
            }
        });

        if (!title.equals("")) {
            mychatRef.document(you_info.getEmail()).update("title", title);  //set title
            youchatRef.document(my_info.getEmail()).update("title", title);  //set title
        }

    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_send:
                    msg = msg_input.getText().toString();
                    Log.w(TAG, "omg btn_send");
                    Date date = new Date(System.currentTimeMillis());
                    SimpleDateFormat sdate = new SimpleDateFormat("yy-MM-dd hh:mm:ss");
                    String time = sdate.format(date);
                    MsgData msgData = new MsgData(name, msg, date);
                    sendMsg(msg,"0");
                    mychatRef.document(you_info.getEmail()).set(msgData);   //sendMsg
                    youchatRef.document(my_info.getEmail()).set(msgData);   //sendMsg
                    msg_input.setText("");

                    break;
            }
        }
    };

    private void sendMsg(final String msg, String msgtype) {
        send_btn.setEnabled(false);




    }

    //starting

}
 */