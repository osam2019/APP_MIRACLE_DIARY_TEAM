package com.miracle.miraclediary;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabaseCorruptException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class GoalActivity extends AppCompatActivity {

    ListView list1;

    DBHelper helper = new DBHelper(this);
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal);
        db = helper.getWritableDatabase();
        // Toolbar toolbar = findViewById(R.id.toolbar);
        // setSupportActionBar(toolbar);
        sqlGet();

        // Float
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sqlAdd();
            }
        });

    }
    public void sqlGet()
    {
        // listView
        list1 = (ListView)findViewById(R.id.goalList);

        ArrayList<HashMap<String,Object>> data_List = new ArrayList<HashMap<String,Object>>();


        String sql = "select * from TestTable";

        Cursor c = db.rawQuery(sql, null);

        while(c.moveToNext()){
            int idx_pos = c.getColumnIndex("idx");
            int textDate = c.getColumnIndex("textDate");
            int textBody = c.getColumnIndex("textBody");

            HashMap<String,Object> map = new HashMap<String, Object>();
            map.put("data1",c.getString(textDate));
            map.put("data2",c.getString(textBody));

            data_List.add(0,map);


        }

/*
        for (int i = 0; i< data1.length; i++)
        {
            HashMap<String,Object> map = new HashMap<String, Object>();
            map.put("data1",data1[i]);
            map.put("data2",data2[i]);

            data_List.add(map);
        }
*/
        String[] keys = {"data1", "data2"};

        int [] ids = {R.id.textView, R.id.textView2};

        SimpleAdapter adapter = new SimpleAdapter(this, data_List, R.layout.row_goal, keys, ids);
        list1.setAdapter(adapter);
    }
    public void sqlAdd() {
        // Temp DbHelper

        String sql = "insert into TestTable (textDate, textBody) values (?, ?)";

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String data = sdf.format(new Date());
        ListView listview = findViewById(R.id.goalList);
        String [] arg1 = { data, listview.getCount() + "번째 내용입니다."};

        db.execSQL(sql, arg1);
        sqlGet();
    }

}
