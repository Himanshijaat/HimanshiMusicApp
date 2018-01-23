package com.example.hp.musicapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

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

    private static final String TABLE_FOLDER_SONG_LIST = "myFolderSongList";

    // Contacts Table Columns names
    private static final String SONG_ID = "id";
    private static final String SONG_TITLE = "title";
    private static final String GENRE="genre";
    private static final String DEITY="deity";
    private static final String IMAGENAME="imagename";

    public DatabaseHelperForSongList(Context context) {
        super(context, DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_SONG_LIST_TABLE = "CREATE TABLE " + TABLE_SONG_LIST + "("
                + SONG_ID + " TEXT," + SONG_TITLE + " TEXT,"+ GENRE + " TEXT,"+ DEITY + " TEXT," + IMAGENAME + " TEXT"+")";
        db.execSQL(CREATE_SONG_LIST_TABLE);




    }
    public void createTable(SQLiteDatabase db){
        String CREATE_FOLDER_SONG_LIST_TABLE = "CREATE TABLE " + TABLE_FOLDER_SONG_LIST + "("
                + SONG_ID + " TEXT," + SONG_TITLE + " TEXT,"+ GENRE + " TEXT,"+ DEITY + " TEXT," + IMAGENAME + " TEXT"+")";
        db.execSQL(CREATE_FOLDER_SONG_LIST_TABLE);
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

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SONG_LIST);
        onCreate(db);
        ContentValues values = new ContentValues();
        for(int j=0;j<bean.size();j++) {
            values.put(SONG_ID, bean.get(j).getTrackId()); // Contact Name
            values.put(SONG_TITLE, bean.get(j).getName());// Contact Phone
            values.put(GENRE,bean.get(j).getGenre());// image ID
            values.put(DEITY,bean.get(j).getDeity());
            values.put(IMAGENAME,bean.get(j).getImageName());

            db.insert(TABLE_SONG_LIST, null, values);//insert in database
        }
        db.close();
    }

    public ArrayList<Main_Bean> getAllSongList() {
        ArrayList<Main_Bean> songList = new ArrayList<Main_Bean>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_SONG_LIST;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Main_Bean bean = new Main_Bean();
                bean.setTrackId(cursor.getString(0));
                bean.setName(cursor.getString(1));
                bean.setGenre(cursor.getString(2));
                bean.setDeity(cursor.getString(3));
                bean.setImageName(cursor.getString(4));
                // Adding contact to list
                songList.add(bean);
            } while (cursor.moveToNext());
        }
        db.close();
        return songList;
    }

    public void addFolderSongList(ArrayList<Main_Bean> bean){
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FOLDER_SONG_LIST);
        createTable(db);
        ContentValues values = new ContentValues();
        for(int j=0;j<bean.size();j++) {
            values.put(SONG_ID, bean.get(j).getTrackId()); // Contact Name
            values.put(SONG_TITLE, bean.get(j).getName());// Contact Phone
            values.put(GENRE,bean.get(j).getGenre());// image ID
            values.put(DEITY,bean.get(j).getDeity());
            values.put(IMAGENAME,bean.get(j).getImageName());

            db.insert(TABLE_FOLDER_SONG_LIST, null, values);//insert in database
        }
        db.close();
    }
    public ArrayList<Main_Bean> getAllFolderSongList(String songName) {
        ArrayList<Main_Bean> songList = new ArrayList<Main_Bean>();
        // Select All Query
        String query="SELECT * FROM " + TABLE_SONG_LIST + " WHERE "+ SONG_TITLE + "=?";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, new String[]{songName});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Main_Bean bean = new Main_Bean();
                bean.setTrackId(cursor.getString(0));
                bean.setName(cursor.getString(1));
                bean.setGenre(cursor.getString(2));
                bean.setDeity(cursor.getString(3));
                bean.setImageName(cursor.getString(4));
                // Adding contact to list
                songList.add(bean);
            } while (cursor.moveToNext());
        }
        db.close();
        return songList;
    }

    // Getting contacts Count
    public int getSongsCount() {
        int count=0;
        String countQuery = "SELECT * FROM " + TABLE_SONG_LIST;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        count=cursor.getCount();
        Log.d("select",cursor.toString());
        cursor.close();
        db.close();

        // return count
        return count;
    }

    public String getImageList(String songId){
        SQLiteDatabase db = this.getWritableDatabase();
        String query="SELECT " + IMAGENAME+" FROM " + TABLE_SONG_LIST + " WHERE "+ SONG_ID + "=?";
        Cursor cursor=db.rawQuery(query,new String[]{songId});
        cursor.moveToFirst();
        String imageName=cursor.getString(0);
        cursor.close();
        return imageName;

    }
}
