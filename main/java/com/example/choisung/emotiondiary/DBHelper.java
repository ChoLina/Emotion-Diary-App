package com.example.choisung.emotiondiary;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ChoISung on 2017-06-03.
 */

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context) {
        super(context, "Diary", null, 1);
    }

    //DB처음 만들때 호출 - 테이블 생성 등의 초기 처리
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createDairyTable =  "CREATE TABLE diary (" +
                "code INTEGER PRIMARY KEY AUTOINCREMENT," +
                "time TEXT," +
                "month TEXT," +
                "dayOfWeek TEXT," +
                "date TEXT," +
                "title TEXT," +
                "contents TEXT)";
        db.execSQL(createDairyTable);
    }

    //DB업그래이드 필요시 호출(version값에 따라 반응)
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Delete  diary v1 db
        String deleteDiaryTable = "DROP TABLE IF EXISTS diary";
        db.execSQL(deleteDiaryTable);
        onCreate(db);
    }
}
