package com.example.choisung.emotiondiary;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ChoISung on 2017-06-04.
 */

public class PasswordDBHelper extends SQLiteOpenHelper {
    public PasswordDBHelper(Context context) {
        super(context, "Password", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createPasswordTable =  "CREATE TABLE password (" +
                "pass TEXT);";
        db.execSQL(createPasswordTable);
    }

    //DB업그래이드 필요시 호출(version값에 따라 반응)
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Delete  diary v1 db
        String deletePasswordTable = "DROP TABLE IF EXISTS password";
        db.execSQL(deletePasswordTable);
        onCreate(db);
    }
}
