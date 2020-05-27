package com.cookandroid.listmembers.subin;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

import java.util.ArrayList;
import java.util.Hashtable;

/**
 * Decorate several days with a dot
 */
public class EventDecorator implements DayViewDecorator {
    private CalendarDay dates;
    static Hashtable<CalendarDay,ArrayList<String>> deco4day = new Hashtable<>(); //정적 해시테이블

    public EventDecorator(CalendarDay dates, String colors, boolean choice) {
        this.dates = dates; //날짜 가져와서
        if(choice) addDeco(colors); //true = add, false = remove
        else removeDeco(colors);

    }

    private void addDeco(String colors){
        if(deco4day.containsKey(dates)) { //이미 있으면
            boolean isThere = false; //같은 색 있는지 검사
            ArrayList<String> preColor = deco4day.get(dates); //색깔 리스트 가져와서
            for (int i = 0; i < preColor.size(); i++) { //하나씩 검사
                if (colors.equals(preColor.get(i))) { //같은게 검사되면
                    isThere = true; //이미 있으니까
                    break; //탈출!
                }
            }
            if (!isThere) {//없었으면 넣어서 리스트 대체!
                preColor.add(colors);
                deco4day.remove(dates);
                deco4day.put(dates, preColor);
            }
        }
        else { //아예 처음생기는 날짜면
            ArrayList<String>  firstColor = new ArrayList<>();
            firstColor.add(colors);
            deco4day.put(dates,firstColor);
        }
    }

    private void removeDeco(String colors){
        ArrayList<String> preColor = deco4day.get(dates); //색깔 리스트 가져와서
        int deletepos=0;
        for (int i = 0; i < preColor.size(); i++) { //하나씩 검사
            if (colors.equals(preColor.get(i))) {
                deletepos = i; //없앨 인덱스 알아내고
                break;
            }
        }
        preColor.remove(deletepos); //색깔리스트에서 없애고
        deco4day.remove(dates);
        deco4day.put(dates, preColor);  //해시테이블 대체
    }


    @Override
    public boolean shouldDecorate(CalendarDay day) {
        int dayyear=day.getYear(); int daymonth= day.getMonth(); int daydate=day.getDay();
        int datesyear=dates.getYear(); int datesmonth=dates.getMonth(); int datesdate=dates.getDay();
        if((dayyear==datesyear)&&(daymonth==datesmonth)&&(daydate==datesdate)) return true;
        else return false;
    }

    @Override
    public void decorate(DayViewFacade view) {

        view.addSpan((new CustomMultipleDotSpan(deco4day.get(dates))));

    }

}
