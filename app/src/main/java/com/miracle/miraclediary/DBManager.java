package com.miracle.miraclediary;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class DBManager {

    public enum TYPE { IDX, DATE, CONTEXT, TEXTSUB };     //   DATE = 일기

    private SQLiteDatabase db = null;

    private HashMap<String, ArrayList<String>> idx = null;
    private HashMap<String, ArrayList<String>> date = null;
    private HashMap<String, ArrayList<String>> context = null;
    private HashMap<String, ArrayList<String>> textSub = null;

    private boolean isNotification = false;

//    private int MorningTime[] = new int[2];
//    private int EveningTime[] = new int[2];

    protected DBManager() {
    }

    public static DBManager getInstance() {
        return SingletonHolder.INSTANCE;
    }

    /**
     * SingletonHolder is loaded on the first execution of Singleton.getInstance()
     * or the first access to SingletonHolder.INSTANCE, not before.
     */
    private static class SingletonHolder {
        public static final DBManager INSTANCE = new DBManager();
    }

//    public void SetMorning(int hour, int minute) {
//        MorningTime[0] = hour;
//        MorningTime[1] = minute;
//    }
//
//    public void SetEvening(int hour, int minute) {
//        EveningTime[0] = hour;
//        EveningTime[1] = minute;
//    }

//    public int[] GetMorning() {
//        return MorningTime;
//    }
//
//    public int[] GetEvening() {
//        return EveningTime;
//    }


    public boolean isNotification() {
        return isNotification;
    }

    public void setNotification(boolean notification) {
        isNotification = notification;
    }

    public void setDB(SQLiteDatabase db) {
        if(this.db != null) return;
        this.db = db;

        idx = new HashMap<>();
        date = new HashMap<>();
        context = new HashMap<>();
        textSub = new HashMap<>();
    }

    public ArrayList<String> GetData(String table, DBManager.TYPE type) {
        switch (type) {
            case IDX:
                return idx.get(table);
            case DATE:
                return date.get(table);
            case CONTEXT:
                return context.get(table);
            case TEXTSUB:
                return textSub.get(table);
        }
        return null;
    }

    public int GetLevel() {                                                                 //레벨을 구합니다.
        int level;
        DBManager.getInstance().updateDB("TestTable2");
        ArrayList dates = DBManager.getInstance().GetData("TestTable2", DBManager.TYPE.DATE);
        level = dates.size()/5;
        return  level;

    }

    public int GetContextNum() {                                                            //습관 갯수를 구합니다.
        int contextnum;
        DBManager.getInstance().updateDB("TestTable");
        ArrayList contexts = DBManager.getInstance().GetData("TestTable", DBManager.TYPE.CONTEXT);
        contextnum = contexts.size();
        return contextnum;
    }

    public boolean updateDB(String table) {
        if (db == null) return false;

        String sql = "select * from " + table;

        Cursor c = db.rawQuery(sql, null);

        if(idx.containsKey(table)) {
            idx.get(table).clear();
            date.get(table).clear();
            context.get(table).clear();
            textSub.get(table).clear();
        }
        else{
            idx.put(table, new ArrayList<String>());
            date.put(table, new ArrayList<String>());
            context.put(table, new ArrayList<String>());
            textSub.put(table, new ArrayList<String>());
        }

        ArrayList idx_arr = idx.get(table);
        ArrayList date_arr = date.get(table);
        ArrayList context_arr = context.get(table);
        ArrayList textSub_arr = textSub.get(table);


        while (c.moveToNext()) {
            int idx_pos = c.getColumnIndex("idx");
            int textDate = c.getColumnIndex("textDate");
            int textBody = c.getColumnIndex("textBody");
            int textSub = c.getColumnIndex("textSub");

            idx_arr.add(c.getString(idx_pos));
            date_arr.add(c.getString(textDate));
            context_arr.add(c.getString(textBody));
            if(textSub > -1)
                textSub_arr.add(c.getString(textSub));

        }

        return true;
    }
}
