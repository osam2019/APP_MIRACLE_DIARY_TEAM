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

import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
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
    ArrayList<String> temp;
    ArrayList<String> mode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal);
        SetActionBarLayout(R.layout.actionbar_prev);
        db = helper.getWritableDatabase();

        // Context Menu 구성
        // Context Menu가 들어간 Listview 는 Switch 와 Checkbox랑 함께 사용할 수 없다. > https://stackoverflow.com/questions/7713945/contextmenu-will-not-appear-on-listview-when-using-simplecursoradapter
        list1 = (ListView) findViewById(R.id.goalList);
        registerForContextMenu(list1);

        sqlGet();
        // Float
        /*
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent regist = new Intent(GoalActivity.this, HabitEditorDialog.class);
                startActivity(regist);
            }
        });
        */


    }

    // Context 메뉴 구성
    @Override
    public void onCreateContextMenu(ContextMenu menu,
                                    View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.goal_menu, menu);
    }

    public boolean onContextItemSelected(MenuItem item) {
        String sql = "";
        String args[] = {temp.get(((AdapterView.AdapterContextMenuInfo) item.getMenuInfo()).position)};
        // Log.d("aaa",temp.get(((AdapterView.AdapterContextMenuInfo) item.getMenuInfo()).position));
        switch (item.getItemId()) {
            case R.id.ach:
                doneGoal(mode.get(((AdapterView.AdapterContextMenuInfo) item.getMenuInfo()).position).equals("00") |
                                mode.get(((AdapterView.AdapterContextMenuInfo) item.getMenuInfo()).position).equals("10")?true:false ,
                        temp.get(((AdapterView.AdapterContextMenuInfo) item.getMenuInfo()).position));
                sqlGet();
                return true;
            case R.id.del:
                sql = "delete from TestTable where idx=?";

                db.execSQL(sql, args);
                sqlGet();
                return true;
        }

        return super.onContextItemSelected(item);
    }
    public void sqlAdd(View view) {
        // Temp DbHelper
        DBHelper helper = new DBHelper(this);
        SQLiteDatabase db = helper.getWritableDatabase();
        String sql = "insert into TestTable (textDate, textBody, mode) values (?, ?, ?)";
        String mode = (((CheckBox)findViewById(R.id.chkImportant)).isChecked() == true) ? "1" : "0";
        mode = mode + ((((CheckBox)findViewById(R.id.chkRepeat)).isChecked() == true) ? "1" : "0");


        String data = "1997-11-24";
        String m_context = ((EditText) findViewById(R.id.content)).getText().toString();
        String [] arg1 = { data, m_context, mode};

        db.execSQL(sql, arg1);
        sqlGet();

    }
    // 목표 달성시
    public void doneGoal(boolean dailyType, String idx)
    {
        String sql;

        if (dailyType == false) {
            sql = "delete from TestTable where idx=?";
            String [] args = { idx };
            db.execSQL(sql, args);

        }
        else {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            String data = sdf.format(new Date());
            sql = "update TestTable set textDate= ? where idx = ?";
            String [] args = { data , idx };
            Log.d("aaa",sql + "/" + data + "/" + idx);
            db.execSQL(sql, args);
        }
                //String sql = "update TestTable2 set textSub = ?, textBody = ? where idx=" + idx;
            ;

    }
    public void sqlGet() {

        ArrayList<HashMap<String, Object>> data_List = new ArrayList<HashMap<String, Object>>();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String data = sdf.format(new Date());

        String sql = "select * from TestTable where textDate != \"" + data + "\"";


        Cursor c = db.rawQuery(sql, null);
        temp = new ArrayList<>();
        mode = new ArrayList<>();
        while (c.moveToNext()) {
            int idx_pos = c.getColumnIndex("idx");
            int textDate = c.getColumnIndex("textDate");
            int textBody = c.getColumnIndex("textBody");
            int textMode = c.getColumnIndex("mode");

            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("data1", c.getString(textDate));
            map.put("data2", c.getString(textBody));
            Log.d("bbb", c.getString(textDate));
            //map.put()

            temp.add(0, c.getString(idx_pos));
            mode.add(0,c.getString(textMode));
            data_List.add(0, map);


        }


        String[] keys = {"data1", "data2"};

        int[] ids = {R.id.textView2, R.id.textView3};
        //if (data_List.size() != 0) {
        SimpleAdapter adapter = new SimpleAdapter(this, data_List, R.layout.row_goal, keys, ids);
        list1.setAdapter(adapter);
        //}
    }

    @Override
    protected void Init() {
        findViewById(R.id.actionbar_prev).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //텍스트를 받아온다.
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        sqlGet();
    }


}
