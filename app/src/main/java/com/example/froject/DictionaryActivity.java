package com.example.froject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;


public class DictionaryActivity extends AppCompatActivity {
    ArrayAdapter<CharSequence> Bigadapter, Smalladapter;
    String choice_Big = "";
    String choice_Small = "";
    TextView explainCategory, relativeMajor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionary);

        String[] bigcategory = getResources().getStringArray(R.array.Bigcategory);
        String[] Designcategory = getResources().getStringArray(R.array.Designcategory);//7 7
        String[] Developcategory = getResources().getStringArray(R.array.Developcategory);//6 13
        String[] Photocategory = getResources().getStringArray(R.array.Photocategory);//12 25
        String[] Translatecategory = getResources().getStringArray(R.array.Translatecategory);//5 30
        String[] Plancategory = getResources().getStringArray(R.array.Plancategory);//1 31
        String[] Interiorlcategory = getResources().getStringArray(R.array.Interiorlcategory);//1 32

        String[][] array = new String[6][];
        array[0]=(Designcategory);
        array[1]=(Developcategory);
        array[2]=(Photocategory);
        array[3]=(Translatecategory);
        array[4]=(Plancategory);
        array[5]=(Interiorlcategory);

        Intent intent = getIntent();
        String contact_title = intent.getStringExtra("contact_title");
        explainCategory = findViewById(R.id.explainCategory);
        relativeMajor = findViewById(R.id.relativeMajor);
        String contact_big = intent.getStringExtra("contact_big");
        String contact_small = intent.getStringExtra("contact_small");
        int i;

        final Spinner Bigspinner = (Spinner) findViewById(R.id.bigcategory);
        final Spinner Smallspinner = (Spinner) findViewById(R.id.smallcategory);
        Bigadapter = ArrayAdapter.createFromResource(this, R.array.Bigcategory, android.R.layout.simple_spinner_dropdown_item);
        Bigadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Bigspinner.setAdapter(Bigadapter);

        for (i = 0; i < bigcategory.length; i++) {
            if (contact_big.equals(bigcategory[i])) {
                Bigspinner.setSelection(i);
                break;
            }
        }
        final int big = i;

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
                            if (Smalladapter.getItem(position).equals("로고·브랜딩")) {
                                explainCategory.setText("로고·브랜딩에 대한 설명입니다.");
                                relativeMajor.setText("로고·브랜딩에 관련 학과입니다.");
                            } else if (Smalladapter.getItem(position).equals("인쇄물")) {
                                explainCategory.setText("인쇄물에 대한 설명입니다.");
                                relativeMajor.setText("인쇄물에 관련 학과입니다.");
                            } else if (Smalladapter.getItem(position).equals("웹디자인")) {
                                explainCategory.setText("웹디자인에 대한 설명입니다.");
                                relativeMajor.setText("웹디자인에 관련 학과입니다.");
                            } else if (Smalladapter.getItem(position).equals("UX·UI")) {
                                explainCategory.setText("UX·UI에 대한 설명입니다.");
                                relativeMajor.setText("UX·UI에 관련 학과입니다.");
                            } else if (Smalladapter.getItem(position).equals("PPT·인포")) {
                                explainCategory.setText("PPT·인포에 대한 설명입니다.");
                                relativeMajor.setText("PPT·인포에 관련 학과입니다.");
                            } else if (Smalladapter.getItem(position).equals("일러스트")) {
                                explainCategory.setText("일러스트에 대한 설명입니다.");
                                relativeMajor.setText("일러스트에 관련 학과입니다.");
                            } else if (Smalladapter.getItem(position).equals("3D")) {
                                explainCategory.setText("3D에 대한 설명입니다.");
                                relativeMajor.setText("3D에 관련 학과입니다.");
                            }
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
                            if (Smalladapter.getItem(position).equals("모바일")) {
                                explainCategory.setText("모바일에 대한 설명입니다.");
                                relativeMajor.setText("모바일에 관련 학과입니다.");
                            } else if (Smalladapter.getItem(position).equals("웹")) {
                                explainCategory.setText("웹에 대한 설명입니다.");
                                relativeMajor.setText("웹에 관련 학과입니다.");
                            } else if (Smalladapter.getItem(position).equals("AI")) {
                                explainCategory.setText("AI에 대한 설명입니다.");
                                relativeMajor.setText("AI에 관련 학과입니다.");
                            } else if (Smalladapter.getItem(position).equals("빅데이터")) {
                                explainCategory.setText("빅데이터에 대한 설명입니다.");
                                relativeMajor.setText("빅데이터에 관련 학과입니다.");
                            } else if (Smalladapter.getItem(position).equals("임베디드")) {
                                explainCategory.setText("임베디드에 대한 설명입니다.");
                                relativeMajor.setText("임베디드에 관련 학과입니다.");
                            } else if (Smalladapter.getItem(position).equals("엑셀·메크로")) {
                                explainCategory.setText("엑셀·메크로에 대한 설명입니다.");
                                relativeMajor.setText("엑셀·메크로에 관련 학과입니다.");
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                } else if (Bigadapter.getItem(position).equals("사진·영상")) {
                    choice_Big = "사진·영상";
                    Smalladapter = ArrayAdapter.createFromResource(DictionaryActivity.this, R.array.Photocategory, android.R.layout.simple_spinner_dropdown_item);
                    Smalladapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    Smallspinner.setAdapter(Smalladapter);
                    Smallspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            choice_Small = Smalladapter.getItem(position).toString();
                            if (Smalladapter.getItem(position).equals("다큐")) {
                                explainCategory.setText("다큐에 대한 설명입니다.");
                                relativeMajor.setText("다큐에 관련 학과입니다.");
                            } else if (Smalladapter.getItem(position).equals("초상")) {
                                explainCategory.setText("초상에 대한 설명입니다.");
                                relativeMajor.setText("초상에 관련 학과입니다.");
                            } else if (Smalladapter.getItem(position).equals("풍경")) {
                                explainCategory.setText("풍경에 대한 설명입니다.");
                                relativeMajor.setText("풍경에 관련 학과입니다.");
                            } else if (Smalladapter.getItem(position).equals("상업")) {
                                explainCategory.setText("상업에 대한 설명입니다.");
                                relativeMajor.setText("상업에 관련 학과입니다.");
                            } else if (Smalladapter.getItem(position).equals("스냅")) {
                                explainCategory.setText("스냅에 대한 설명입니다.");
                                relativeMajor.setText("스냅에 관련 학과입니다.");
                            } else if (Smalladapter.getItem(position).equals("광고")) {
                                explainCategory.setText("광고에 대한 설명입니다.");
                                relativeMajor.setText("광고에 관련 학과입니다.");
                            } else if (Smalladapter.getItem(position).equals("패션")) {
                                explainCategory.setText("패션에 대한 설명입니다.");
                                relativeMajor.setText("패션에 관련 학과입니다.");
                            } else if (Smalladapter.getItem(position).equals("뮤직비디오")) {
                                explainCategory.setText("뮤직비디오에 대한 설명입니다.");
                                relativeMajor.setText("뮤직비디오에 관련 학과입니다.");
                            } else if (Smalladapter.getItem(position).equals("성우")) {
                                explainCategory.setText("성우에 대한 설명입니다.");
                                relativeMajor.setText("성우에 관련 학과입니다.");
                            } else if (Smalladapter.getItem(position).equals("모델")) {
                                explainCategory.setText("모델에 대한 설명입니다.");
                                relativeMajor.setText("모델에 관련 학과입니다.");
                            } else if (Smalladapter.getItem(position).equals("영상편집")) {
                                explainCategory.setText("영상편집에 대한 설명입니다.");
                                relativeMajor.setText("영상편집에 관련 학과입니다.");
                            } else if (Smalladapter.getItem(position).equals("모션그래픽")) {
                                explainCategory.setText("모션그래픽에 대한 설명입니다.");
                                relativeMajor.setText("모션그래픽에 관련 학과입니다.");
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                } else if (Bigadapter.getItem(position).equals("번역·통역")) {
                    choice_Big = "번역·통역";
                    Smalladapter = ArrayAdapter.createFromResource(DictionaryActivity.this, R.array.Translatecategory, android.R.layout.simple_spinner_dropdown_item);
                    Smalladapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    Smallspinner.setAdapter(Smalladapter);
                    Smallspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            choice_Small = Smalladapter.getItem(position).toString();
                            if (Smalladapter.getItem(position).equals("영어")) {
                                explainCategory.setText("영어에 대한 설명입니다.");
                                relativeMajor.setText("영어에 관련 학과입니다.");
                            } else if (Smalladapter.getItem(position).equals("중국어")) {
                                explainCategory.setText("중국어에 대한 설명입니다.");
                                relativeMajor.setText("중국어에 관련 학과입니다.");
                            } else if (Smalladapter.getItem(position).equals("일본어")) {
                                explainCategory.setText("일본어에 대한 설명입니다.");
                                relativeMajor.setText("일본어에 관련 학과입니다.");
                            } else if (Smalladapter.getItem(position).equals("스페인어")) {
                                explainCategory.setText("스페인어에 대한 설명입니다.");
                                relativeMajor.setText("스페인어에 관련 학과입니다.");
                            } else if (Smalladapter.getItem(position).equals("프랑스어")) {
                                explainCategory.setText("프랑스어에 대한 설명입니다.");
                                relativeMajor.setText("프랑스어에 관련 학과입니다.");
                            } else if (Smalladapter.getItem(position).equals("기타")) {
                                explainCategory.setText("기타에 대한 설명입니다.");
                                relativeMajor.setText("기타에 관련 학과입니다.");
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                } else if (Bigadapter.getItem(position).equals("기획")) {
                    choice_Big = "기획";
                    Smalladapter = ArrayAdapter.createFromResource(DictionaryActivity.this, R.array.Plancategory, android.R.layout.simple_spinner_dropdown_item);
                    Smalladapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    Smallspinner.setAdapter(Smalladapter);
                    Smallspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            choice_Small = Smalladapter.getItem(position).toString();
                            if (Smalladapter.getItem(position).equals("기획")) {
                                explainCategory.setText("기획에 대한 설명입니다.");
                                relativeMajor.setText("기획에 관련 학과입니다.");
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                } else if (Bigadapter.getItem(position).equals("인테리어")) {
                    choice_Big = "인테리어";
                    Smalladapter = ArrayAdapter.createFromResource(DictionaryActivity.this, R.array.Interiorlcategory, android.R.layout.simple_spinner_dropdown_item);
                    Smalladapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    Smallspinner.setAdapter(Smalladapter);
                    Smallspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            choice_Small = Smalladapter.getItem(position).toString();
                            if (Smalladapter.getItem(position).equals("인테리어")) {
                                explainCategory.setText("인테리어에 대한 설명입니다.");
                                relativeMajor.setText("인테리어에 관련 학과입니다.");
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                } else if (Bigadapter.getItem(position).equals("대외활동")) {
                    choice_Big = "대외활동";
                    Smalladapter = ArrayAdapter.createFromResource(DictionaryActivity.this, R.array.Extracategory, android.R.layout.simple_spinner_dropdown_item);
                    Smalladapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    Smallspinner.setAdapter(Smalladapter);
                    Smallspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            choice_Small = Smalladapter.getItem(position).toString();
                            if (Smalladapter.getItem(position).equals("디자인")) {
                                explainCategory.setText("대외활동 디자인에 대한 설명입니다.");
                                relativeMajor.setText("대외활동 디자인에 관련 학과입니다.");
                            } else if (Smalladapter.getItem(position).equals("개발")) {
                                explainCategory.setText("대외활동 개발에 대한 설명입니다.");
                                relativeMajor.setText("대외활동 개발에 관련 학과입니다.");
                            } else if (Smalladapter.getItem(position).equals("사진·영상")) {
                                explainCategory.setText("대외활동 사진·영상에 대한 설명입니다.");
                                relativeMajor.setText("대외활동 사진·영상에 관련 학과입니다.");
                            } else if (Smalladapter.getItem(position).equals("번역·통역")) {
                                explainCategory.setText("대외활동 번역·통역에 대한 설명입니다.");
                                relativeMajor.setText("대외활동 번역·통역에 관련 학과입니다.");
                            } else if (Smalladapter.getItem(position).equals("기획")) {
                                explainCategory.setText("대외활동 기획에 대한 설명입니다.");
                                relativeMajor.setText("대외활동 기획에 관련 학과입니다.");
                            } else if (Smalladapter.getItem(position).equals("인테리어")) {
                                explainCategory.setText("대외활동 인테리어에 대한 설명입니다.");
                                relativeMajor.setText("대외활동 인테리어에 관련 학과입니다.");
                            } else if (Smalladapter.getItem(position).equals("봉사활동")) {
                                explainCategory.setText("대외활동 봉사활동에 대한 설명입니다.");
                                relativeMajor.setText("대외활동 봉사활동에 관련 학과입니다.");
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                    Log.w("omg",Integer.toString(big));
                    Log.w("omg",Integer.toString(array[big].length));
                    }
                for(int j=0;j<array[big].length;j++) {
                    if (contact_small.equals(array[big][j])) {
                        Smallspinner.setSelection(j);
                        break;
                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(DictionaryActivity.this, choice_Big + "=" + choice_Small, Toast.LENGTH_SHORT).show();
            }
        });
    }
}