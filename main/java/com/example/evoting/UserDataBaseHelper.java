package com.example.evoting;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;


public class UserDataBaseHelper extends SQLiteOpenHelper {
    public static final String USER_TABLE = "USER_TABLE";
    public static final String USER_ID = "USER_ID";
    public static final String USERNAME = "USERNAME";
    public static final String PASSWORD = "PASSWORD";
    public static final String PHONE_NUM = "PHONE_NUMBER";
    public static final String VOTED = "VOTED";


    public String database;

    public UserDataBaseHelper(@Nullable Context context) {
        super(context, "user.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "";
        createTableStatement = "CREATE TABLE " + USER_TABLE + "(" + USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + USERNAME + " TEXT, " + PASSWORD + " TEXT, " + PHONE_NUM + " TEXT, " + VOTED + " BOOL)";
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
        String queryString = "SELECT * FROM USER_TABLE WHERE USERNAME = '" + givenUsername + "'";
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



    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void setVotedTrue(String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "UPDATE " + USER_TABLE + " SET " + VOTED + " = TRUE WHERE " + USERNAME  + " = '" + username + "'";
        db.execSQL(queryString);
        db.close();
    }

    public void setAllVotedFalse(){
        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "UPDATE " + USER_TABLE + " SET " + VOTED + " = FALSE WHERE " + VOTED  + " = TRUE ";
        db.execSQL(queryString);
        db.close();
    }

    public boolean hasVoted(String username){
        SQLiteDatabase db = this.getReadableDatabase();
        String queryString = "SELECT * FROM USER_TABLE WHERE USERNAME = '" + username + "'";
        Cursor cursor = db.rawQuery(queryString, null);
        boolean hasVoted = false;
        if(cursor.moveToFirst()){
            do{
                hasVoted = cursor.getInt(4) > 0;
            } while(cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return hasVoted;
    }
}


