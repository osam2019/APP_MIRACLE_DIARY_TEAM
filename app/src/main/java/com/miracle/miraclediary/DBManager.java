package com.miracle.miraclediary;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class DBManager {

    public enum TYPE { IDX, DATE, CONTEXT };

    private SQLiteDatabase db = null;

    private HashMap<String, ArrayList<String>> idx = null;
    private HashMap<String, ArrayList<String>> date = null;
    private HashMap<String, ArrayList<String>> context = null;

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
    }

    public ArrayList<String> GetData(String table, DBManager.TYPE type) {
        switch (type) {
            case IDX:
                return idx.get(table);
            case DATE:
                return date.get(table);
            case CONTEXT:
                return context.get(table);
        }
        return null;
    }

    public boolean updateDB(String table) {
        if (db == null) return false;

        String sql = "select * from " + table;

        Cursor c = db.rawQuery(sql, null);

        if(idx.containsKey(table)) {
            idx.get(table).clear();
            date.get(table).clear();
            context.get(table).clear();
        }
        else{
            idx.put(table, new ArrayList<String>());
            date.put(table, new ArrayList<String>());
            context.put(table, new ArrayList<String>());
        }

        ArrayList idx_arr = idx.get(table);
        ArrayList date_arr = date.get(table);
        ArrayList context_arr = context.get(table);


        while (c.moveToNext()) {
            int idx_pos = c.getColumnIndex("idx");
            int textDate = c.getColumnIndex("textDate");
            int textBody = c.getColumnIndex("textBody");

            idx_arr.add(c.getString(idx_pos));
            date_arr.add(c.getString(textDate));
            context_arr.add(c.getString(textBody));

        }

        return true;
    }
}
