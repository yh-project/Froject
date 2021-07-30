package com.example.froject.activity;

import android.os.Bundle;
import android.text.InputType;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.froject.R;

import static android.text.InputType.TYPE_TEXT_FLAG_MULTI_LINE;

public class WriteActivity extends AppCompatActivity {

    LinearLayout contentslayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);

        findViewById(R.id.addContents).setOnClickListener(onClickListener);
        findViewById(R.id.finishcontents).setOnClickListener(onClickListener);

        contentslayout = findViewById(R.id.contentsLayout);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()) {
                case R.id.addContents:
                    add_contents();
                    break;
                case R.id.finishcontents:

                    break;
            }
        }
    };


    private void add_contents() {
        int id_contentlayout = 1;
        int id_contentfield = 10;
        int id_field1 = 100;
        int id_field2 = 1000;
        int id_contenttext = 10000;

        String personcount = ((EditText)findViewById(R.id.inputCount)).getText().toString();
        int index = Integer.parseInt(personcount);
        for(int i=0 ; i<index; i++) {
            // contentlayout 생성
            LinearLayout contentlayout = new LinearLayout(getApplicationContext());

            LinearLayout.LayoutParams param_contentlayout = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            contentlayout.setOrientation(LinearLayout.VERTICAL);
            contentlayout.setId(id_contentlayout+i);
            param_contentlayout.setMargins(0,0,0,getDp(10));

            contentlayout.setLayoutParams(param_contentlayout);
            contentslayout.addView(contentlayout);

            // contentfield 생성
            LinearLayout contentfield = new LinearLayout(getApplicationContext());

            LinearLayout.LayoutParams param_contentfield = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            contentfield.setOrientation(LinearLayout.HORIZONTAL);
            contentfield.setId(id_contentfield+i);

            contentfield.setLayoutParams(param_contentfield);
            contentlayout.addView(contentfield);

            // field1 생성
            TextView field1 = new TextView(getApplicationContext());

            LinearLayout.LayoutParams param_field1 = new LinearLayout.LayoutParams(getDp(100), LinearLayout.LayoutParams.WRAP_CONTENT);
            field1.setText("hello");
            field1.setTextSize(20);
            field1.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            field1.setId(id_field1+i);

            field1.setLayoutParams(param_field1);
            contentfield.addView(field1);

            // field2 생성
            TextView field2 = new TextView(getApplicationContext());

            LinearLayout.LayoutParams param_field2 = new LinearLayout.LayoutParams(getDp(100), LinearLayout.LayoutParams.WRAP_CONTENT);
            field2.setText("world");
            field2.setTextSize(20);
            field2.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            field2.setId(id_field2+i);

            field2.setLayoutParams(param_field2);
            contentfield.addView(field2);

            // contenttext 생성
            EditText contenttext = new EditText(getApplicationContext());

            LinearLayout.LayoutParams param_contenttext = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            contenttext.setBackgroundResource(R.drawable.borderline);
            contenttext.setGravity(Gravity.START);
            contenttext.setHint("content");
            contenttext.setInputType(InputType.TYPE_CLASS_TEXT | TYPE_TEXT_FLAG_MULTI_LINE);
            contenttext.setLines(7);
            contenttext.setPadding(getDp(5), getDp(5), getDp(5), getDp(5));
            contenttext.setId(id_contenttext+i);

            contenttext.setLayoutParams(param_contenttext);
            contentlayout.addView(contenttext);
        }
    }












    private void finish_contents() {

    }




    private int getDp(Integer j) {
        int result = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, j, getResources().getDisplayMetrics());
        return result;
    }
}
