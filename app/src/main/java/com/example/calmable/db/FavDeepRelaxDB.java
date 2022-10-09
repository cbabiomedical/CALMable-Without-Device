package com.example.calmable.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class FavDeepRelaxDB extends SQLiteOpenHelper {


    private static int DB_VERSIONDP = 1;
    private static String DATABASE_NAMEDP = "MusicDR";
    private static String TABLE_NAMEDP = "favoriteTableDR";
    public static String KEY_IDDP = "id";
    public static String ITEM_TITLEDP = "itemTitle";
    public static String ITEM_IMAGEDP = "itemImage";
    public static String FAVORITE_STATUSDP = "fStatus";
    // dont forget write this spaces
    private static String CREATE_TABLEDP = "CREATE TABLE " + TABLE_NAMEDP + "("
            + KEY_IDDP + " TEXT," + ITEM_TITLEDP + " TEXT,"
            + ITEM_IMAGEDP + " TEXT," + FAVORITE_STATUSDP + " TEXT)";

    public FavDeepRelaxDB(Context context) {
        super(context, DATABASE_NAMEDP, null, DB_VERSIONDP);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLEDP);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    // create empty table
    public void insertEmptyDRMusic() {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        // enter your value
        for (int x = 1; x < 11; x++) {
            cv.put(KEY_IDDP, x);
            cv.put(FAVORITE_STATUSDP, "0");

            db.insert(TABLE_NAMEDP, null, cv);
        }
    }

    // insert data into database
    public void insertIntoTheDatabaseDPMusic(String item_title, int item_image, String id, String fav_status) {
        SQLiteDatabase db;
        db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(ITEM_TITLEDP, item_title);
        cv.put(ITEM_IMAGEDP, item_image);
        cv.put(KEY_IDDP, id);
        cv.put(FAVORITE_STATUSDP, fav_status);
        db.insert(TABLE_NAMEDP, null, cv);
        Log.d("FavDB Status DR-DB ", item_title + ", favstatus - " + fav_status + " - . " + cv);
    }

    // read all data
    public Cursor read_all_data_dp_music(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "select * from " + TABLE_NAMEDP + " where " + KEY_IDDP + "=" + id + "";
        return db.rawQuery(sql, null, null);
    }

    // remove line from database
    public void remove_fav_dp_music(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "UPDATE " + TABLE_NAMEDP + " SET  " + FAVORITE_STATUSDP + " ='0' WHERE " + KEY_IDDP + "=" + id + "";
        db.execSQL(sql);
        Log.d("remove DR-DB", id.toString());

    }

    // select all favorite list
    public Cursor select_all_favorite_list_dp_music() {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + TABLE_NAMEDP + " WHERE " + FAVORITE_STATUSDP + " ='1'";
        return db.rawQuery(sql, null, null);
    }


}
