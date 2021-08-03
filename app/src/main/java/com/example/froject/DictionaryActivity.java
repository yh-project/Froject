package com.example.froject;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class DictionaryActivity extends AppCompatActivity {
    ArrayAdapter<CharSequence> Bigadapter, Smalladapter;
    String choice_Big = "";
    String choice_Small = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionary);
        final Spinner Bigspinner = (Spinner) findViewById(R.id.bigcategory);
        final Spinner Smallspinner = (Spinner) findViewById(R.id.smallcategory);
        Bigadapter = ArrayAdapter.createFromResource(this, R.array.Bigcategory, android.R.layout.simple_spinner_dropdown_item);
        Bigadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Bigspinner.setAdapter(Bigadapter);
        Bigspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (Bigadapter.getItem(position).equals("디자인")) {
                    choice_Big = "디자인";
                    Smalladapter = ArrayAdapter.createFromResource(DictionaryActivity.this, R.array.Designcategory, android.R.layout.simple_spinner_dropdown_item);
                    Smalladapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    Smallspinner.setAdapter(Smalladapter);
                    Smallspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            choice_Small = Smalladapter.getItem(position).toString();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                } else if (Bigadapter.getItem(position).equals("개발")) {
                    choice_Big = "개발";
                    Smalladapter = ArrayAdapter.createFromResource(DictionaryActivity.this, R.array.Developcategory, android.R.layout.simple_spinner_dropdown_item);
                    Smalladapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    Smallspinner.setAdapter(Smalladapter);
                    Smallspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            choice_Small = Smalladapter.getItem(position).toString();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                }
                if (Bigadapter.getItem(position).equals("사진·영상")) {
                    choice_Big = "사진·영상";
                    Smalladapter = ArrayAdapter.createFromResource(DictionaryActivity.this, R.array.Photocategory, android.R.layout.simple_spinner_dropdown_item);
                    Smalladapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    Smallspinner.setAdapter(Smalladapter);
                    Smallspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            choice_Small = Smalladapter.getItem(position).toString();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                }
                if (Bigadapter.getItem(position).equals("번역·통역")) {
                    choice_Big = "번역·통역";
                    Smalladapter = ArrayAdapter.createFromResource(DictionaryActivity.this, R.array.Translatecategory, android.R.layout.simple_spinner_dropdown_item);
                    Smalladapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    Smallspinner.setAdapter(Smalladapter);
                    Smallspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            choice_Small = Smalladapter.getItem(position).toString();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                }
                if (Bigadapter.getItem(position).equals("기획")) {
                    choice_Big = "기획";
                    Smalladapter = ArrayAdapter.createFromResource(DictionaryActivity.this, R.array.Plancategory, android.R.layout.simple_spinner_dropdown_item);
                    Smalladapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    Smallspinner.setAdapter(Smalladapter);
                    Smallspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            choice_Small = Smalladapter.getItem(position).toString();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                }
                if (Bigadapter.getItem(position).equals("인테리어")) {
                    choice_Big = "인테리어";
                    Smalladapter = ArrayAdapter.createFromResource(DictionaryActivity.this, R.array.Interiorlcategory, android.R.layout.simple_spinner_dropdown_item);
                    Smalladapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    Smallspinner.setAdapter(Smalladapter);
                    Smallspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            choice_Small = Smalladapter.getItem(position).toString();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                }
                if (Bigadapter.getItem(position).equals("대외활동")) {
                    choice_Big = "대외활동";
                    Smalladapter = ArrayAdapter.createFromResource(DictionaryActivity.this, R.array.Extracategory, android.R.layout.simple_spinner_dropdown_item);
                    Smalladapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    Smallspinner.setAdapter(Smalladapter);
                    Smallspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            choice_Small = Smalladapter.getItem(position).toString();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(DictionaryActivity.this, choice_Big + "=" + choice_Small, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
