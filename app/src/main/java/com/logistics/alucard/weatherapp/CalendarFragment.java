package com.logistics.alucard.weatherapp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;


public class CalendarFragment extends Fragment {
    private static final String TAG = "CalendarFragment";

    public interface OnDataPass {
        void onDataPass(String data, String date);
    }

    CalendarView calendarView;
    Fragment me = this;

    OnDataPass datePasser;
    String curDate, weatherDate;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        datePasser = (OnDataPass) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_calendar, container, false);

        calendarView = v.findViewById(R.id.calendarView);
        calendarView.setMinDate(System.currentTimeMillis() - 1000);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {

                curDate = String.valueOf(month+1) + "-"
                        + String.valueOf(dayOfMonth) + "-"
                        + String.valueOf(year);
                String day = null;
                if(dayOfMonth < 10) {
                    day = "0" + String.valueOf(dayOfMonth);
                } else {
                    day = String.valueOf(dayOfMonth);
                }
                weatherDate = String.valueOf(year) + "-"
                        + String.valueOf(month+1) + "-"
                        + day;
                datePasser.onDataPass(curDate, weatherDate);


                getActivity().getSupportFragmentManager()
                        .beginTransaction().remove(me).commit();
            }
        });


        return v;
    }

}
