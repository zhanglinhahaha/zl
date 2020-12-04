package com.miniapp.account.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.miniapp.account.LogUtil;

/**
 * Created by zl on 20-4-30.
 */
public class AccountItemDb extends SQLiteOpenHelper {
    private static final String TAG = "AccountItemDb";
    private static final String DATABASE_NAME = "AccountItemDb.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_ACCOUNT = "account";

    public static final String ID = "_id";
    public static final String ACCOUNT_ITEM_USERNAME = "username";
    public static final String ACCOUNT_ITEM_PRICE = "price";
    public static final String ACCOUNT_ITEM_DATE = "date";
    public static final String ACCOUNT_ITEM_COMMENT = "comment";

    private Context mContext = null;
    private SQLiteDatabase database = this.getWritableDatabase();
    private Cursor cursor = null;

    public static final String CREATE_ACCOUNT = "create table " + TABLE_ACCOUNT + " ("
            + ID + " integer primary key autoincrement, "
            + ACCOUNT_ITEM_USERNAME + " text, "
            + ACCOUNT_ITEM_PRICE + " DOUBLE, "
            + ACCOUNT_ITEM_DATE + " text, "
            + ACCOUNT_ITEM_COMMENT + " text)";

    public AccountItemDb(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_ACCOUNT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_ACCOUNT + ";");
        onCreate(db);
    }

    public void insert(ContentValues values) {
        database.insert(TABLE_ACCOUNT, null, values);
        LogUtil.d(TAG, "insert values:");
    }

    public void update(ContentValues values, Integer id){
        database.update(TABLE_ACCOUNT, values, ID + " = ?",
                new String[]{""+id});
        LogUtil.d(TAG, "update: " + id);
    }

    public void delete(Integer id){
        database.delete(TABLE_ACCOUNT, ID + " = ?",
                new String[]{""+id});
        LogUtil.d(TAG, "delete: " + id);
    }

    public void deleteAll() {
        database.execSQL("delete from " + TABLE_ACCOUNT);
        LogUtil.d(TAG, "deleteAll()");
    }

    public Cursor query(Integer id) {
        Cursor cursor = database.query(TABLE_ACCOUNT,null, ID + " = ?", new String[] {""+id}, null, null, null);
        if(cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public Cursor getCursor() {
        Cursor cursor = database.query(TABLE_ACCOUNT, null, null ,
                null, null, null, null);
        return cursor;
    }
}
