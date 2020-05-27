package com.cookandroid.listmembers.subin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.cookandroid.listmembers.R;
import com.cookandroid.listmembers.fragments.ScheduleFragment;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.util.ArrayList;
import java.util.Calendar;

public class MonthlyActivity extends AppCompatActivity {
    private FragmentManager fragmentManager;
    private ScheduleFragment myplace;

    //변수 선언
//    MaterialCalendarView calendar;
//    ListView listView;
//    TextView noSchedule;
//    int yearNow, monthNow, dateNow;
//    int yearLast, monthLast, dateLast;
//    int yearNext, monthNext, dateNext;
//    ArrayList<ListData> allList = new ArrayList<>();
//    ArrayList<ListData> dayList = new ArrayList<>();
//    Button adder;
//    CalendarDay onClickday;
//    ListAdapter dayListAdapter;
//    ListData sel4edt;
//    int pos4edt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monthly);


        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.monthlyContent, new ScheduleFragment());
        fragmentTransaction.commit();
    }
}



//        //변수 정의
//        yearNow= CalendarDay.today().getYear(); yearLast=yearNow-1; yearNext=yearNow+1;
//        monthNow= CalendarDay.today().getMonth()+1; monthLast=monthNow-1; monthNext=monthNow+1;
//        if(monthNow==12) {
//            monthNext=1;
//        }
//        dateNow= CalendarDay.today().getDay(); dateLast=dateNow-1; dateNext=dateNow+1;
//        calendar = findViewById(R.id.calendarView);
//        listView=findViewById(R.id.data_list);
//        noSchedule=findViewById(R.id.noSchedule);
//        adder=findViewById(R.id.adder);
//        onClickday= CalendarDay.today();
//
//        allList.add(new ListData(2019,12,19,0,24,"종강쓰","핵교","날 찾지말아요","RED"));
//        already();
//
//        listChange();
//
//        //캘린더 설정
//       calendar.state().edit()
//                .setFirstDayOfWeek(Calendar.SUNDAY)
//                .setMinimumDate(CalendarDay.today())
//                .setMaximumDate(CalendarDay.from(2020,12,31))
//                .setCalendarDisplayMode(CalendarMode.MONTHS)
//                .commit();
//
//        calendar.setShowOtherDates(MaterialCalendarView.SHOW_OUT_OF_RANGE);
//        calendar.addDecorators(new SundayDecorator(), new SaturdayDecorator(), new OneDayDecorator());
//        calendar.setDynamicHeightEnabled(true);
//        calendar.setSelectedDate(CalendarDay.today().getDate());
//
//        //캘린더 일 선택 이벤트
//
//        calendar.setOnDateChangedListener(new OnDateSelectedListener() {
//            @Override
//            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
//
//                onClickday=date;
//                listChange();
//            }
//
//
//        });
//
//        //버튼 선택이벤트(리스트 추가)
//
//        adder.setOnClickListener( new Button.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent listAdder = new Intent(getApplicationContext(), PopupActivity.class);
//                listAdder.putExtra("SELECTOR","MAIN");
//                startActivityForResult(listAdder, 0000);
//            }
//        });
//
//
//
//        //리스트 선택 이벤트 (목록 열기)
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView parent, View view, int position, long id) {
//                ListData selected = dayList.get(position);
//                boolean nowDoor = selected.getDoor();
//                for(int i=0; i<dayList.size();i++) dayList.get(i).setDoor(false);
//                selected.setDoor(!nowDoor);
//                dayListAdapter.notifyDataSetChanged();
//            }
//        });
//
//        //리스트 길게 누르기 (목록 삭제)
//        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView parent, View view, int position, long id) {
//                pos4edt=position;
//                sel4edt= dayList.get(pos4edt);
//                Intent listEdit = new Intent(getApplicationContext(), AlertActivity.class);
//                listEdit.putExtra("place",sel4edt.getPlace());
//                listEdit.putExtra("memo",sel4edt.getMemo());
//                listEdit.putExtra("color",sel4edt.getColor());
//                listEdit.putExtra("end",sel4edt.getEnd() );
//                listEdit.putExtra("keyword",sel4edt.getKeyword());
//                listEdit.putExtra("start",sel4edt.getStart());
//                listEdit.putExtra("SELECTOR","ALERT");
//
//                startActivityForResult(listEdit, 0001);
//
//                return true;
//            }
//           });
//
//
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if(requestCode==0000){
//            if(resultCode==1) {
//                String dayColor=data.getStringExtra("color");
//                int addyear = onClickday.getYear();
//                int addday = onClickday.getDay();
//                int addmonth = onClickday.getMonth()+1;
//                ListData got = new ListData(addyear,addmonth, addday, data.getIntExtra("start", 0), data.getIntExtra("end", 24), data.getStringExtra("name"), data.getStringExtra("place"), data.getStringExtra("memo"),dayColor );
//                allList.add(got);
//                dayList.add(got);
//                calendar.addDecorator(new EventDecorator(onClickday,dayColor,true));
//                listChange();
//            }
//
//        }
//        if(requestCode==0001){
//            if(resultCode==2) {
//
//                if(data.getStringExtra("result").equals("delete")) {
//                    dayList.remove(pos4edt);
//                    for (int i = 0; i < allList.size(); i++) {
//                        if (sel4edt.equals(allList.get(i))) allList.remove(i);
//                    }
//                    calendar.addDecorator(new EventDecorator(onClickday,sel4edt.getColor(),false));
//                    listChange();
//                }
//            }
//
//            else if(resultCode==3) {
//                int searchreques = requestCode;
//                String dayColor=data.getStringExtra("color");
//                int addyear = onClickday.getYear();
//                int addday = onClickday.getDay();
//                int addmonth = onClickday.getMonth()+1;
//                ListData got = new ListData(addyear,addmonth, addday, data.getIntExtra("start", 0), data.getIntExtra("end", 24), data.getStringExtra("name"), data.getStringExtra("place"), data.getStringExtra("memo"),dayColor );
//
//                dayList.remove(pos4edt);
//                for (int i = 0; i < allList.size(); i++) {
//                    if (sel4edt.equals(allList.get(i))) allList.remove(i);
//                }
//                calendar.addDecorator(new EventDecorator(onClickday,sel4edt.getColor(),false));
//
//                allList.add(got);
//                dayList.add(got);
//                calendar.addDecorator(new EventDecorator(onClickday,dayColor,true));
//                listChange();
//            }
//        }
//
//
//
//    }
//
//    public void already(){
//        for(int i=0; i<allList.size(); i++){
//            ListData al_data = allList.get(i);
//            CalendarDay al_day = new CalendarDay(al_data.getYear(),al_data.getMonth()-1,al_data.getDate());
//            calendar.addDecorator(new EventDecorator(al_day,al_data.getColor(),true));
//        }
//    }
//
//
//
//    public void listChange(){
//        dayList.clear();
//        for(int index=0; index<allList.size();index++){
//            if((allList.get(index).getYear()==onClickday.getYear())&&(allList.get(index).getMonth()==onClickday.getMonth()+1)&&(allList.get(index).getDate()==onClickday.getDay()))
//                dayList.add(allList.get(index));
//        }
//        if(dayList.isEmpty()) {
//            listView.setVisibility(View.GONE);
//            noSchedule.setVisibility(View.VISIBLE);
//        }
//        else{
//            noSchedule.setVisibility(View.GONE);
//            listView.setVisibility(View.VISIBLE);
//        }
//        dayListAdapter = new ListAdapter(dayList);
//        listView.setAdapter(dayListAdapter);
//        dayListAdapter.notifyDataSetChanged();
//    }
//
//
//}
//
