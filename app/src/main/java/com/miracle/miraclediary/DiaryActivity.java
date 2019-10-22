package com.miracle.miraclediary;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.miracle.miraclediary.dialog.HabitEditorDialog;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class DiaryActivity extends BaseCustomBarActivity {
    ListView list1;

    DBHelper helper = new DBHelper(this);
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);
        db = helper.getWritableDatabase();

        sqlGet();
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent regist = new Intent(DiaryActivity.this, EditorActivity.class);
                startActivity (regist);
            }
        });
    }

    @Override
    protected void Init() {

    }

    @Override
    protected void onResume()
    {
        super.onResume();
        sqlGet();
    }
    public void sqlGet()
    {
        // listView
        list1 = (ListView)findViewById(R.id.diaryList);

        ArrayList<HashMap<String,Object>> data_List = new ArrayList<HashMap<String,Object>>();


        String sql = "select * from TestTable2";

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
        String[] keys = {"data1", "data2"};

        int [] ids = {R.id.textView3, R.id.textView2};

        SimpleAdapter adapter = new SimpleAdapter(this, data_List, R.layout.row_goal, keys, ids);
        list1.setAdapter(adapter);
    }


}
