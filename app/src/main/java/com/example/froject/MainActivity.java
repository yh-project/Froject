package com.example.froject;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    Info my_info = new Info();

    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.bottomNavi);

        Intent intent = getIntent();

        //Start = check Login state
        if (user == null) {     ///state == Logout -> goto LoginActivity
            startActivity(LoginActivity.class);
        } else {  ///state == Login -> get DB for firebase
            DocumentReference docRef = db.collection("users").document(user.getEmail());
            docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    my_info = documentSnapshot.toObject(Info.class);
                    docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (document != null) {
                                    if (!document.exists()) {   //Need fix
                                        Log.d(TAG, "No such document");
                                        startActivity(UserinfoActivity.class);
                                    } else {
                                        Log.d(TAG,"DocumentSnapshot data: "+document.getData());
                                    }
                                }
                            } else {
                                Log.d(TAG, "get failed with ", task.getException());
                            }
                        }
                    });
                }
            });
        }
        //End = check Login state

        //Case : Back to MainActivity
        @Nullable String data = intent.getStringExtra("data");
        if (data == null) {
            data = "none";
        }

        switch (data) {
            case "none":
                getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, new Boardfragment()).commit();
                break;
            case "editprofile":
                //Start = put Info for next Activity
                Bundle bundle = new Bundle();
                bundle.putSerializable("my_info", my_info);
                Profilefragment profilefragment = new Profilefragment();
                profilefragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, profilefragment).commit();
                //End = put Info for next Activity
                break;
        }

        //Case : Select Button
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.item_fragment1:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, new Boardfragment()).commit();
                        break;
                    case R.id.item_fragment2:
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("my_info", my_info);
                        Profilefragment profilefragment = new Profilefragment();
                        profilefragment.setArguments(bundle);
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, profilefragment).commit();
                        Log.w(TAG, "shit" + my_info);
                        break;
                    case R.id.item_writeactivity:
                        startActivity(WriteActivity.class);
                        break;
                }
                return true;
            }
        });

    }

    /*@Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        //Start = get Info for past Activity
        Log.w(TAG, "shit main " + (intent.getSerializableExtra("my_info") != null));
        if (intent.getSerializableExtra("my_info") != null) {
            my_info = (Info) intent.getSerializableExtra("my_info");
            Log.w(TAG, "shit get main" + intent.getSerializableExtra("my_info"));
        }
        //End = get Info for past Activity

        *//*@Nullable String data = intent.getStringExtra("data");
        if (data == null) {
            data = "none";
        }

        switch (data) {
            case "none":
                getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, new Boardfragment()).commit();
                break;
            case "editprofile":
                //Start = put Info for next Activity
                Bundle bundle = new Bundle();
                bundle.putSerializable("my_info", my_info);
                Profilefragment profilefragment = new Profilefragment();
                profilefragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, profilefragment).commit();
                //End = put Info for next Activity
                break;
        }*//*

    }*/

    @Override
    public void onBackPressed() {
        finishAlert();
    }

    private void startToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    private void startActivity(Class c) {
        Intent intent = new Intent(this, c);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    private void finishAlert() {
        AlertDialog.Builder msgBuilder = new AlertDialog.Builder(MainActivity.this)
                .setTitle("앱 종료")
                .setMessage("앱을 종료하시겠습니까?")
                .setPositiveButton("네", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        moveTaskToBack(true);
                        finishAndRemoveTask();
                        android.os.Process.killProcess(android.os.Process.myPid());
                    }
                })
                .setNegativeButton("아니요", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        startToast("취소되었습니다.");     //past version : 시발 그럼 왜눌러 개새끼야
                    }
                });
        AlertDialog msgDlg = msgBuilder.create();
        msgDlg.show();
    }
}

//응애