package com.miracle.miraclediary;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MenuActivity extends BaseCustomBarActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);

        DBHelper helper = new DBHelper(this);
        SQLiteDatabase db = helper.getWritableDatabase();
        DBManager.getInstance().setDB(db);
        DBManager.getInstance().updateDB("TestTable");
        DBManager.getInstance().updateDB("TestTable2");
    }

    @Override
    protected void Init() {

    }

    public void goToMain(View v){

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    public void goToEdit(View v){

        Intent intent = new Intent(this, EditorActivity.class);
        startActivity(intent);
    }
    public void goToDiary(View v){

        Intent intent = new Intent(this, DiaryActivity.class);
        startActivity(intent);
    }

}
