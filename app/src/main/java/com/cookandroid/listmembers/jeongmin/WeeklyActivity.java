package com.cookandroid.listmembers.jeongmin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.cookandroid.listmembers.R;
import com.google.firebase.auth.FirebaseAuth;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class WeeklyActivity extends AppCompatActivity {
    TextView titleText;
    TextView classtimesText;
    TextView classesText;
    TextView professorText;
    TextView test;
    myDBHelper myDBHelper;
    SQLiteDatabase sqlDB;

// 유저이름(id) / 년 월일(date) / 시작(start) /끝시간(end) / 키워드(과목명=keyword)  / 장소(location) /  메모(교수명 = meno) /색깔(color)
    private static final String TAG_ID = "id";
    private static final String TAG_DATE = "date";
    private static final String TAG_STARTS = "start";
    private static final String TAG_ENDS = "ends";
    private static final String TAG_KEYWORD = "keyword";
    private static final String TAG_LOCATION = "location";
    private static final String TAG_MEMO = "memo";
    private static final String TAG_COLOR = "color";
    private String userId =FirebaseAuth.getInstance().getCurrentUser().getUid();  //현재 로그인한 유저의 정보를 담는 변수


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weekly);

        myDBHelper = new myDBHelper(this);

        test = findViewById(R.id.text);
        titleText = findViewById(R.id.title);
        classtimesText = findViewById(R.id.times);
        classesText = findViewById(R.id.classes);
        professorText = findViewById(R.id.professor);

        int[] MondayId = {R.id.monday0, R.id.monday1, R.id.monday2,
                R.id.monday3, R.id.monday4, R.id.monday5,
                R.id.monday6, R.id.monday7, R.id.monday8,
                R.id.monday9, R.id.monday10
        };
        int[] tusedayId = {R.id.tuseday0, R.id.tuseday1, R.id.tuseday2,
                R.id.tuseday3, R.id.tuseday4, R.id.tuseday5,
                R.id.tuseday6, R.id.tuseday7, R.id.tuseday8,
                R.id.tuseday9, R.id.tuseday10
        };
        int[] wednesdayId = {R.id.wednesday0, R.id.wednesday1, R.id.wednesday2,
                R.id.wednesday3, R.id.wednesday4, R.id.wednesday5,
                R.id.wednesday6, R.id.wednesday7, R.id.wednesday8,
                R.id.wednesday9, R.id.wednesday10
        };
        int[] thursdayId = {R.id.thursday0, R.id.thursday1, R.id.thursday2,
                R.id.thursday3, R.id.thursday4, R.id.thursday5,
                R.id.thursday6, R.id.thursday7, R.id.thursday8,
                R.id.thursday9, R.id.thursday10
        };
        int[] fridayId = {R.id.friday0, R.id.friday1, R.id.friday2,
                R.id.friday3, R.id.friday4, R.id.friday5,
                R.id.friday6, R.id.friday7, R.id.friday8,
                R.id.friday9, R.id.friday10
        };


        final TextView []monday = new TextView[11];
        TextView []tuseday = new TextView[11];
        TextView []wednesday = new TextView[11];
        TextView []thursday = new TextView[11];
        TextView []friday = new TextView[11];


        final ArrayList<HashMap<String , String >> array = new ArrayList<HashMap<String, String>>();


        try{
            sqlDB = myDBHelper.getReadableDatabase();
            Cursor cursor;
            cursor = sqlDB.rawQuery("SELECT * FROM todo WHERE id ='"+ userId  +  "';", null);

            HashMap<String, String> persons = new HashMap<String, String>();


            while(cursor.moveToNext()){
                String ids = cursor.getString(0);
                String dates = cursor.getString(1);
                Integer start = cursor.getInt(2);
                Integer end = cursor.getInt(3);
                String keyword = cursor.getString(4);
                String location = cursor.getString(5);
                String memo = cursor.getString(6);
                String color = cursor.getString(7);


                try{
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
                    Date date = formatter.parse(dates);  //  날짜를 받음
                    date = new Date(date.getTime() + (1000*60*60*24-1));

                    Calendar cal = Calendar.getInstance();
                    cal.setTime(date);
                    String convertedString = "";
                    int dayNum = cal.get(Calendar.DAY_OF_WEEK);

                    switch (dayNum){
                        case 1:
                            convertedString = "";
                            break;
                        case 2:
                            convertedString = "monday";
                            break;
                        case 3:
                            convertedString = "tuseday";
                            break;
                        case 4:
                            convertedString = "wednesday";
                            break;
                        case 5:
                            convertedString = "thursday";
                            break;
                        case 6:
                            convertedString = "friday";
                            break;
                        case 7:
                            convertedString="";
                            break;
                    }

                    persons.put(TAG_DATE, convertedString);

                }catch (Exception e){}
//                특정 날짜에 해당하는 날짜를 요일로 변경해줌





                persons.put(TAG_ID, ids);
                persons.put(TAG_STARTS, start.toString());
                persons.put(TAG_ENDS, end.toString());
                persons.put(TAG_KEYWORD, keyword);
                persons.put(TAG_LOCATION, location);
                persons.put(TAG_MEMO, memo);
                persons.put(TAG_COLOR, color);

                array.add(persons);


            }
            test.setText("성공" + array.get(0) + "");

            cursor.close();
            sqlDB.close();


        }catch (Exception e){
            test.setText(e+"");
        }






        for(int i=0; i<11; i++){
            monday[i] = (TextView)findViewById(MondayId[i]);
            tuseday[i] = (TextView)findViewById(tusedayId[i]);
            wednesday[i] = (TextView)findViewById(wednesdayId[i]);
            thursday[i] = (TextView)findViewById(thursdayId[i]);
            friday[i] = (TextView)findViewById(fridayId[i]);
        }





        for(int i=0; i<array.size(); i++){
            if(array.get(i).get(TAG_DATE).equals("monday")){
                final int startTime = Integer.parseInt(array.get(i).get(TAG_STARTS)) - 8;
                final int endTime = Integer.parseInt(array.get(i).get(TAG_ENDS))-8;    //2
                final String title = array.get(i).get(TAG_KEYWORD);
                final String professor =array.get(i).get(TAG_MEMO);
                final String classes = array.get(i).get(TAG_LOCATION);

                int red = (int) (Math.random() * 10);
                int green = (int) (Math.random() * 100 );
                int blue = (int) (Math.random() * 100 );

                for(int j=startTime; j<endTime; j++){
                    monday[j].setText(title.substring(0,4));
                    monday[j].setBackgroundColor(Color.rgb(red, green, blue));
                    monday[j].setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            titleText.setText(title);
                            classtimesText.setText((startTime+8)+":00 ~" + (endTime+8)+":00");
                            classesText.setText(classes);
                            professorText.setText(professor);
                        }
                    });
                }
            }
            if(array.get(i).get(TAG_DATE).equals("tuseday")) {
                final int startTime = Integer.parseInt(array.get(i).get(TAG_STARTS)) - 8;
                final int endTime = Integer.parseInt(array.get(i).get(TAG_ENDS)) - 8;    //2
                final String title = array.get(i).get(TAG_KEYWORD);
                final String professor = array.get(i).get(TAG_MEMO);
                final String classes = array.get(i).get(TAG_LOCATION);

                int red = (int) (Math.random() * 10);
                int green = (int) (Math.random() * 100);
                int blue = (int) (Math.random() * 100);

                for (int j = startTime; j < endTime; j++) {
                    tuseday[j].setText(title.substring(0, 4));
                    tuseday[j].setBackgroundColor(Color.rgb(red, green, blue));
                    tuseday[j].setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            titleText.setText(title);
                            classtimesText.setText((startTime + 8) + ":00 ~" + (endTime + 8) + ":00");
                            classesText.setText(classes);
                            professorText.setText(professor);
                        }
                    });
                }
            }
            if(array.get(i).get(TAG_DATE).equals("wednesday"))
            {
                final int startTime = Integer.parseInt(array.get(i).get(TAG_STARTS)) - 8;
                final int endTime = Integer.parseInt(array.get(i).get(TAG_ENDS)) - 8;    //2
                final String title = array.get(i).get(TAG_KEYWORD);
                final String professor = array.get(i).get(TAG_MEMO);
                final String classes = array.get(i).get(TAG_LOCATION);

                int red = (int) (Math.random() * 10);
                int green = (int) (Math.random() * 100);
                int blue = (int) (Math.random() * 100);

                for (int j = startTime; j < endTime; j++) {
                    wednesday[j].setText(title.substring(0, 4));
                    wednesday[j].setBackgroundColor(Color.rgb(red, green, blue));
                    wednesday[j].setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            titleText.setText(title);
                            classtimesText.setText((startTime + 8) + ":00 ~" + (endTime + 8) + ":00");
                            classesText.setText(classes);
                            professorText.setText(professor);
                        }
                    });
                }
            }
            if(array.get(i).get(TAG_DATE).equals("thursday"))
            {
                final int startTime = Integer.parseInt(array.get(i).get(TAG_STARTS)) - 8;
                final int endTime = Integer.parseInt(array.get(i).get(TAG_ENDS)) - 8;    //2
                final String title = array.get(i).get(TAG_KEYWORD);
                final String professor = array.get(i).get(TAG_MEMO);
                final String classes = array.get(i).get(TAG_LOCATION);

                int red = (int) (Math.random() * 10);
                int green = (int) (Math.random() * 100);
                int blue = (int) (Math.random() * 100);

                for (int j = startTime; j < endTime; j++) {
                    thursday[j].setText(title.substring(0, 4));
                    thursday[j].setBackgroundColor(Color.rgb(red, green, blue));
                    thursday[j].setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            titleText.setText(title);
                            classtimesText.setText((startTime + 8) + ":00 ~" + (endTime + 8) + ":00");
                            classesText.setText(classes);
                            professorText.setText(professor);
                        }
                    });
                }
            }
            if(array.get(i).get(TAG_DATE).equals("friday"))
            {
                final int startTime = Integer.parseInt(array.get(i).get(TAG_STARTS)) - 8;
                final int endTime = Integer.parseInt(array.get(i).get(TAG_ENDS)) - 8;    //2
                final String title = array.get(i).get(TAG_KEYWORD);
                final String professor = array.get(i).get(TAG_MEMO);
                final String classes = array.get(i).get(TAG_LOCATION);

                int red = (int) (Math.random() * 10);
                int green = (int) (Math.random() * 100);
                int blue = (int) (Math.random() * 100);

                for (int j = startTime; j < endTime; j++) {
                    friday[j].setText(title.substring(0, 4));
                    friday[j].setBackgroundColor(Color.rgb(red, green, blue));
                    friday[j].setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            titleText.setText(title);
                            classtimesText.setText((startTime + 8) + ":00 ~" + (endTime + 8) + ":00");
                            classesText.setText(classes);
                            professorText.setText(professor);
                        }
                    });
                }
            }
        }


    }



    public class myDBHelper extends SQLiteOpenHelper {

        //1. groupDB를 초기화시킨다.
        public myDBHelper(Context context) {
            super(context, "sch", null, 1);
        }

        //2. groupTBL이라는 테이블을 생성한다.
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE todo (id char(100), date DATE, start INT, ends INT, keyword CHAR(100), \n" +
                    "                                     location CHAR(100), memo CHAR(100), color CHAR(30));");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS todo;");
            onCreate(db);
        }
    }
}


