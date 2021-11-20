/*package com.example.froject;

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
}*/

/*
package com.example.froject;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
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
import java.util.List;
import java.util.Map;

public class ChatActivity extends AppCompatActivity {

    private static final String TAG = "ChatActivity";

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

    //
    private RecyclerView recyclerView;
    private RecyclerViewAdapter mAdapter;
    private LinearLayoutManager linearLayoutManager;
    //

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

        findViewById(R.id.et_chatting).setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    View view = getCurrentFocus();
                    if (view == null) {
                        view = new View(ChatActivity.this);
                    }
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            }
        });

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

        recyclerView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v,
                                       int left, int top, int right, int bottom,
                                       int oldLeft, int oldTop, int oldRight, int oldBottom) {
                if (mAdapter!=null & bottom < oldBottom) {
                    final int lastAdapterItem = mAdapter.getItemCount() - 1;
                    recyclerView.post(new Runnable() {
                        @Override
                        public void run() {
                            int recyclerViewPositionOffset = -1000000;
                            View bottomView = linearLayoutManager.findViewByPosition(lastAdapterItem);
                            if (bottomView != null) {
                                recyclerViewPositionOffset = 0 - bottomView.getHeight();
                            }
                            linearLayoutManager.scrollToPositionWithOffset(lastAdapterItem, recyclerViewPositionOffset);
                        }
                    });
                }
            }
        });

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
                    */
/*MsgData msgData = new MsgData(name, msg, date);
                    sendMsg(msg,"0");
                    mychatRef.document(you_info.getEmail()).set(msgData);   //sendMsg
                    youchatRef.document(my_info.getEmail()).set(msgData);   //sendMsg
                    msg_input.setText("");*//*


                    break;
            }
        }
    };

    private void sendMsg(final String msg, String msgtype) {
        send_btn.setEnabled(false);




    }

    //starting



    //End
    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    ////////////////////////////////////

    void setUnread2Read() {
        //if (roomID==null) return;

        mychatRef.document(you_info.getEmail()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (!task.isSuccessful()) {return;}
                DocumentSnapshot document = task.getResult();
                Map<String, Long> users = (Map<String, Long>) document.get("users");

                users.put(my_info.getEmail(), (long) 0);
                document.getReference().update("users", users);
            }
        });
    }

    class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
        List<MsgData> messageList;
        String beforeDay = null;
        MessageViewHolder beforeViewHolder;

        RecyclerViewAdapter() {
            */
