package com.example.hp.musicapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SANTOSH on 15-Jan-18.
 */

public class DatabaseHelperForSongList extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "databaseSongList";

    // Contacts table name
    private static final String TABLE_SONG_LIST = "mySongList";

    // Contacts Table Columns names
    private static final String SONG_ID = "id";
    private static final String SONG_TITLE = "title";

    public DatabaseHelperForSongList(Context context) {
        super(context, DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_SONG_LIST_TABLE = "CREATE TABLE " + TABLE_SONG_LIST + "("
                + SONG_ID + " TEXT," + SONG_TITLE + " TEXT" + ")";
        db.execSQL(CREATE_SONG_LIST_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
// Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SONG_LIST);

        // Create tables again
        onCreate(db);
    }


    public void addSong(ArrayList<Main_Bean> bean) {

        SQLiteDatabase db = this.getWritableDatabase();
        if(getSongsCount()<=0) {

            ContentValues values = new ContentValues();
            for(int j=0;j<bean.size();j++) {
                values.put(SONG_ID, bean.get(j).getTrackId()); // Contact Name
                values.put(SONG_TITLE, bean.get(j).getName());// Contact Phone
                db.insert(TABLE_SONG_LIST, null, values);//insert in database
            }
            // Inserting Row

            db.close(); // Closing database connection
        }else {

            db.execSQL("DROP TABLE IF EXISTS " + TABLE_SONG_LIST);
            onCreate(db);
            ContentValues values = new ContentValues();
            for(int j=0;j<bean.size();j++) {
                values.put(SONG_ID, bean.get(j).getTrackId()); // Contact Name
                values.put(SONG_TITLE, bean.get(j).getName());// Contact Phone
                db.insert(TABLE_SONG_LIST, null, values);//insert in database
            }
            db.close();
        }
    }

    public ArrayList<Main_Bean> getAllSongList() {
        ArrayList<Main_Bean> songList = new ArrayList<Main_Bean>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_SONG_LIST;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Main_Bean bean = new Main_Bean();
                bean.setTrackId(cursor.getString(0));
                bean.setName(cursor.getString(1));
                // Adding contact to list
                songList.add(bean);
            } while (cursor.moveToNext());
        }
        return songList;
    }

    // Getting contacts Count
    public int getSongsCount() {
        int count=0;
        String countQuery = "SELECT  * FROM " + TABLE_SONG_LIST;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        count=cursor.getCount();
        cursor.close();

        // return count
        return count;
    }
}
