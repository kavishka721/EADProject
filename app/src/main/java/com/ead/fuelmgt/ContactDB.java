package com.ead.fuelmgt;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class ContactDB {
    public static final String  KEY_ROWID = "_id";
    public static final String KEY_USERNAME = "username";
    public static final String  KEY_PASSWORD = "password";
    public static final String  KEY_CONFIRM_PASSWORD = "confirmPassword";
    //add a _ for above string

    private final String DATABASE_NAME = "UserDB";
    private final String DATABASE_TABLE = "user";
    private final int DATABASE_VERSION = 1;

    private DBhelper ourHelper;
    private final Context ourContext;
    private SQLiteDatabase ourDatabase;

    public ContactDB(Context context){
        ourContext = context;
    }

    private class DBhelper extends SQLiteOpenHelper{
        public DBhelper(@Nullable Context context) {
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



    }


    public ContactDB open() throws SQLException{
        ourHelper = new DBhelper(ourContext);
        ourDatabase = ourHelper.getWritableDatabase();
        return this;
    }

    public  void close(){
        ourHelper.close();
    }

    public long createEntry(String username, String password){
        ContentValues cv = new ContentValues(); // cv for add key/value pairs
        cv.put(KEY_USERNAME, username);
        cv.put(KEY_PASSWORD, password);
        return ourDatabase.insert(DATABASE_TABLE, null, cv);

    }

    public String getData(){
        String [] columns = new String[] {KEY_ROWID, KEY_USERNAME, KEY_PASSWORD};
        Cursor c = ourDatabase.query(DATABASE_TABLE, columns, null, null, null, null, null);

        String result = "";

        //we want column index to extract data
        int iRowID = c.getColumnIndex(KEY_ROWID);
        int iUserName = c.getColumnIndex(KEY_USERNAME);
        int iPassword = c.getColumnIndex(KEY_PASSWORD);

        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
            //c.isAfterLast() row after the last row
            result = result + c.getString(iRowID) + ": " + c.getString(iUserName) + " " +
                    c.getString(iPassword) + "\n";

        }

        c.close();
        return  result;
    }

    public long deleteEntry(String rowId){
        //delete entry in DATABASE_TABLE where KEY_ROWID = rowID. (? replace with new String[] {rowId})
        return ourDatabase.delete(DATABASE_TABLE, KEY_ROWID + "=?", new String[] {rowId});
    }

    public long updateEntry(String rowID, String name, String phone){

        ContentValues cv = new ContentValues();

        if (!name.isEmpty()){
            cv.put(KEY_USERNAME, name);
        }
        if(!phone.isEmpty()){
            cv.put(KEY_PASSWORD, phone);
        }


        return ourDatabase.update(DATABASE_TABLE, cv, KEY_ROWID + "=?", new String[] {rowID});
    }
}
