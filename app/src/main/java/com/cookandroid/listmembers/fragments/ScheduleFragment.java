package com.cookandroid.listmembers.fragments;

//내꺼

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.cookandroid.listmembers.R;
import com.cookandroid.listmembers.jeongmin.WeeklyActivity;
import com.cookandroid.listmembers.subin.AlertActivity;
import com.cookandroid.listmembers.subin.EventDecorator;
import com.cookandroid.listmembers.subin.ListAdapter;
import com.cookandroid.listmembers.subin.ListData;
import com.cookandroid.listmembers.subin.OneDayDecorator;
import com.cookandroid.listmembers.subin.PopupActivity;
import com.cookandroid.listmembers.subin.SaturdayDecorator;
import com.cookandroid.listmembers.subin.SundayDecorator;
import com.google.firebase.auth.FirebaseAuth;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;


public class ScheduleFragment extends Fragment {
    myDBHelper myDBHelper;
    SQLiteDatabase sqlDB;

    MaterialCalendarView calendar;
    ListView listView;
    TextView noSchedule;
    int yearNow, monthNow, dateNow;
    int yearLast,monthLast,dateLast;
    int yearNext, monthNext, dateNext;
    ArrayList<ListData> allList = new ArrayList<>();
    ArrayList<ListData> dayList = new ArrayList<>();
    Button adder, toWeekly;
    CalendarDay onClickday;
    ListAdapter dayListAdapter;
    ListData sel4edt; int pos4edt;