/*File dir = new File(rootPath);
            if (!dir.exists()) {
                if (!Util9.isPermissionGranted(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    return;
                }
                dir.mkdirs();
            }*//*


            messageList = new ArrayList<MsgData>();
            setUnread2Read();
            startListening();
        }

        public void startListening() {
            beforeDay = null;
            messageList.clear();

            CollectionReference roomRef = mychatRef.document(you_info.getEmail()).collection("messages");
            // my chatting room information
            listenerRegistration = roomRef.orderBy("timestamp").addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot documentSnapshots, @Nullable FirebaseFirestoreException e) {
                    if (e != null) {return;}

                    MsgData message;
                    for (DocumentChange change : documentSnapshots.getDocumentChanges()) {
                        switch (change.getType()) {
                            case ADDED:
                                message = change.getDocument().toObject(Message.class);
                                //if (message.msg !=null & message.timestamp == null) {continue;} // FieldValue.serverTimestamp is so late

                                if (message.getReadUsers().indexOf(myUid) == -1) {
                                    message.getReadUsers().add(myUid);
                                    change.getDocument().getReference().update("readUsers", message.getReadUsers());
                                }
                                messageList.add(message);
                                notifyItemInserted(change.getNewIndex());
                                break;
                            case MODIFIED:
                                message = change.getDocument().toObject(Message.class);
                                messageList.set(change.getOldIndex(), message);
                                notifyItemChanged(change.getOldIndex());
                                break;
                            case REMOVED:
                                messageList.remove(change.getOldIndex());
                                notifyItemRemoved(change.getOldIndex());
                                break;
                        }
                    }
                    recyclerView.scrollToPosition(messageList.size() - 1);
                }
            });
        }

        public void stopListening() {
            if (listenerRegistration != null) {
                listenerRegistration.remove();
                listenerRegistration = null;
            }

            messageList.clear();
            notifyDataSetChanged();
        }

        @Override
        public int getItemViewType(int position) {
            MsgData message = messageList.get(position);
            if (myUid.equals(message.getUid()) ) {
                switch(message.getMsgtype()){
                    case "1": return R.layout.item_chatimage_right;
                    case "2": return R.layout.item_chatfile_right;
                    default:  return R.layout.item_chatmsg_right;
                }
            } else {
                switch(message.getMsgtype()){
                    case "1": return R.layout.item_chatimage_left;
                    case "2": return R.layout.item_chatfile_left;
                    default:  return R.layout.item_chatmsg_left;
                }
            }
        }
        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = null;
            view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
            return new MessageViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            final MessageViewHolder messageViewHolder = (MessageViewHolder) holder;
            final MsgData message = messageList.get(position);

            setReadCounter(message, messageViewHolder.read_counter);

            if ("0".equals(message.getMsgtype())) {                                      // text message
                messageViewHolder.msg_item.setText(message.getMsg());
            } else
            if ("2".equals(message.getMsgtype())) {                                      // file transfer
                messageViewHolder.msg_item.setText(message.getFilename() + "\n" + message.getFilesize());
                messageViewHolder.filename = message.getFilename();
                messageViewHolder.realname = message.getMsg();
                File file = new File(rootPath + message.getFilename());
                if(file.exists()) {
                    messageViewHolder.button_item.setText("Open File");
                } else {
                    messageViewHolder.button_item.setText("Download");
                }
            } else {                                                                // image transfer
                messageViewHolder.realname = message.getMsg();
                Glide.with(getContext())
                        .load(storageReference.child("filesmall/"+message.getMsg()))
                        .apply(new RequestOptions().override(1000, 1000))
                        .into(messageViewHolder.img_item);
            }

            if (! myUid.equals(message.getUid())) {
                UserModel userModel = userList.get(message.getUid());
                messageViewHolder.msg_name.setText(userModel.getUsernm());

                if (userModel.getUserphoto()==null) {
                    Glide.with(getContext()).load(R.drawable.user)
                            .apply(requestOptions)
                            .into(messageViewHolder.user_photo);
                } else{
                    Glide.with(getContext())
                            .load(storageReference.child("userPhoto/"+userModel.getUserphoto()))
                            .apply(requestOptions)
                            .into(messageViewHolder.user_photo);
                }
            }
            messageViewHolder.divider.setVisibility(View.INVISIBLE);
            messageViewHolder.divider.getLayoutParams().height = 0;
            messageViewHolder.timestamp.setText("");
            if (message.getTimestamp()==null) {return;}

            String day = dateFormatDay.format( message.getTimestamp());
            String timestamp = dateFormatHour.format( message.getTimestamp());
            messageViewHolder.timestamp.setText(timestamp);

            if (position==0) {
                messageViewHolder.divider_date.setText(day);
                messageViewHolder.divider.setVisibility(View.VISIBLE);
                messageViewHolder.divider.getLayoutParams().height = 60;
            } else {
                Message beforeMsg = messageList.get(position - 1);
                String beforeDay = dateFormatDay.format( beforeMsg.getTimestamp() );

                if (!day.equals(beforeDay) && beforeDay != null) {
                    messageViewHolder.divider_date.setText(day);
                    messageViewHolder.divider.setVisibility(View.VISIBLE);
                    messageViewHolder.divider.getLayoutParams().height = 60;
                }
            }
            */
