package com.example.choisung.emotiondiary.fragment;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.choisung.emotiondiary.DBHelper;
import com.example.choisung.emotiondiary.DiaryText;
import com.example.choisung.emotiondiary.R;
import com.example.choisung.emotiondiary.SentiWordNet;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;


public class StaticsFragment extends Fragment {

    public static PieChart pieChart;
    public static SentiWordNet sw;
    public static DBHelper dbHelper;
    public static SQLiteDatabase db;
    public static Button btn_PieChart;

    private int negtiveCount, positiveCount, neutralCount, sum;
    private String contents, classify;

    private int[] colortable = {0xFFFFB3D9, 0xFFFF80BF, 0xFFFF4DA6,
            0xFFFF1A8C, 0xFFE60073, 0xFFB30059};
//    private int[] positiveColor = ;

    public static StaticsFragment newInstance() {
        Bundle args = new Bundle();

        StaticsFragment fragment = new StaticsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_statics, container, false);
        dbHelper = new DBHelper(getContext());

        pieChart = (PieChart) rootView.findViewById(R.id.chart);
        btn_PieChart = (Button) rootView.findViewById(R.id.btn_PieChart);

        btn_PieChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               InputStream inputStream = getResources().openRawResource(R.raw.swn);
                sw = new SentiWordNet(inputStream);

                getNPCount();

                ArrayList<Entry> entries = new ArrayList<>();
                sum = (positiveCount + neutralCount + negtiveCount);
////                positiveCount/sum
                entries.add(new Entry( (float)positiveCount/sum, 0));
                entries.add(new Entry((float)neutralCount/sum, 1));
                entries.add(new Entry((float)negtiveCount/sum, 2));


                PieDataSet dataset = new PieDataSet(entries, null);

                ArrayList<String> labels = new ArrayList<String>();
                labels.add("positive");
                labels.add("neutral");
                labels.add("negative");

                PieData data = new PieData(labels, dataset);
                dataset.setColors(colortable); //
                pieChart.setDescription("");
                pieChart.setData(data);
                dataset.setValueTextSize(15f);
                pieChart.setCenterText("All Emotion");

                pieChart.animateY(3000);

                pieChart.saveToGallery("/sd/mychart.jpg", 85); // 85 is the quality of the image
            }
        });

        return rootView;
    }

    public void getNPCount() {
        db = dbHelper.getReadableDatabase();
        String sql = "SELECT contents FROM diary";
        Cursor cursor = db.rawQuery(sql, null);

        while ((cursor.moveToNext())) {
            contents = cursor.getString(0);
            classify = sw.classifyContents(contents);
//            Toast.makeText(getContext(), classify, Toast.LENGTH_SHORT).show();

            if (classify == "negative") {
                negtiveCount++;
            } else if (classify == "positive") {
                positiveCount++;
            } else if (classify == "neutral") {
                neutralCount++;
            }
        }

    }
}


//        if(totalScore>=0.75)
//            return "very positive";
//        else if(totalScore >= 0.5 && totalScore<0.75)
//            return  "positive";
//        else if(totalScore>=0.25 && totalScore<0.5)
//            return  "little positive";
//        else if(totalScore < 0 && totalScore>=-0.25)
//            return "little negative";
//        else if(totalScore < -0.25 && totalScore>=-0.75)
//            return "negative";
//        else if(totalScore < -0.75)
//            return "very negative";
//        return "neutral";


