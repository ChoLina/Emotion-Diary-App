package com.example.choisung.emotiondiary.fragment;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.example.choisung.emotiondiary.PasswordDBHelper;
import com.example.choisung.emotiondiary.R;

public class SettingFragment extends Fragment {

    public static PasswordDBHelper pdbHelper;
    public static SQLiteDatabase db;

    //SettingFragment 전역변수
    public static Switch secretSwitch;
    public static EditText secretEditText;
    public static Button secretSaveBtn;


    public static SettingFragment newInstance() {
        Bundle args = new Bundle();

        SettingFragment fragment = new SettingFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        this.pdbHelper = new PasswordDBHelper(this.getContext());

        secretSwitch = (Switch)view.findViewById(R.id.switch1);
        secretEditText = (EditText)view.findViewById(R.id.secret_editText);
        secretSaveBtn = (Button)view.findViewById(R.id.secret_save_btn);
        secretSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    //On
                    secretEditText.setVisibility(View.VISIBLE);

                }else{
                    //Off
                    secretEditText.setVisibility(View.INVISIBLE);


                }
            }
        });
        secretSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = secretEditText.getText().toString();
                passDB(str);
                Toast.makeText(getActivity(), "save", Toast.LENGTH_SHORT).show();
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    //비밀번호 입력 메소드
    public static void passDB(String str){
        db = pdbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("pass", str);

        db.insert("password",null,values);
        db.close();
        secretEditText.setText("");
    }
    //비밀번호 확인 메소드
    public static String checkPass(){
        db = pdbHelper.getReadableDatabase();
        String sql = "select * from passwd";
        Cursor cursor = db.rawQuery(sql, null);
        String pass="";
        while(cursor.moveToNext()){
            pass = cursor.getString(0);
        }
        cursor.close();
        db.close();
        return pass;
    }


}