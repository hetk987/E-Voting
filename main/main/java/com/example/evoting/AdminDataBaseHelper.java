package com.example.evoting;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;


public class AdminDataBaseHelper extends SQLiteOpenHelper {
    public static final String USER_TABLE = "USER_TABLE";
    public static final String USER_ID = "USER_ID";
    public static final String USERNAME = "USERNAME";
    public static final String PASSWORD = "PASSWORD";
    public static final String PHONE_NUM = "PHONE_NUMBER";
    public static final String VOTED = "VOTED";

    public static final String ADMIN_TABLE = "ADMIN_TABLE";
    public static final String ADMIN_ID = "ADMIN_ID";
    public static final String ADMIN_USERNAME = "ADMIN_USERNAME";
    public static final String ADMIN_PASSWORD = "ADMIN_PASSWORD";
    public static final String ADMIN_PHONE_NUM = "ADMIN_PHONE_NUMBER";

    public static final String CANDIDATE_TABLE = "CANDIDATE_TABLE";
    public static final String CANDIDATE_ID = "CANDIDATE_ID";
    public static final String CANDIDATE_NAME = "CANDIDATE_NAME";
    public static final String CANDIDATE_VOTES = "VOTES";



    String database;
    public AdminDataBaseHelper(@Nullable Context context) {
        super(context, "admin.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement;
        createTableStatement = "CREATE TABLE " + ADMIN_TABLE + "(" + ADMIN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + ADMIN_USERNAME + " TEXT, " + ADMIN_PASSWORD + " TEXT, " + ADMIN_PHONE_NUM + " INTEGER)";
        db.execSQL(createTableStatement);

    }

    public boolean addOneUser(UserModel userModel){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(USERNAME, userModel.getUsername());
        cv.put(PASSWORD, userModel.getPassword());
        cv.put(PHONE_NUM, userModel.getPhone());
        cv.put(VOTED, userModel.isHasVoted());

        long insert = db.insert(USER_TABLE, null, cv);

        return insert>0;
    }

    public List<String> getUsernames(){
        List<String> returnList = new ArrayList<>();
        String queryString = "SELECT * FROM " + USER_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst()){
            do{
                String username = cursor.getString(1);
                returnList.add(username);
            } while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return returnList;
    }

    public String getPassword(String givenUsername){
        String queryString = "SELECT * FROM USER_TABLE WHERE USERNAME ='" + givenUsername + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        String returnPassword = "";
        if(cursor.moveToFirst()){
            do{
                returnPassword = cursor.getString(2);
            } while(cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return returnPassword;
    }

    public List<String> getCandidates() {
        List<String> returnList = new ArrayList<>();
        String queryString = "SELECT * FROM " + CANDIDATE_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst()){
            do{
                String username = cursor.getString(1);
                returnList.add(username);
            } while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return returnList;
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }



    public boolean addCandidate(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(CANDIDATE_NAME, name);
        cv.put(CANDIDATE_VOTES, 0);

        long insert = db.insert(USER_TABLE, null, cv);

        return insert>0;
    }
}