    private String userid= FirebaseAuth.getInstance().getCurrentUser().getUid();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_monthly,null);

        myDBHelper = new myDBHelper(getActivity());

        yearNow= CalendarDay.today().getYear(); yearLast=yearNow-1; yearNext=yearNow+1;
        monthNow= CalendarDay.today().getMonth()+1; monthLast=monthNow-1; monthNext=monthNow+1;
        if(monthNow==12) {
            monthNext=1;
        }
        dateNow= CalendarDay.today().getDay(); dateLast=dateNow-1; dateNext=dateNow+1;
        calendar = view.findViewById(R.id.calendarView);
        listView=view.findViewById(R.id.data_list);
        noSchedule=view.findViewById(R.id.noSchedule);
        adder=view.findViewById(R.id.adder);
        onClickday= CalendarDay.today();
        toWeekly=view.findViewById(R.id.goWeekly);

        allList.add(new ListData(2019,12,19,0,24,"종강쓰","핵교","날 찾지말아요","RED"));
        already();

        listChange();

        //캘린더 설정
        calendar.state().edit()
                .setFirstDayOfWeek(Calendar.SUNDAY)
                .setMinimumDate(CalendarDay.today())
                .setMaximumDate(CalendarDay.from(2020,12,31))
                .setCalendarDisplayMode(CalendarMode.MONTHS)
                .commit();

        calendar.setShowOtherDates(MaterialCalendarView.SHOW_OUT_OF_RANGE);
        calendar.addDecorators(new SundayDecorator(), new SaturdayDecorator(), new OneDayDecorator());
        calendar.setDynamicHeightEnabled(true);
        calendar.setSelectedDate(CalendarDay.today().getDate());

        //캘린더 일 선택 이벤트

        calendar.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {

                onClickday=date;
                listChange();
            }


        });

        adder.setOnClickListener( new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent listAdder = new Intent(getActivity(), PopupActivity.class);
                listAdder.putExtra("SELECTOR","MAIN");
                startActivityForResult(listAdder, 0000);
            }
        });

        toWeekly.setOnClickListener( new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent week = new Intent(getActivity(), WeeklyActivity.class);
                startActivity(week);
            }
        });

        //리스트 선택 이벤트 (목록 열기)
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                ListData selected = dayList.get(position);
                boolean nowDoor = selected.getDoor();
                for(int i=0; i<dayList.size();i++) dayList.get(i).setDoor(false);
                selected.setDoor(!nowDoor);
                dayListAdapter.notifyDataSetChanged();
            }
        });

        //리스트 길게 누르기 (목록 삭제)
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView parent, View view, int position, long id) {
                pos4edt=position;
                sel4edt= dayList.get(pos4edt);
                Intent listEdit = new Intent(getActivity(), AlertActivity.class);
                listEdit.putExtra("place",sel4edt.getPlace());
                listEdit.putExtra("memo",sel4edt.getMemo());
                listEdit.putExtra("color",sel4edt.getColor());
                listEdit.putExtra("end",sel4edt.getEnd() );
                listEdit.putExtra("keyword",sel4edt.getKeyword());
                listEdit.putExtra("start",sel4edt.getStart());
                listEdit.putExtra("SELECTOR","ALERT");

                startActivityForResult(listEdit, 0001);

                return true;
            }
        });




        return view;

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==0000){
            if(resultCode==1) {
                String dayColor=data.getStringExtra("color");
                int addyear = onClickday.getYear();
                int addday = onClickday.getDay();
                int addmonth = onClickday.getMonth()+1;
                ListData got = new ListData(addyear,addmonth, addday, data.getIntExtra("start", 0), data.getIntExtra("end", 24), data.getStringExtra("name"), data.getStringExtra("place"), data.getStringExtra("memo"),dayColor );
                addADB(userid,got);
                Toast.makeText(getActivity(),"입력됨",Toast.LENGTH_SHORT).show();
                calendar.addDecorator(new EventDecorator(onClickday,dayColor,true));
                listChange();
            }

        }
        if(requestCode==0001){
            if(resultCode==2) {

                if(data.getStringExtra("result").equals("delete")) {
                    deleteADB(userid,sel4edt);
                    calendar.addDecorator(new EventDecorator(onClickday,sel4edt.getColor(),false));
                    listChange();
                    Toast.makeText(getActivity(),"삭제됨",Toast.LENGTH_SHORT).show();
                }
            }

            else if(resultCode==3) {
                String dayColor=data.getStringExtra("color");
                int addyear = onClickday.getYear();
                int addday = onClickday.getDay();
                int addmonth = onClickday.getMonth()+1;
                ListData got = new ListData(addyear,addmonth, addday, data.getIntExtra("start", 0), data.getIntExtra("end", 24), data.getStringExtra("name"), data.getStringExtra("place"), data.getStringExtra("memo"),dayColor );

                dayList.remove(pos4edt);
                for (int i = 0; i < allList.size(); i++) {
                    if (sel4edt.equals(allList.get(i))) allList.remove(i);
                }

                calendar.addDecorator(new EventDecorator(onClickday,sel4edt.getColor(),false));

                deleteADB(userid,sel4edt);
                addADB(userid,got);
                calendar.addDecorator(new EventDecorator(onClickday,dayColor,true));
                listChange();

                Toast.makeText(getActivity(),"수정됨",Toast.LENGTH_SHORT).show();
            }
        }



    }
    public void listChange(){
        dayList.clear();
        getADB(userid);
        for(int index=0; index<allList.size();index++){
            int fdsfkj = allList.get(index).getMonth();
            if((allList.get(index).getYear()==onClickday.getYear())&&(allList.get(index).getMonth()==onClickday.getMonth()+1)&&(allList.get(index).getDate()==onClickday.getDay()))
                dayList.add(allList.get(index));
        }
        if(dayList.isEmpty()) {
            listView.setVisibility(View.GONE);
            noSchedule.setVisibility(View.VISIBLE);
        }
        else{
            noSchedule.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);
        }
        dayListAdapter = new ListAdapter(dayList);
        listView.setAdapter(dayListAdapter);
        dayListAdapter.notifyDataSetChanged();
    }


    public void already(){
        getADB(userid);
        for(int i=0; i<allList.size(); i++){
            ListData al_data = allList.get(i);
            CalendarDay al_day = new CalendarDay(al_data.getYear(),al_data.getMonth()-1,al_data.getDate());
            calendar.addDecorator(new EventDecorator(al_day,al_data.getColor(),true));
        }
    }

    public void addADB(String id,ListData makeList){
        int year = makeList.getYear(),month=makeList.getMonth(), date = makeList.getDate(), start=makeList.getStart(), end=makeList.getEnd();
        String keyword=makeList.getKeyword(), place=makeList.getPlace(), memo=makeList.getMemo(), color=makeList.getColor();
        sqlDB= myDBHelper.getWritableDatabase();
        String doday = Integer.toString(year)+Integer.toString(month)+Integer.toString(date);
        sqlDB.execSQL("INSERT INTO todo VALUES ('"+id+"','"+doday+"','"+start+"','"+end+"','"+keyword+"','"+place+"','"+memo+"','"+color+"');");
        sqlDB.close();
    }
    public void getADB(String id){
        Cursor cursor;
        sqlDB=myDBHelper.getReadableDatabase();
        cursor = sqlDB.rawQuery("SELECT * FROM todo WHERE id ='"+ id + "';", null);
        allList.clear();
        boolean isthere = cursor.moveToNext();
        while(cursor.moveToNext()) {
            int year, month, date, start, end;
            String keyword, place, memo, color;

            String temp[] = cursor.getString(1).split("");

            String yeart = temp[1] + temp[2] + temp[3] + temp[4];
            String montht = temp[5] + temp[6];
            String datet = temp[7] + temp[8];

            year = Integer.parseInt(yeart);
            month = Integer.parseInt(montht);
            date = Integer.parseInt(datet);

            start = Integer.parseInt(cursor.getString(2));
            end = Integer.parseInt(cursor.getString(3));
            keyword = cursor.getString(4);
            place = cursor.getString(5);
            memo = cursor.getString(6);
            color = cursor.getString(7);

            ListData tempData = new ListData(year, month, date, start, end, keyword, place, memo, color);
            allList.add(tempData);
        }
        cursor.close();
        sqlDB.close();
    }

    public void deleteADB(String id, ListData deleteData) {
        sqlDB = myDBHelper.getWritableDatabase();
        Cursor cursor;

        cursor = sqlDB.rawQuery("SELECT * FROM todo WHERE id = '" + id + "';", null);

        if (cursor.getCount() != 0) {
            sqlDB.execSQL("DELETE FROM todo WHERE keyword = '"+ deleteData.getKeyword()+ "';");
            sqlDB.close();
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
