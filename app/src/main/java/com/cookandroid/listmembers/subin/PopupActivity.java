package com.cookandroid.listmembers.subin;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.cookandroid.listmembers.R;

public class PopupActivity extends AppCompatActivity {

    private EditText name, place, start, end, memo;
    private Button school, student, personal; //변수선언
    private Switch allday;
    private Button ok, nok;
    private LinearLayout startLayout, endLayout;

    private String color; //수정할 때 쓰는 변수
    private boolean isModify = false; //true면 수정하는 거, false 면 추가하는거

    private String val_name, val_place, val_memo, val_color=null;
    private int val_start, val_end;
    private String SELECTOR;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_popup);

        name=(EditText)findViewById(R.id.p_name);
        place=(EditText)findViewById(R.id.p_place);
        start=(EditText)findViewById(R.id.p_startTime);
        end=(EditText)findViewById(R.id.p_endTime);
        memo=(EditText)findViewById(R.id.p_memo);
        school=(Button)findViewById(R.id.p_school);
        student=(Button)findViewById(R.id.p_student);
        personal=(Button)findViewById(R.id.p_personal);
        allday=(Switch)findViewById(R.id.p_allday);
        ok=(Button)findViewById(R.id.p_ok);
        nok=(Button)findViewById(R.id.p_nok);
        startLayout=(LinearLayout)findViewById(R.id.startLayout);
        endLayout=(LinearLayout)findViewById(R.id.endLayout);
        //변수정의

        student.setBackgroundColor(Color.parseColor("WHITE"));
        personal.setBackgroundColor(Color.parseColor("WHITE"));
        school.setBackgroundColor(Color.parseColor("WHITE"));

        Intent givenIntent = getIntent();
        SELECTOR = givenIntent.getStringExtra("SELECTOR");


        if(SELECTOR.equals("ALERT")){
            isModify=true;
            int pre_start = givenIntent.getIntExtra("start",0);
            int pre_end= givenIntent.getIntExtra("end",24);
            name.setText(givenIntent.getStringExtra("keyword"));
            place.setText(givenIntent.getStringExtra("place"));
            color= givenIntent.getStringExtra("color");
            memo.setText(givenIntent.getStringExtra("memo"));
            start.setText(Integer.toString(pre_start));
            end.setText(Integer.toString(pre_end));
            if((pre_start==0) && (pre_end==24)){
                val_start=0; val_end=24;
                allday.setChecked(true);
                startLayout.setVisibility(View.INVISIBLE);
                endLayout.setVisibility(View.INVISIBLE);
            }

            if(color.equals("RED")){
                student.setBackgroundColor(Color.parseColor("WHITE"));
                student.setTextColor(Color.parseColor("GRAY"));

                personal.setBackgroundColor(Color.parseColor("WHITE"));
                personal.setTextColor(Color.parseColor("GRAY"));

                school.setBackgroundColor(Color.parseColor("RED"));
                school.setTextColor(Color.parseColor("WHITE"));
                val_color="RED";

            }

            else if(color.equals("BLUE")){
                student.setBackgroundColor(Color.parseColor("WHITE"));
                student.setTextColor(Color.parseColor("GRAY"));

                school.setBackgroundColor(Color.parseColor("WHITE"));
                school.setTextColor(Color.parseColor("GRAY"));

                personal.setBackgroundColor(Color.parseColor("BLUE"));
                personal.setTextColor(Color.parseColor("WHITE"));
                val_color="BLUE";

            }
            else if(color.equals("GREEN")){
                school.setBackgroundColor(Color.parseColor("WHITE"));
                school.setTextColor(Color.parseColor("GRAY"));

                personal.setBackgroundColor(Color.parseColor("WHITE"));
                personal.setTextColor(Color.parseColor("GRAY"));

                student.setBackgroundColor(Color.parseColor("GREEN"));
                student.setTextColor(Color.parseColor("WHITE"));
                val_color="GREEN";
            }

        }

        school.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                student.setBackgroundColor(Color.parseColor("WHITE"));
                student.setTextColor(Color.parseColor("GRAY"));

                personal.setBackgroundColor(Color.parseColor("WHITE"));
                personal.setTextColor(Color.parseColor("GRAY"));

                school.setBackgroundColor(Color.parseColor("RED"));
                school.setTextColor(Color.parseColor("WHITE"));
                val_color="RED";
            }
        });
        student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                school.setBackgroundColor(Color.parseColor("WHITE"));
                school.setTextColor(Color.parseColor("GRAY"));

                personal.setBackgroundColor(Color.parseColor("WHITE"));
                personal.setTextColor(Color.parseColor("GRAY"));

                student.setBackgroundColor(Color.parseColor("GREEN"));
                student.setTextColor(Color.parseColor("WHITE"));
                val_color="GREEN";
            }
        });
        personal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                student.setBackgroundColor(Color.parseColor("WHITE"));
                student.setTextColor(Color.parseColor("GRAY"));

                school.setBackgroundColor(Color.parseColor("WHITE"));
                school.setTextColor(Color.parseColor("GRAY"));

                personal.setBackgroundColor(Color.parseColor("BLUE"));
                personal.setTextColor(Color.parseColor("WHITE"));
                val_color="BLUE";
            }
        });

        allday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    startLayout.setVisibility(View.INVISIBLE);
                    endLayout.setVisibility(View.INVISIBLE);
                }
                else{
                    startLayout.setVisibility(View.VISIBLE);
                    endLayout.setVisibility(View.VISIBLE);
                }
            }
        });

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(val_color!=null) {
                    try {
                        val_name = name.getText().toString();
                        val_place = place.getText().toString();
                        try {
                            val_memo = memo.getText().toString();
                        }catch(Exception e){
                            val_memo="";
                        }
                        Intent thisIntent = new Intent();
                        thisIntent.putExtra("name", val_name);
                        thisIntent.putExtra("place", val_place);
                        thisIntent.putExtra("color", val_color);
                        thisIntent.putExtra("memo", val_memo);
                       if(allday.isChecked()){
                           val_start=0; val_end=24;
                       }
                       else{
                           val_start = Integer.parseInt(start.getText().toString());
                           val_end = Integer.parseInt(end.getText().toString());
                       }
                       if((val_end>val_start) && (val_start<24) && (val_end<=24) && (val_start>=0)&& (val_end>0)) {
                           thisIntent.putExtra("start", val_start);
                           thisIntent.putExtra("end", val_end);
                           if(isModify) setResult(3, thisIntent);
                           else setResult(1, thisIntent);
                           finish();
                       }
                       else Toast.makeText(getApplicationContext(), "시간을 확인하세요!", Toast.LENGTH_LONG).show();
                    }catch(Exception e){
                        Toast.makeText(getApplicationContext(), "내용을 채워주세요!", Toast.LENGTH_LONG).show();
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(), "카테고리를 선택해주세요!", Toast.LENGTH_LONG).show();
                }

            }
        });

        nok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

}
