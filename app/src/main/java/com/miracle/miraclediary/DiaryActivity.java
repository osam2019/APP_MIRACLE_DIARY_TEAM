package com.miracle.miraclediary;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.miracle.miraclediary.dialog.HabitEditorDialog;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.Html;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class DiaryActivity extends BaseCustomBarActivity {
    ListView list1;
    ArrayList<String> temp; // idx
    ArrayList<String> arrSub;
    ArrayList<String> arrBody;
    DBHelper helper = new DBHelper(this);
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);
        SetActionBarLayout(R.layout.actionbar_prev);
        db = helper.getWritableDatabase();
        DBManager.getInstance().setDB(db);
        DBManager.getInstance().updateDB("TestTable");
        DBManager.getInstance().updateDB("TestTable2");

        // Context Menu 구성
        list1 = (ListView) findViewById(R.id.diaryList);
        registerForContextMenu(list1);
        // Sql
        sqlGet();

        // fab
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent regist = new Intent(DiaryActivity.this, EditorActivity.class);
                regist.putExtra("mode",false);
                startActivity(regist);
            }
        });
    }

    // Context 메뉴 구성
    @Override
    public void onCreateContextMenu(ContextMenu menu,
                                    View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.diary_menu, menu);
    }

    public boolean onContextItemSelected(MenuItem item) {
        String sql = "";
        String args[] = {temp.get(((AdapterView.AdapterContextMenuInfo) item.getMenuInfo()).position)};
        // Log.d("aaa",temp.get(((AdapterView.AdapterContextMenuInfo) item.getMenuInfo()).position));
        switch (item.getItemId()) {

            case R.id.edit:
                Intent edit = new Intent(DiaryActivity.this, EditorActivity.class);
                edit.putExtra("mode",true);
                edit.putExtra("idx",args[0]);
                edit.putExtra("subject",arrSub.get(((AdapterView.AdapterContextMenuInfo) item.getMenuInfo()).position));
                edit.putExtra("body",Html.fromHtml(arrBody.get(((AdapterView.AdapterContextMenuInfo) item.getMenuInfo()).position)).toString());
                startActivity(edit);
                return true;
            case R.id.del:
                sql = "delete from TestTable2 where idx=?";

                db.execSQL(sql, args);
                sqlGet();
                return true;
        }

        return super.onContextItemSelected(item);
    }

    public void sqlGet() {


        ArrayList<HashMap<String, Object>> data_List = new ArrayList<HashMap<String, Object>>();


        String sql = "select * from TestTable2";

        Cursor c = db.rawQuery(sql, null);

        temp = new ArrayList<>();
        arrSub = new ArrayList<>();
        arrBody = new ArrayList<>();


        while (c.moveToNext()) {
            int idx_pos = c.getColumnIndex("idx");
            int textDate = c.getColumnIndex("textDate");
            int textBody = c.getColumnIndex("textBody");
            int textSub = c.getColumnIndex("textSub");

            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("data1", c.getString(textDate));
            map.put("data2", c.getString(textBody));
            map.put("data3", c.getString(textSub));
            // map.put("idx", c.getString(idx_pos));

            arrSub.add(0,c.getString(textSub));
            arrBody.add(0,c.getString(textBody));
            temp.add(0, c.getString(idx_pos));


            data_List.add(0, map);
        }
        Log.d("aaa", "" + temp.size());
        String[] keys = {"data1", "data2", "data3"};

        int[] ids = {R.id.textView, R.id.textView2, R.id.textView3};

        SimpleAdapter adapter = new HighlightSimpleAdapter(this, data_List, R.layout.row_diary, keys, ids);
        list1.setAdapter(adapter);
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
        DBManager.getInstance().updateDB("TestTable");
        DBManager.getInstance().updateDB("TestTable2");
        sqlGet();
    }


}
