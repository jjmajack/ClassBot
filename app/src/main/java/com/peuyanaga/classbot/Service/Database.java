package com.peuyanaga.classbot.Service;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by DELL on 2020-05-09.
 */

public class Database extends SQLiteOpenHelper {

    private String table = "USER";
    public Database(Context context){
        super(context, "CLASSBOT_DB", null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        try{
            sqLiteDatabase.execSQL("CREATE TABLE " + table + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, userId INTEGER)");
        } catch (Exception ex){

        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        try{
            sqLiteDatabase.execSQL("DROP TABLE IF EXIST " + table);
            onCreate(sqLiteDatabase);
        } catch (Exception ex){

        }
    }
}