/*messageViewHolder.timestamp.setText("");
            if (message.getTimestamp()==null) {return;}

            String day = dateFormatDay.format( message.getTimestamp());
            String timestamp = dateFormatHour.format( message.getTimestamp());

            messageViewHolder.timestamp.setText(timestamp);

            if (position==0) {
                messageViewHolder.divider_date.setText(day);
                messageViewHolder.divider.setVisibility(View.VISIBLE);
                messageViewHolder.divider.getLayoutParams().height = 60;
            };
            if (!day.equals(beforeDay) && beforeDay!=null) {
                beforeViewHolder.divider_date.setText(beforeDay);
                beforeViewHolder.divider.setVisibility(View.VISIBLE);
                beforeViewHolder.divider.getLayoutParams().height = 60;
            }
            beforeViewHolder = messageViewHolder;
            beforeDay = day;*//*

        }

        void setReadCounter (Message message, final TextView textView) {
            int cnt = userCount - message.getReadUsers().size();
            if (cnt > 0) {
                textView.setVisibility(View.VISIBLE);
                textView.setText(String.valueOf(cnt));
            } else {
                textView.setVisibility(View.INVISIBLE);
            }
        }

        @Override
        public int getItemCount() {
            return messageList.size();
        }


    }

    private class MessageViewHolder extends RecyclerView.ViewHolder {
        public ImageView user_photo;
        public TextView msg_item;
        public ImageView img_item;          // only item_chatimage_
        public TextView msg_name;
        public TextView timestamp;
        public TextView read_counter;
        public LinearLayout divider;
        public TextView divider_date;
        public TextView button_item;            // only item_chatfile_
        public LinearLayout msgLine_item;       // only item_chatfile_
        public String filename;
        public String realname;

        public MessageViewHolder(View view) {
            super(view);
            user_photo = view.findViewById(R.id.user_photo);
            msg_item = view.findViewById(R.id.msg_item);
            img_item = view.findViewById(R.id.img_item);
            timestamp = view.findViewById(R.id.timestamp);
            msg_name = view.findViewById(R.id.msg_name);
            read_counter = view.findViewById(R.id.read_counter);
            divider = view.findViewById(R.id.divider);
            divider_date = view.findViewById(R.id.divider_date);
            button_item = view.findViewById(R.id.button_item);
            msgLine_item = view.findViewById(R.id.msgLine_item);        // for file
            if (msgLine_item!=null) {
                msgLine_item.setOnClickListener(downloadClickListener);
            }
            if (img_item!=null) {                                       // for image
                img_item.setOnClickListener(imageClickListener);
            }
        }
        // file download and open
        Button.OnClickListener downloadClickListener = new View.OnClickListener() {
            public void onClick(View view) {
                if ("Download".equals(button_item.getText())) {
                    download();
                } else {
                    openWith();
                }
            }
            public void download() {
                if (!Util9.isPermissionGranted(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    return ;
                }
                showProgressDialog("Downloading File.");

                final File localFile = new File(rootPath, filename);

                storageReference.child("files/"+realname).getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                        button_item.setText("Open File");
                        hideProgressDialog();
                        Log.e("DirectTalk9 ","local file created " +localFile.toString());
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Log.e("DirectTalk9 ","local file not created  " +exception.toString());
                    }
                });
            }

            public void openWith() {
                File newFile = new File(rootPath + filename);
                MimeTypeMap mime = MimeTypeMap.getSingleton();
                String ext = newFile.getName().substring(newFile.getName().lastIndexOf(".") + 1);
                String type = mime.getMimeTypeFromExtension(ext);

                Intent intent = new Intent(Intent.ACTION_VIEW);
                Uri uri;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    uri = FileProvider.getUriForFile(getContext(), getActivity().getPackageName() + ".provider", newFile);

                    List<ResolveInfo> resInfoList = getActivity().getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
                    for (ResolveInfo resolveInfo : resInfoList) {
                        String packageName = resolveInfo.activityInfo.packageName;
                        getActivity().grantUriPermission(packageName, uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    }
                }else {
                    uri = Uri.fromFile(newFile);
                }
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.setDataAndType(uri, type);//"application/vnd.android.package-archive");
                startActivity(Intent.createChooser(intent, "Your title"));
            }
        };
        // photo view
        Button.OnClickListener imageClickListener = new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ViewPagerActivity.class);
                intent.putExtra("roomID", roomID);
                intent.putExtra("realname", realname);
                startActivity(intent);
            }
        };
    }
}
*/

package com.example.froject;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
//import android.support.v4.content.FileProvider;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/*import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;*/
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.firestore.WriteBatch;
/*import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;*/
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

/*import gujc.directtalk9.R;
import gujc.directtalk9.common.Util9;
import gujc.directtalk9.model.ChatModel;
import gujc.directtalk9.model.Message;
import gujc.directtalk9.model.NotificationModel;
import gujc.directtalk9.model.UserModel;
import gujc.directtalk9.photoview.ViewPagerActivity;*/
/*import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;*/

import static android.app.Activity.RESULT_OK;

public class ChatActivity extends AppCompatActivity {

    private static final int PICK_FROM_ALBUM = 1;
    private static final int PICK_FROM_FILE = 2;
    //private static String rootPath = Util9.getRootPath()+"/DirectTalk9/";

    private ImageButton sendBtn;
    private EditText msg_input;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter mAdapter;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private SimpleDateFormat dateFormatDay = new SimpleDateFormat("yyyy-MM-dd");
    private SimpleDateFormat dateFormatHour = new SimpleDateFormat("aa hh:mm");
    private String roomID;
    private String myUid;
    private String toUid;
    private Map<String, Info> userList = new HashMap<>();

