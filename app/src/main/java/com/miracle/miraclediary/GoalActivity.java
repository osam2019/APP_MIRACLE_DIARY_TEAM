package com.miracle.miraclediary;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabaseCorruptException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.miracle.miraclediary.dialog.HabitEditorDialog;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class GoalActivity extends BaseCustomBarActivity {

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

        // Context Menu 구성
        list1 = (ListView)findViewById(R.id.diaryList);
        registerForContextMenu(list1);

        // Float
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent regist = new Intent(GoalActivity.this, HabitEditorDialog.class);
                startActivity (regist);
            }
        });

    }
    // Context 메뉴 구성
    @Override
    public void onCreateContextMenu(ContextMenu menu,
                                    View v,
                                    ContextMenu.ContextMenuInfo menuInfo)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.diary_menu, menu);
    }

    public boolean onContextItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            case R.id.del:

                return true;
        }

        return super.onContextItemSelected(item);
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

        int [] ids = {R.id.textView3, R.id.textView2};

        SimpleAdapter adapter = new SimpleAdapter(this, data_List, R.layout.row_goal, keys, ids);
        list1.setAdapter(adapter);
    }


}
