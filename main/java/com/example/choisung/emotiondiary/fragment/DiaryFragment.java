package com.example.choisung.emotiondiary.fragment;


import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.choisung.emotiondiary.DBHelper;
import com.example.choisung.emotiondiary.DiaryText;
import com.example.choisung.emotiondiary.PasswordDBHelper;
import com.example.choisung.emotiondiary.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class DiaryFragment extends Fragment implements CalendarView.OnDateChangeListener {


    private static ArrayList<DiaryText> data;
    public static DBHelper dbHelper;
    public static PasswordDBHelper pdbHelper;
    public static SQLiteDatabase db;

    private static TextView diary_tvMonth, diary_tvDay, diary_tvDayOfWeek, diary_tvTime;
    private static EditText diary_edTitle, diary_edContents;
    private static View dialogView;  // fragment 연결하기
    private static CalendarView calendar;

    private static String aMonth, aDayOfWeek, aDay, aTime, aTitle, aContents;

    public static DiaryFragment newInstance(){
        Bundle args = new Bundle();

        DiaryFragment fragment = new DiaryFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.diary_dialog, container, false); //fragment 보이기
        View calendarView = inflater.inflate(R.layout.fragment_diary, container, false);
        this.dbHelper = new DBHelper(this.getContext());

        calendar = (CalendarView) calendarView.findViewById(R.id.calendar);
        calendar.setOnDateChangeListener(this);

        return calendarView;
    }


    @Override
    public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, final int dayOfMonth) {

        dialogView = (View) view.inflate(getActivity(), R.layout.diary_dialog, null);
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());

        alertDialog.setView(dialogView);

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, dayOfMonth);


        aDay = Integer.toString(dayOfMonth);
        aMonth = Integer.toString(month +1);
        aDayOfWeek = getDayOfWeek(calendar.get(Calendar.DAY_OF_WEEK));
        aTime = new SimpleDateFormat("HH:mm").format(calendar.getInstance().getTime());

        diary_tvMonth = (TextView) dialogView.findViewById(R.id.diary_tvMonth);
        diary_tvDay = (TextView) dialogView.findViewById(R.id.diary_tvDay);
        diary_tvDayOfWeek = (TextView) dialogView.findViewById(R.id.diary_tvDayOfWeek);
        diary_tvTime = (TextView) dialogView.findViewById(R.id.diary_tvTime);

        diary_tvMonth.setText(Integer.toString(month+1) + "월");
        diary_tvDay.setText(Integer.toString(dayOfMonth));
        diary_tvDayOfWeek.setText(aDayOfWeek);
        diary_tvTime.setText(aTime);

        alertDialog.setPositiveButton("save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                diary_edTitle = (EditText) dialogView.findViewById(R.id.diary_edTitle);
                diary_edContents = (EditText) dialogView.findViewById(R.id.diary_edContents);

                aTitle = diary_edTitle.getText().toString();
                aContents = diary_edContents.getText().toString();

                Toast.makeText(getActivity(), aTitle, Toast.LENGTH_SHORT).show();

                insert(aTime, aMonth, aDayOfWeek, aDay, aTitle, aContents);
            }
        });
        alertDialog.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        alertDialog.show();
        Toast.makeText(getActivity(), "" + year + "/" + (month+1) + "/" + dayOfMonth, Toast.LENGTH_SHORT).show();
    }

    private String getDayOfWeek(int dayOfWeek) {
        String day = "";
        switch (dayOfWeek){
            case 1:
                day = "일요일";
                break;
            case 2:
                day = "월요일";
                break;
            case 3:
                day = "화요일";
                break;
            case 4:
                day = "수요일";
                break;
            case 5:
                day = "목요일";
                break;
            case 6:
                day = "금요일";
                break;
            case 7:
                day = "토요일";
                break;
        }
        return day;
    }


    public void insert(String time, String month, String dayOfWeek, String date, String title, String contents){
        db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("time", time);
        values.put("month", month);
        values.put("dayOfWeek", dayOfWeek);

        values.put("date", date);
        values.put("title", title);
        values.put("contents", contents);

        db.insert("diary",null,values);
    }


}