    private ListenerRegistration listenerRegistration;
    private FirebaseFirestore firestore=FirebaseFirestore.getInstance();
    //private StorageReference storageReference;
    private LinearLayoutManager linearLayoutManager;

    private ProgressDialog progressDialog = null;
    private Integer userCount = 0;
    private String title;
    private String rName;

    /*public static final ChatFragment getInstance(String toUid, String roomID) {
        ChatFragment f = new ChatFragment();
        Bundle bdl = new Bundle();
        bdl.putString("toUid", toUid);
        bdl.putString("roomID", roomID);
        f.setArguments(bdl);
        return f;
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        title = getIntent().getStringExtra("title");
        rName = getIntent().getStringExtra("rName");
        TextView tv = findViewById(R.id.opponentName);
        tv.setText(rName);

        recyclerView = findViewById(R.id.rv_list);
        linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        msg_input = findViewById(R.id.et_chatting);
        sendBtn = findViewById(R.id.btn_send);
        sendBtn.setOnClickListener(sendBtnClickListener);

        //findViewById(R.id.imageBtn).setOnClickListener(imageBtnClickListener);
        //findViewById(R.id.fileBtn).setOnClickListener(fileBtnClickListener);
        findViewById(R.id.et_chatting).setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                    View view = getCurrentFocus();
                    if (view == null) {
                        view = new View(ChatActivity.this);
                    }
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            }
        });

        if (getIntent() != null) {
            roomID = getIntent().getStringExtra("roomID");
            toUid = getIntent().getStringExtra("you_email");
        }

        firestore = FirebaseFirestore.getInstance();
        //storageReference  = FirebaseStorage.getInstance().getReference();

        dateFormatDay.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));
        dateFormatHour.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));

        myUid = FirebaseAuth.getInstance().getCurrentUser().getEmail();

        /*
         two user: roomid or uid talking
         multi user: roomid
         */
        if (!"".equals(toUid) && toUid!=null) {                     // find existing room for two user
            findChatRoom(toUid);
        } else
        if (!"".equals(roomID) && roomID!=null) { // existing room (multi user)
            setChatRoom(roomID);
        };

        if (roomID==null) {                                                     // new room for two user
            getUserInfoFromServer(myUid);
            getUserInfoFromServer(toUid);

            userCount = 2;
        };

