package com.example.choisung.emotiondiary.fragment;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.choisung.emotiondiary.DBHelper;
import com.example.choisung.emotiondiary.DiaryListAdapter;
import com.example.choisung.emotiondiary.DiaryText;
import com.example.choisung.emotiondiary.R;

import java.util.ArrayList;

import static com.example.choisung.emotiondiary.fragment.DiaryFragment.dbHelper;


public class EntriesFragment extends Fragment implements AdapterView.OnItemLongClickListener, AdapterView.OnItemClickListener{

    public static ListView list_diary;
    public static DiaryListAdapter listAdapter;
    public static Button btn_showList;
    public static TextView diary_tvTime, diary_tvMonth, diary_tvDayOfWeek, diary_tvDay, diary_tvTitle, diary_tvContents;
    public static EditText update_edTitle, update_edContents, ET_pass_dialog;
    public static LinearLayout LL_diary_content;
    public static SettingFragment setting;

    public static ArrayList<DiaryText> list;
    public static DBHelper dbHelper;
    public static SQLiteDatabase db;

    private static View updateDialogView, passDialogView;
    private static String title, contents, updateTitle, updateContents;
    
    private int code;

    // fragment
    public static EntriesFragment newInstance(){
        Bundle args = new Bundle();

        EntriesFragment fragment = new EntriesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_entries, container, false); //fragment 보이기
        dbHelper = new DBHelper(getContext());

        //xml에 추가한 listView연결
        list_diary = (ListView) rootView.findViewById(R.id.list_diary);
        btn_showList = (Button) rootView.findViewById(R.id.btn_showList);
        btn_showList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    list = showDB();
                    listAdapter = new DiaryListAdapter(getContext(), R.layout.item_entries, list);
                    list_diary.setAdapter(listAdapter);
                }
        });

        list_diary.setOnItemLongClickListener(this);
        list_diary.setOnItemClickListener(this);

        return rootView;
    }

    //item long클릭시 삭제
    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
        alertDialog.setMessage(list.get(position).getTitle() + "을(를) 삭제하시겠습니까?");
        alertDialog.setPositiveButton("삭제", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                code = list.get(position).getCode();
                list.remove(position);
                deleteDB(code);
                showDB();
                listAdapter.notifyDataSetChanged();
            }
        });
        alertDialog.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alertDialog.show();
        return true;
    }

    //item click 일기보기
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        updateDialogView = (View) view.inflate(getContext(), R.layout.updatediary_dialog, null);
        AlertDialog.Builder updateDialog = new AlertDialog.Builder(getActivity());

        updateDialog.setView(updateDialogView);

        code = list.get(position).getCode();
        LL_diary_content = (LinearLayout) updateDialogView.findViewById(R.id.LL_diary_content);

        diary_tvTime = (TextView) updateDialogView.findViewById(R.id.diary_tvTime);
        diary_tvMonth = (TextView) updateDialogView.findViewById(R.id.diary_tvMonth);
        diary_tvDayOfWeek = (TextView) updateDialogView.findViewById(R.id.diary_tvDayOfWeek);
        diary_tvDay = (TextView) updateDialogView.findViewById(R.id.diary_tvDay);

        diary_tvTitle = (TextView) updateDialogView.findViewById(R.id.diary_tvTitle);
        diary_tvContents = (TextView) updateDialogView.findViewById(R.id.diary_tvContents);
        update_edTitle = (EditText) updateDialogView.findViewById(R.id.update_edTitle);
        update_edContents = (EditText) updateDialogView.findViewById(R.id.update_edContents);

        diary_tvTime.setText(list.get(position).getTime());
        diary_tvMonth.setText(list.get(position).getMonth() + "월");
        diary_tvDayOfWeek.setText(list.get(position).getDayOfWeek());
        diary_tvDay.setText(list.get(position).getDate());

        title = list.get(position).getTitle().toString();
        contents = list.get(position).getContents().toString();

        diary_tvTitle.setText(title);
        diary_tvContents.setText(contents);

        LL_diary_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                update_edTitle.setText(title);
                update_edContents.setText(contents);

                diary_tvTitle.setVisibility(updateDialogView.GONE);
                update_edTitle.setVisibility(updateDialogView.VISIBLE);
                diary_tvContents.setVisibility(updateDialogView.GONE);
                update_edContents.setVisibility(updateDialogView.VISIBLE);

            }
        });

        updateDialog.setPositiveButton("modify", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                updateTitle = update_edTitle.getText().toString();
                updateContents = update_edContents.getText().toString();

                updateDB(updateTitle, updateContents, code);
            }
        });

        updateDialog.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        updateDialog.show();
    }


    public static ArrayList<DiaryText> showDB(){
        db = dbHelper.getReadableDatabase();
        String sql = "SELECT * FROM diary";
        Cursor cusor = db.rawQuery(sql, null);
        list = new ArrayList<>();

        while(cusor.moveToNext()){
            DiaryText diaryText = new DiaryText();
            diaryText.setCode(cusor.getInt(0));
            diaryText.setTime(cusor.getString(1));
            diaryText.setMonth(cusor.getString(2));
            diaryText.setDayOfWeek(cusor.getString(3));
            diaryText.setDate(cusor.getString(4));
            diaryText.setTitle(cusor.getString(5));
            diaryText.setContents(cusor.getString(6));
            list.add(diaryText);
        }

        return list;
    }

    private void deleteDB(int code) {
        db = dbHelper.getReadableDatabase();
        String sql = "DELETE FROM diary where code=" + code;
        db.execSQL(sql);
        db.close();
    }

    private void updateDB(String title, String contents, int code) {
        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("title", title);
        values.put("contents", contents);
        db.update("diary", values, "code=" + code, null);

    }
}

