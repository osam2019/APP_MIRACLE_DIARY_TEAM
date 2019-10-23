package com.miracle.miraclediary;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {


    public DBHelper(Context context){
        // 사용할 데이터 베이스 이름
        super(context, "Test.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // 데이터 베이스 생성 (습관)
        String sql = "create table TestTable("
                + "idx integer primary key autoincrement, "
                + "textDate date not null,"
                + "textBody text not null"
                + ")";

        sqLiteDatabase.execSQL(sql);

        // 데이트 베이스 생성 (일기)
        sql = "create table TestTable2("
                + "idx integer primary key autoincrement, "
                + "textDate date not null,"
                + "textBody text not null,"
                + "textSub text not null"
                + ")";

        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        switch (i){
            case 1:

            case 2:

            case 3:
        }
    }
}
