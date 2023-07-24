package com.example.evoting;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;


public class CandidateDataBaseHelper extends SQLiteOpenHelper {
    public static final String CANDIDATE_TABLE = "CANDIDATE_TABLE";
    public static final String CANDIDATE_ID = "CANDIDATE_ID";
    public static final String CANDIDATE_NAME = "CANDIDATE_NAME";
    public static final String CANDIDATE_VOTES = "VOTES";



    String database;
    public CandidateDataBaseHelper(@Nullable Context context) {
        super(context, "candidate.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "";
        createTableStatement = "CREATE TABLE " + CANDIDATE_TABLE + "(" + CANDIDATE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + CANDIDATE_NAME + " TEXT, " + CANDIDATE_VOTES + " INTEGER)";
        db.execSQL(createTableStatement); 
    }



    public List<CandidateModel> getCandidates() {
        List<CandidateModel> returnList = new ArrayList<>();
        String queryString = "SELECT * FROM " + CANDIDATE_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst()){
            do{
                String username = cursor.getString(1);
                returnList.add(new CandidateModel(username, false));
            } while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return returnList;
    }

    public void incrementVote(String candidate){
        int votes = getVotes(candidate) + 1;
        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "UPDATE " + CANDIDATE_TABLE + " SET " + CANDIDATE_VOTES + " = " + votes + " WHERE " + CANDIDATE_NAME  + " = '" + candidate + "'";
        db.execSQL(queryString);
        db.close();

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }



    public boolean addCandidate(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(CANDIDATE_NAME, name);
        cv.put(CANDIDATE_VOTES, 0);

        long insert = db.insert(CANDIDATE_TABLE, null, cv);

        return insert>0;
    }

    public int getVotes(String name){
        String queryString = "SELECT * FROM CANDIDATE_TABLE WHERE CANDIDATE_NAME ='" + name + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        int returnVote = 0;
        if(cursor.moveToFirst()){
            do{
                returnVote = cursor.getInt(2);
            } while(cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return returnVote;
    }

    public List<String> getTotalVotes(){
        String queryString = "SELECT * FROM " + CANDIDATE_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        List<String> returnList = new ArrayList<String>();
        String addition = "";
        if(cursor.moveToFirst()){
            do{
                addition = cursor.getString(1) + "   " + cursor.getInt(2);
                returnList.add(addition);
            } while(cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return returnList;
    }

    public void deleteTable(){
        String queryString = "DROP TABLE " + CANDIDATE_TABLE;
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(queryString);
        onCreate(db);
        db.close();
    }

}


