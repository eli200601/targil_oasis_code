package com.app.codeoasis.targil.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by eliran.alon on 11-May-17.
 */

public class DBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "database.db";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_CONTACTS = "CREATE TABLE "
                + ContactsTable.TABLE + "("
                + ContactsTable.ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + ContactsTable.KEY_UserID + " TEXT, "
                + ContactsTable.KEY_name + " TEXT, "
                + ContactsTable.KEY_email + " TEXT, "
                + ContactsTable.KEY_address + " TEXT, "
                + ContactsTable.KEY_gender + " TEXT, "
                + ContactsTable.KEY_mobile + " TEXT, "
                + ContactsTable.KEY_home + " TEXT, "
                + ContactsTable.KEY_office + " TEXT)";
        db.execSQL(CREATE_TABLE_CONTACTS);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + ContactsTable.TABLE);

        // Create tables again
        onCreate(db);
    }
}
