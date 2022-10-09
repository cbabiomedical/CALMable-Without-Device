package com.example.calmable.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SugDB extends SQLiteOpenHelper {


    private static int DB_VERSION = 1;
    private static String DATABASE_NAMESM = "FavDB";
    private static String TABLE_NAMESM = "favoriteTable";
    public static String KEY_IDSM = "id";
    public static String ITEM_TITLESM = "itemTitle";
    public static String ITEM_IMAGESM = "itemImage";
    public static String ITEM_URLSM = "itemUrl";
    public static String FAVORITE_STATUSSM = "fStatus";
    public static String CALMING_INDEXSM = "cIndex";

    // don't forget write this spacesr
    private static String CREATE_TABLE = "CREATE TABLE " + TABLE_NAMESM + "("
            + KEY_IDSM + " TEXT," + ITEM_TITLESM + " TEXT,"
            + ITEM_IMAGESM + " TEXT," + ITEM_URLSM + " TEXT," + CALMING_INDEXSM + " TEXT," + FAVORITE_STATUSSM + " TEXT)";

    public SugDB(Context context) {
        super(context, DATABASE_NAMESM, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    // create empty table
    public void insertEmpty() {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        // enter your value
        for (int x = 1; x < 11; x++) {
            cv.put(KEY_IDSM, x);
            cv.put(FAVORITE_STATUSSM, "0");

            db.insert(TABLE_NAMESM, null, cv);
        }
    }

    // insert data into database
    public void insertIntoTheDatabase(String item_title, String item_image, String id, String fav_status, String itemUrl) {
        SQLiteDatabase db;
        db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(ITEM_TITLESM, item_title);
        cv.put(ITEM_IMAGESM, item_image);
        cv.put(KEY_IDSM, id);
        cv.put(FAVORITE_STATUSSM, fav_status);
        cv.put(ITEM_URLSM, itemUrl);
        db.insert(TABLE_NAMESM, null, cv);
        Log.d("FavDB Status ", item_title + ", favstatus - " + fav_status + " - . " + cv);
    }

    // read all data
    public Cursor read_all_data(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "select * from " + TABLE_NAMESM + " where " + KEY_IDSM + "=" + id + "";
        return db.rawQuery(sql, null, null);
    }

    // remove line from database
    public void remove_fav_music(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "UPDATE " + TABLE_NAMESM + " SET  " + FAVORITE_STATUSSM + " ='0' WHERE " + KEY_IDSM + "=" + id +"" ;
        db.execSQL(sql);
        Log.d("remove ", id.toString());
    }

    // select all favorite list
    public Cursor select_all_favorite_list() {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + TABLE_NAMESM + " WHERE " + FAVORITE_STATUSSM + " ='1'";
        return db.rawQuery(sql, null, null);
    }
}