        recyclerView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v,
                                       int left, int top, int right, int bottom,
                                       int oldLeft, int oldTop, int oldRight, int oldBottom) {
                if (mAdapter!=null & bottom < oldBottom) {
                    final int lastAdapterItem = mAdapter.getItemCount() - 1;
                    recyclerView.post(new Runnable() {
                        @Override
                        public void run() {
                            int recyclerViewPositionOffset = -1000000;
                            View bottomView = linearLayoutManager.findViewByPosition(lastAdapterItem);
                            if (bottomView != null) {
                                recyclerViewPositionOffset = 0 - bottomView.getHeight();
                            }
                            linearLayoutManager.scrollToPositionWithOffset(lastAdapterItem, recyclerViewPositionOffset);
                        }
                    });
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mAdapter != null) {
            mAdapter.stopListening();
        }
    }

    // get a user info
    void getUserInfoFromServer(String id){
        firestore.collection("users").document(id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Info userModel = documentSnapshot.toObject(Info.class);
                userList.put(userModel.getEmail(), userModel);
                if (roomID != null & userCount == userList.size()) {
                    mAdapter = new RecyclerViewAdapter();
                    recyclerView.setAdapter(mAdapter);
                }
            }
        });
    }

    // Returns the room ID after locating the chatting room with the user ID.
    void findChatRoom(final String toUid){
        Log.w("omg","findchatroom"+myUid+toUid);
        String me = myUid.replace(".","\\.");
        me = me.replace("@","\\@");
        Log.w("omg",me);
        /*firestore.collection("rooms").where.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
                if (!task.isSuccessful()) {Log.w("omg","fail find"); return;}
                Log.w("omg",task.getResult().getDocuments().size()+"");
                //task.getResult().getDocuments().get(0).get("users");
                //Map<String,Integer> a = (Map<String, Integer>) task.getResult().getDocuments().get(0).get("users");
                //Log.w("om","shit"+a.containsKey(myUid));
            }
        });*/
        firestore.collection("rooms").whereArrayContains("users2", myUid).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (!task.isSuccessful()) {Log.w("omg","fail find"); return;}
                        Log.w("omg",task.getResult().getDocuments().size()+"");
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Log.w("omg","find room");
                            Map<String, Long> users = (Map<String, Long>) document.get("users");
                            if (users.size()==2 & users.get(toUid)!=null){
                                setChatRoom(document.getId());
                                break;
                            }
                        }
                    }
                });
    }

    // get user list in a chatting room
    void setChatRoom(String rid) {
        Log.w("omg","setchatroom + "+rid);
        roomID = rid;
        firestore.collection("rooms").document(roomID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (!task.isSuccessful()) {return;}
                DocumentSnapshot document = task.getResult();
                Map<String, Long> users = (Map<String, Long>) document.get("users");

                for( String key : users.keySet() ){
                    getUserInfoFromServer(key);
                }
                userCount = users.size();
                users.put(myUid, (long) 0);
                document.getReference().update("users", users);
                if (getIntent() != null)
                    document.getReference().update("title",title);
            }
        });
    }

    void setUnread2Read() {
        if (roomID==null) return;

        firestore.collection("rooms").document(roomID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (!task.isSuccessful()) {return;}
                DocumentSnapshot document = task.getResult();
                Map<String, Long> users = (Map<String, Long>) document.get("users");

                users.put(myUid, (long) 0);
                document.getReference().update("users", users);
            }
        });
    }

    public void CreateChattingRoom(final DocumentReference room) {
        Log.w("omg","createroom");
        Map<String, Integer> users = new HashMap<>();
        String title = "";
        for( String key : userList.keySet() ){
            users.put(key, 0);
        }
        Map<String, Object> data = new HashMap<>();
        ArrayList<String> users2 = new ArrayList<>();
        for( String key : users.keySet() ){
            users2.add(key);
        }
        //data.put("title", null);
        data.put("users", users);
        data.put("users2",users2);


        room.set(data).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    mAdapter = new RecyclerViewAdapter();
                    recyclerView.setAdapter(mAdapter);
                }
            }
        });
    }
    public Map<String, Info> getUserList() {
        return userList;
    }

    Button.OnClickListener sendBtnClickListener = new View.OnClickListener() {
        public void onClick(View view) {
            String msg = msg_input.getText().toString();
            sendMessage(msg, "0");
            msg_input.setText("");
        }
    };

    private void sendMessage(final String msg, String msgtype) {
        sendBtn.setEnabled(false);

        if (roomID==null) {             // create chatting room for two user
            roomID = firestore.collection("rooms").document().getId();
            CreateChattingRoom( firestore.collection("rooms").document(roomID) );
        }

        final Map<String,Object> messages = new HashMap<>();
        messages.put("uid", myUid);
        messages.put("msg", msg);
        messages.put("msgtype", msgtype);
        messages.put("timestamp", FieldValue.serverTimestamp());

        final DocumentReference docRef = firestore.collection("rooms").document(roomID);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (!task.isSuccessful()) {return;}

                WriteBatch batch = firestore.batch();

                // save last message
                batch.set(docRef, messages, SetOptions.merge());

                // save message
                List<String> readUsers = new ArrayList();
                readUsers.add(myUid);
                messages.put("readUsers", readUsers);//new String[]{myUid} );
                batch.set(docRef.collection("messages").document(), messages);

                // inc unread message count
                DocumentSnapshot document = task.getResult();
                Map<String, Long> users = (Map<String, Long>) document.get("users");

                for( String key : users.keySet() ){
                    if (!myUid.equals(key)) users.put(key, users.get(key)+1);
                }
                document.getReference().update("users", users);
                if (getIntent() != null)
                    document.getReference().update("title",title);

                batch.commit().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            //sendGCM();
                            sendBtn.setEnabled(true);
                        }
                    }
                });
            }

        });
    };

    /*void sendGCM(){
        Gson gson = new Gson();
        NotificationModel notificationModel = new NotificationModel();
        notificationModel.notification.title = userList.get(myUid).getUsernm();
        notificationModel.notification.body = msg_input.getText().toString();
        notificationModel.data.title = userList.get(myUid).getUsernm();
        notificationModel.data.body = msg_input.getText().toString();

        for ( Map.Entry<String, UserModel> elem : userList.entrySet() ){
            if (myUid.equals(elem.getValue().getUid())) continue;
            notificationModel.to = elem.getValue().getToken();
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf8"), gson.toJson(notificationModel));
            Request request = new Request.Builder()
                    .header("Content-Type", "application/json")
                    .addHeader("Authorization", "key=")
                    .url("https://fcm.googleapis.com/fcm/send")
                    .post(requestBody)
                    .build();

            OkHttpClient okHttpClient = new OkHttpClient();
            okHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {                }
                @Override
                public void onResponse(Call call, Response response) throws IOException {                }
            });
        }
    }*/

    /*// choose image
    Button.OnClickListener imageBtnClickListener = new View.OnClickListener() {
        public void onClick(final View view) {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
            startActivityForResult(intent, PICK_FROM_ALBUM);
        }
    };*/

    // choose file
    //Button.OnClickListener fileBtnClickListener = new View.OnClickListener() {
    //    public void onClick(final View view) {
    //        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
    //        //intent.setType("*/*");
    //        intent.setAction(Intent.ACTION_GET_CONTENT);
    //       startActivityForResult(Intent.createChooser(intent, "Select File"), PICK_FROM_FILE);
    //    }
    //};

    /*// uploading image / file
    @Override
    public void onActivityResult(final int requestCode, int resultCode, Intent data) {
        if (resultCode!= RESULT_OK) { return;}
        Uri fileUri = data.getData();
        final String filename = Util9.getUniqueValue();

        showProgressDialog("Uploading selected File.");
        final ChatModel.FileInfo fileinfo  = getFileDetailFromUri(getContext(), fileUri);

        storageReference.child("files/"+filename).putFile(fileUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                sendMessage(filename, Integer.toString(requestCode), fileinfo);
                hideProgressDialog();
            }
        });
        if (requestCode != PICK_FROM_ALBUM) { return;}

        // small image
        Glide.with(getContext())
                .asBitmap()
                .load(fileUri)
                .apply(new RequestOptions().override(150, 150))
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap bitmap, Transition<? super Bitmap> transition) {
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                        byte[] data = baos.toByteArray();
                        storageReference.child("filesmall/"+filename).putBytes(data);
                    }
                });
    }

    // get file name and size from Uri
    public static ChatModel.FileInfo getFileDetailFromUri(final Context context, final Uri uri) {
        if (uri == null) { return null; }

        ChatModel.FileInfo fileDetail = new ChatModel.FileInfo();
        // File Scheme.
        if (ContentResolver.SCHEME_FILE.equals(uri.getScheme())) {
            File file = new File(uri.getPath());
            fileDetail.filename = file.getName();
            fileDetail.filesize = Util9.size2String(file.length());
        }
        // Content Scheme.
        else if (ContentResolver.SCHEME_CONTENT.equals(uri.getScheme())) {
            Cursor returnCursor =
                    context.getContentResolver().query(uri, null, null, null, null);
            if (returnCursor != null && returnCursor.moveToFirst()) {
                int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                int sizeIndex = returnCursor.getColumnIndex(OpenableColumns.SIZE);
                fileDetail.filename = returnCursor.getString(nameIndex);
                fileDetail.filesize = Util9.size2String(returnCursor.getLong(sizeIndex));
                returnCursor.close();
            }
        }

        return fileDetail;
    }*/

    public void showProgressDialog(String title ) {
        if (progressDialog==null) {
            progressDialog = new ProgressDialog(this);
        }
        progressDialog.setIndeterminate(true);
        progressDialog.setTitle(title);
        progressDialog.setMessage("Please wait..");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }
    public void setProgressDialog(int value) {
        progressDialog.setProgress(value);
    }
    public void hideProgressDialog() {
        progressDialog.dismiss();
    }
    // =======================================================================================

    class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
        //final private RequestOptions requestOptions = new RequestOptions().transforms(new CenterCrop(), new RoundedCorners(90));

        List<MsgData> messageList;
        String beforeDay = null;
        MessageViewHolder beforeViewHolder;

        RecyclerViewAdapter() {
            /*File dir = new File(rootPath);
            if (!dir.exists()) {
                if (!Util9.isPermissionGranted(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    return;
                }
                dir.mkdirs();
            }*/

            messageList = new ArrayList<MsgData>();
            setUnread2Read();
            startListening();
        }

        public void startListening() {
            beforeDay = null;
            messageList.clear();

            CollectionReference roomRef = firestore.collection("rooms").document(roomID).collection("messages");
            // my chatting room information
            listenerRegistration = roomRef.orderBy("timestamp").addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot documentSnapshots, @Nullable FirebaseFirestoreException e) {
                    if (e != null) {return;}

                    MsgData message;
                    for (DocumentChange change : documentSnapshots.getDocumentChanges()) {
                        switch (change.getType()) {
                            case ADDED:
                                message = change.getDocument().toObject(MsgData.class);
                                //if (message.msg !=null & message.timestamp == null) {continue;} // FieldValue.serverTimestamp is so late

                                if (message.getReadUsers().indexOf(myUid) == -1) {
                                    message.getReadUsers().add(myUid);
                                    change.getDocument().getReference().update("readUsers", message.getReadUsers());
                                }
                                messageList.add(message);
                                notifyItemInserted(change.getNewIndex());
                                break;
                            case MODIFIED:
                                message = change.getDocument().toObject(MsgData.class);
                                messageList.set(change.getOldIndex(), message);
                                notifyItemChanged(change.getOldIndex());
                                break;
                            case REMOVED:
                                messageList.remove(change.getOldIndex());
                                notifyItemRemoved(change.getOldIndex());
                                break;
                        }
                    }
                    recyclerView.scrollToPosition(messageList.size() - 1);
                }
            });
        }

        public void stopListening() {
            if (listenerRegistration != null) {
                listenerRegistration.remove();
                listenerRegistration = null;
            }

            messageList.clear();
            notifyDataSetChanged();
        }

        @Override
        public int getItemViewType(int position) {
            MsgData message = messageList.get(position);
            if (myUid.equals(message.getUid()) ) {
                switch(message.getMsgtype()){
                    //case "1": return R.layout.item_chatimage_right;
                    //case "2": return R.layout.item_chatfile_right;
                    default:  return R.layout.item_mymsgbox;
                }
            } else {
                switch(message.getMsgtype()){
                    //case "1": return R.layout.item_chatimage_left;
                    //case "2": return R.layout.item_chatfile_left;
                    default:  return R.layout.item_othermsgobx;
                }
            }
        }
        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = null;
            view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
            return new MessageViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            final MessageViewHolder messageViewHolder = (MessageViewHolder) holder;
            final MsgData message = messageList.get(position);

            setReadCounter(message, messageViewHolder.read_counter);

            if ("0".equals(message.getMsgtype())) {                                      // text message
                messageViewHolder.msg_item.setText(message.getMsg());
            } /*else
            if ("2".equals(message.getMsgtype())) {                                      // file transfer
                messageViewHolder.msg_item.setText(message.getFilename() + "\n" + message.getFilesize());
                messageViewHolder.filename = message.getFilename();
                messageViewHolder.realname = message.getMsg();
                File file = new File(rootPath + message.getFilename());
                if(file.exists()) {
                    messageViewHolder.button_item.setText("Open File");
                } else {
                    messageViewHolder.button_item.setText("Download");
                }
            } else {                                                                // image transfer
                messageViewHolder.realname = message.getMsg();
                Glide.with(getContext())
                        .load(storageReference.child("filesmall/"+message.getMsg()))
                        .apply(new RequestOptions().override(1000, 1000))
                        .into(messageViewHolder.img_item);
            }*/         //image and file

            if (!myUid.equals(message.getUid())) {
                Info userModel = userList.get(message.getUid());
                messageViewHolder.msg_name.setText(userModel.getname());

                /*if (userModel.getUserphoto()==null) {
                    Glide.with(getContext()).load(R.drawable.user)
                            .apply(requestOptions)
                            .into(messageViewHolder.user_photo);
                } else{
                    Glide.with(getContext())
                            .load(storageReference.child("userPhoto/"+userModel.getUserphoto()))
                            .apply(requestOptions)
                            .into(messageViewHolder.user_photo);
                }*/
            }
            messageViewHolder.divider.setVisibility(View.INVISIBLE);
            messageViewHolder.divider.getLayoutParams().height = 0;
            messageViewHolder.timestamp.setText("");
            if (message.getTimestamp()==null) {return;}

            String day = dateFormatDay.format( message.getTimestamp());
            String timestamp = dateFormatHour.format( message.getTimestamp());
            messageViewHolder.timestamp.setText(timestamp);

            if (position==0) {
                messageViewHolder.divider_date.setText(day);
                messageViewHolder.divider.setVisibility(View.VISIBLE);
                messageViewHolder.divider.getLayoutParams().height = 60;
            } else {
                MsgData beforeMsg = messageList.get(position - 1);
                String beforeDay = dateFormatDay.format( beforeMsg.getTimestamp() );

                if (!day.equals(beforeDay) && beforeDay != null) {
                    messageViewHolder.divider_date.setText(day);
                    messageViewHolder.divider.setVisibility(View.VISIBLE);
                    messageViewHolder.divider.getLayoutParams().height = 60;
                }
            }
            /*messageViewHolder.timestamp.setText("");
            if (message.getTimestamp()==null) {return;}

            String day = dateFormatDay.format( message.getTimestamp());
            String timestamp = dateFormatHour.format( message.getTimestamp());

            messageViewHolder.timestamp.setText(timestamp);

            if (position==0) {
                messageViewHolder.divider_date.setText(day);
                messageViewHolder.divider.setVisibility(View.VISIBLE);
                messageViewHolder.divider.getLayoutParams().height = 60;
            };
            if (!day.equals(beforeDay) && beforeDay!=null) {
                beforeViewHolder.divider_date.setText(beforeDay);
                beforeViewHolder.divider.setVisibility(View.VISIBLE);
                beforeViewHolder.divider.getLayoutParams().height = 60;
            }
            beforeViewHolder = messageViewHolder;
            beforeDay = day;*/
        }

        void setReadCounter (MsgData message, final TextView textView) {
            int cnt = userCount - message.getReadUsers().size();
            if (cnt > 0) {
                textView.setVisibility(View.VISIBLE);
                textView.setText(String.valueOf(cnt));
            } else {
                textView.setVisibility(View.INVISIBLE);
            }
        }

        @Override
        public int getItemCount() {
            return messageList.size();
        }


    }

    private class MessageViewHolder extends RecyclerView.ViewHolder {
        //public ImageView user_photo;
        public TextView msg_item;
        //public ImageView img_item;          // only item_chatimage_
        public TextView msg_name;
        public TextView timestamp;
        public TextView read_counter;
        public LinearLayout divider;
        public TextView divider_date;
        public TextView button_item;            // only item_chatfile_
        public LinearLayout msgLine_item;       // only item_chatfile_
        //public String filename;
        //public String realname;

        public MessageViewHolder(View view) {
            super(view);
            //user_photo = view.findViewById(R.id.user_photo);
            msg_item = view.findViewById(R.id.msg_item);
            //img_item = view.findViewById(R.id.img_item);
            timestamp = view.findViewById(R.id.timestamp);
            msg_name = view.findViewById(R.id.msg_name);
            read_counter = view.findViewById(R.id.read_counter);
            divider = view.findViewById(R.id.divider);
            divider_date = view.findViewById(R.id.divider_date);
            //button_item = view.findViewById(R.id.button_item);
            /*msgLine_item = view.findViewById(R.id.msgLine_item);        // for file
            if (msgLine_item!=null) {
                msgLine_item.setOnClickListener(downloadClickListener);
            }
            if (img_item!=null) {                                       // for image
                img_item.setOnClickListener(imageClickListener);
            }*/
        }
        // file download and open
        /*Button.OnClickListener downloadClickListener = new View.OnClickListener() {
            public void onClick(View view) {
                if ("Download".equals(button_item.getText())) {
                    download();
                } else {
                    openWith();
                }
            }
            public void download() {
                if (!Util9.isPermissionGranted(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    return ;
                }
                showProgressDialog("Downloading File.");

                final File localFile = new File(rootPath, filename);

                storageReference.child("files/"+realname).getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                        button_item.setText("Open File");
                        hideProgressDialog();
                        Log.e("DirectTalk9 ","local file created " +localFile.toString());
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Log.e("DirectTalk9 ","local file not created  " +exception.toString());
                    }
                });
            }

            public void openWith() {
                File newFile = new File(rootPath + filename);
                MimeTypeMap mime = MimeTypeMap.getSingleton();
                String ext = newFile.getName().substring(newFile.getName().lastIndexOf(".") + 1);
                String type = mime.getMimeTypeFromExtension(ext);

                Intent intent = new Intent(Intent.ACTION_VIEW);
                Uri uri;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    uri = FileProvider.getUriForFile(getContext(), getActivity().getPackageName() + ".provider", newFile);

                    List<ResolveInfo> resInfoList = getActivity().getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
                    for (ResolveInfo resolveInfo : resInfoList) {
                        String packageName = resolveInfo.activityInfo.packageName;
                        getActivity().grantUriPermission(packageName, uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    }
                }else {
                    uri = Uri.fromFile(newFile);
                }
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.setDataAndType(uri, type);//"application/vnd.android.package-archive");
                startActivity(Intent.createChooser(intent, "Your title"));
            }
        };
        // photo view
        Button.OnClickListener imageClickListener = new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ViewPagerActivity.class);
                intent.putExtra("roomID", roomID);
                intent.putExtra("realname", realname);
                startActivity(intent);
            }
        };*/
    }

    public void backPressed() {
    }
}
