package com.miracle.miraclediary;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class DaliyGoals extends AppCompatActivity {

    // 리스트뷰 항목에 셋팅한 데이터
    String [] data1 = {
            "2019.10.21","2019.10.22","2019.10.23","2019.10.24"
    };

    String [] data2 = {
            "영단어 10개 외우기", "팔굽혀펴기 10회 하기", "인사 3번 먼저 하기", "일기 매일 작성하기"
    };

    ListView list1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daliy_goals);

        list1 = (ListView)findViewById(R.id.goalList);

        ArrayList<HashMap<String,Object>> data_List = new ArrayList<HashMap<String,Object>>();

        for (int i = 0; i< data1.length; i++)
        {
            HashMap<String,Object> map = new HashMap<String, Object>();
            map.put("data1",data1[i]);
            map.put("data2",data2[i]);

            data_List.add(map);
        }

        String[] keys = {"data1", "data2"};

        int [] ids = {R.id.textView, R.id.textView2};

        SimpleAdapter adapter = new SimpleAdapter(this, data_List, R.layout.row_goal, keys, ids);
        list1.setAdapter(adapter);

    }
}
