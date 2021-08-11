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
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    Info my_info = new Info();

    Categoryfragment categoryfragment;
    Boardfragment boardfragment;
    Profilefragment profilefragment;
    Homefragment homefragment;
    ChatlistFragment chatlistFragment;

    private static final String TAG = "MainActivity";
    private static final String PROFILE_TAG = "ProfileFragment";
    private static final String BOARD_TAG = "BoardFragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.w(TAG,"PRINT: "+savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.bottomNavi);

        //Start = check Login State
        if(user == null) { //state == Logout -> goto LoginActivity
            startActivity(LoginActivity.class);
        }else{ //state = Login -> get DB for firebase
            categoryfragment = new Categoryfragment();
            addFragment(categoryfragment);
            /*boardfragment = new Boardfragment();
            addFragment(boardfragment);*/
            DocumentReference docRef = db.collection("users").document(user.getEmail());
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document != null) {
                            my_info = document.toObject(Info.class);
                            if (my_info.checkNull()) { //Need fix
                                Log.d(TAG, "No such document");
                                startActivity(UserinfoActivity.class);
                            } else {
                                Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                            }
                        }
                    } else {
                        Log.d(TAG, "get failed with ", task.getException());
                    }
                }
            });
        }
        //End = check Login state

    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        Log.w(TAG,"Document "+intent);
        Log.w(TAG,"Document "+intent.getSerializableExtra("my_info"));
        Log.w(TAG,"Document "+intent.getComponent());
        Log.w(TAG,"Document "+intent.getStringExtra("data"));

        //Start = get info for past activity
        if(intent.getSerializableExtra("my_info") != null) {
            my_info = (Info) intent.getSerializableExtra("my_info");
            Log.w(TAG, "shit get main" + intent.getSerializableExtra("my_info"));
        }

        @Nullable String data = intent.getStringExtra("data");
        //End = get info for past Activity



        //Case : Back to MainActivity

        if (data!=null) {
            switch (data) {
                case "none":
                    showFragment(categoryfragment);
                    //showFragment(boardfragment);
                    break;
                case "editprofile":
                    if (profilefragment == null) {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("my_info", my_info);
                        profilefragment = new Profilefragment();
                        profilefragment.setArguments(bundle);
                        addFragment(profilefragment);
                    } else {
                        showFragment(profilefragment);
                    }
                    break;
                case "board":
                    boardfragment = new Boardfragment();
                    addFragment(boardfragment);
                    break;
            }
        }

        //Case : Select Button
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    /*case R.id.item_fragment1:
                        if(boardfragment == null) {
                            boardfragment = new Boardfragment();
                            addFragment(boardfragment);
                        }
                        else {
                            showFragment(boardfragment);
                        }
                        break;*/
                    case R.id.item_fragment1:
                        if (categoryfragment == null) {
                            categoryfragment = new Categoryfragment();
                            addFragment(categoryfragment);
                        } else {
                            showFragment(categoryfragment);
                        }
                        break;

                    case R.id.item_fragment2:
                        if(profilefragment == null) {
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("my_info", my_info);
                            profilefragment = new Profilefragment();
                            profilefragment.setArguments(bundle);
                            addFragment(profilefragment);
                        }
                        else {
                            showFragment(profilefragment);
                        }
                        Log.w(TAG, "shit" + my_info);
                        break;
                    case R.id.item_writeactivity:
                        //startActivity(WriteActivity.class);   //need fix

                        //임시    need fix
                        if(chatlistFragment == null) {
                            chatlistFragment = new ChatlistFragment();
                            addFragment(chatlistFragment);
                        }
                        else {
                            showFragment(chatlistFragment);
                        }
                        break;
                        /*if(chatFragment == null) {
                            chatFragment = new ChatFragment();
                            addFragment(chatFragment);
                        }
                        else {
                            showFragment(chatFragment);
                        }
                        break;*/
                    case R.id.item_fragment3:
                        if(homefragment == null) {
                            homefragment = new Homefragment();
                            addFragment(homefragment);
                        }
                        else {
                            showFragment(homefragment);
                        }
                        break;
                }
                return true;
            }
        });
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
                        startToast("취소되었습니다.");
                    }
                });
        AlertDialog msgDlg = msgBuilder.create();
        msgDlg.show();
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, fragment);
        fragmentTransaction.commit();
    }

    private void addFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        List<Fragment> List = fragmentManager.getFragments();
        int listsize = List.size();
        Log.w(TAG, "fragment data : " + listsize + List.toString());

        for(int i=0;i<listsize;i++) {
            fragmentTransaction.hide(List.get(i));
        }
        fragmentTransaction.add(R.id.main_frame, fragment);
        fragmentTransaction.commit();
    }

    private void showFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        List<Fragment> List = fragmentManager.getFragments();
        int listsize = List.size();
        Log.w(TAG, "fragment data : "+listsize+List.toString());

        for(int i=0;i<listsize;i++) {
            if(!(fragment.equals(List.get(i)))) {
                fragmentTransaction.hide(List.get(i));
            }
        }
        fragmentTransaction.show(fragment);
        fragmentTransaction.commit();
    }

}





//응애
//오늘 저녁 막창임 개부럽제