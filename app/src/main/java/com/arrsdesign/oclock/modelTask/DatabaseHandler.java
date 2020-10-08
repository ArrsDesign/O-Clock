package com.arrsdesign.oclock.modelTask;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    public static final int VERSION = 1;
    public static final String NAME = "SubTaskDatabase";
    public static final String SUB_TABLE = "sub";
    public static final String ID = "id";
    public static final String TASK = "task";
    public static final String STATUS = "status";
    public static final String CREATE_SUB_TABLE = "CREATE TABLE" + SUB_TABLE + "(" + "INTEGER PRIMARY KEY AUTOINCREMENT, " + TASK + "TEXT, " + STATUS + "INTEGER)";

    private SQLiteDatabase db;

    public DatabaseHandler(Context context){
        super(context, NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_SUB_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Drop the older tables
        db.execSQL("DROP TABLE IF EXISTS" + SUB_TABLE);
        //Create Table Again
        onCreate(db);
    }
    public void openDatabase(){
        db = this.getWritableDatabase();
    }
    public void insertTask(SubTaskModel task){
        ContentValues cv = new ContentValues();
        cv.put(TASK, task.getTask());
        cv.put(STATUS, 0);
        db.insert(SUB_TABLE, null, cv);
    }

    public List<SubTaskModel> getAllTasks(){
        List<SubTaskModel> taskList = new ArrayList<>();
        Cursor cur = null;
        db.beginTransaction();
            try {
                cur = db.query(SUB_TABLE, null, null, null, null, null, null, null);
                if (cur != null) {
                    if (cur.moveToFirst()) {
                        do {
                            SubTaskModel task = new SubTaskModel();
                            task.setId(cur.getInt(cur.getColumnIndex(ID)));
                            task.setTask(cur.getString(cur.getColumnIndex(TASK)));
                            task.setStatus(cur.getInt(cur.getColumnIndex(STATUS)));
                            taskList.add(task);
                        } while (cur.moveToNext());
                    }
                }
            }finally {
                db.endTransaction();
                cur.close();

            }
            return taskList;
    }
    public void updateStatus(int id, int status){
        ContentValues cv = new ContentValues();
        cv.put(STATUS, status);
        db.update(SUB_TABLE, cv, ID + "=?", new String[] {String.valueOf(id)});
    }
    public void updateTask(int id, String task){
        ContentValues cv = new ContentValues();
        cv.put(TASK, task);
        db.update(SUB_TABLE, cv, ID + "=?", new String[] {String.valueOf(id)});
    }
    public void deletedTask(int id){
        db.delete(SUB_TABLE, ID +"=?", new String[] {String.valueOf(id)});
    }
}
