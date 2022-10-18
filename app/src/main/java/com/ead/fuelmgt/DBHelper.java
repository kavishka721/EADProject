package com.ead.fuelmgt;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    public static final String  KEY_ROWID = "_id";
    public static final String KEY_USERNAME = "username";
    public static final String  KEY_PASSWORD = "password";
    public static final String  KEY_CONFIRM_PASSWORD = "confirmPassword";
    //add a _ for above string

    private static final String DATABASE_NAME = "UserDB";
    private static final String DATABASE_TABLE = "user";
    private static final int DATABASE_VERSION = 1;

//    private ContactDB.DBhelper ourHelper;
//    private final Context ourContext;
    private SQLiteDatabase database;

    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    //run when db file did not exist and was just created
    //onCreated only runs when database get created the very first time
    public void onCreate(SQLiteDatabase db) {

        String sqlCode = "CREATE TABLE " + DATABASE_TABLE + " (" +
                KEY_ROWID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                KEY_USERNAME + " TEXT NOT NULL, " +
                KEY_PASSWORD + " TEXT NOT NULL);";

        db.execSQL(sqlCode);
    }

    @Override   //only run if stored version number is lower than requested version number
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
        onCreate(db);
    }

    public boolean checkUsername(String username) {
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery("select * from "+DATABASE_TABLE+" where "+KEY_USERNAME+"=?",new String[]{username});
        if (cursor.getCount()>0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean checkUsernamePassword(String username, String password) {
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery("select * from "+DATABASE_TABLE+" where "+KEY_USERNAME+"=? and "+KEY_PASSWORD+"=?",
                new String[]{username, password});
        if (cursor.getCount()>0) {
            return true;
        } else {
            return false;
        }
    }

    public long createEntry(String username, String password){
        ContentValues cv = new ContentValues(); // cv for add key/value pairs
        cv.put(KEY_USERNAME, username);
        cv.put(KEY_PASSWORD, password);
        return database.insert(DATABASE_TABLE, null, cv);

    }

}
