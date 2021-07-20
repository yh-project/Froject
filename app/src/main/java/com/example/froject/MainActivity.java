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
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;

    Info my_info = new Info();

    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        Log.w(TAG,"shit main "+(intent.getSerializableExtra("my_info") != null));
        if (intent.getSerializableExtra("my_info") != null) {
            my_info = (Info) intent.getSerializableExtra("my_info");
            Log.w(TAG,"shit get main"+intent.getSerializableExtra("my_info"));
        }


        bottomNavigationView = findViewById(R.id.bottomNavi);


        Intent i = getIntent();
        @Nullable String data = i.getStringExtra("data");
        if(data == null) { data = "none"; }

        switch(data) {
            case "none":
                getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, new Boardfragment()).commit();
                break;
            case "editprofile":
                Bundle bundle = new Bundle();
                bundle.putSerializable("my_info",my_info);
                Profilefragment profilefragment = new Profilefragment();
                profilefragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, profilefragment).commit();
                break;
        }


        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.item_fragment1:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, new Boardfragment()).commit();
                        break;
                    case R.id.item_fragment2:
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("my_info",my_info);
                        Profilefragment profilefragment = new Profilefragment();
                        profilefragment.setArguments(bundle);
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, profilefragment).commit();
                        Log.w(TAG,"shit"+my_info);
                        break;
                    case R.id.item_writeactivity:
                        startActivity(WriteActivity.class);
                        break;
                }
                return true;
            }
        });

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if(user == null) {
            startActivity(LoginActivity.class);
        }else{
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            DocumentReference docRef = db.collection("users").document(user.getUid());
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if(document != null) {
                            if (document.exists()) {
                                Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                                my_info.setname(document.getData().get("name").toString());
                                my_info.setmajor(document.getData().get("major").toString());
                                my_info.setlevel(document.getData().get("level").toString());
                                my_info.setuniv(document.getData().get("univ").toString());
                            } else {
                                Log.d(TAG, "No such document");
                                startActivity(UserinfoActivity.class);
                            }
                        }
                    } else {
                        Log.d(TAG, "get failed with ", task.getException());
                    }
                }
            });
        }
    }

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
                        startToast("시발 그럼 왜눌러 개새끼야.");
                    }
                });
        AlertDialog msgDlg = msgBuilder.create();
        msgDlg.show();
    }
}

//응애