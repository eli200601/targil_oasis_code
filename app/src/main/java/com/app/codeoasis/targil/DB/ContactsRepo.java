package com.app.codeoasis.targil.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.app.codeoasis.targil.Recycler.ContactItem;

import java.util.ArrayList;

/**
 * Created by eliran.alon on 11-May-17.
 */

public class ContactsRepo {
    private static String TAG = "ContactsRepo";

    private DBHelper dbHelper;

    public ContactsRepo(Context context) {
        dbHelper = new DBHelper(context);
    }

    public int insert(ContactItem contact) {

        //Open connection to write data
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ContactsTable.KEY_UserID, contact.getId());
        values.put(ContactsTable.KEY_name,contact.getName());
        values.put(ContactsTable.KEY_email, contact.getEmail());
        values.put(ContactsTable.KEY_address,contact.getAddress());
        values.put(ContactsTable.KEY_gender,contact.getGender());
        values.put(ContactsTable.KEY_mobile,contact.getMobile());
        values.put(ContactsTable.KEY_home,contact.getHome());
        values.put(ContactsTable.KEY_office,contact.getOffice());

        // Inserting Row
        long Id = db.insert(ContactsTable.TABLE, null, values);
        db.close(); // Closing database connection
        return (int) Id;
    }

    public void deleteByID(String id) {
        ContactItem item = getContactById(id);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Log.d(TAG, "id = " + id);
        Log.d(TAG, item.getName() + " " + item.getId());



        db.delete(ContactsTable.TABLE, ContactsTable.KEY_UserID + " =?", new String[] { id });
        db.close(); // Closing database connection
    }

    public void delete(int Id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(ContactsTable.TABLE, ContactsTable.ID + " =?", new String[] { String.valueOf(Id) });
        db.close(); // Closing database connection
    }

    public void deleteAll() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(ContactsTable.TABLE, null, null);
        db.close(); // Closing database connection
    }


    public void update(ContactItem contact) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(ContactsTable.KEY_UserID, contact.getId());
        values.put(ContactsTable.KEY_name,contact.getName());
        values.put(ContactsTable.KEY_email, contact.getEmail());
        values.put(ContactsTable.KEY_address,contact.getAddress());
        values.put(ContactsTable.KEY_gender,contact.getGender());
        values.put(ContactsTable.KEY_mobile,contact.getMobile());
        values.put(ContactsTable.KEY_home,contact.getHome());
        values.put(ContactsTable.KEY_office,contact.getOffice());

        db.update(ContactsTable.TABLE, values, ContactsTable.KEY_UserID + "= ?", new String[] { String.valueOf(contact.getId()) });
        db.close(); // Closing database connection
    }

    public ArrayList<ContactItem> getContactListASC() {
        //Open connection to read only
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                ContactsTable.KEY_UserID + "," +
                ContactsTable.KEY_name + "," +
                ContactsTable.KEY_email + "," +
                ContactsTable.KEY_address + "," +
                ContactsTable.KEY_gender + "," +
                ContactsTable.KEY_mobile + "," +
                ContactsTable.KEY_home + "," +
                ContactsTable.KEY_office +
                " FROM " + ContactsTable.TABLE + " ORDER BY " + ContactsTable.KEY_name + " ASC";

        //Student student = new Student();
        ArrayList<ContactItem> contactList = new ArrayList<>();

        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list

        if (cursor.moveToFirst()) {
            do {
                String id = cursor.getString(cursor.getColumnIndex(ContactsTable.KEY_UserID));
                String name = cursor.getString(cursor.getColumnIndex(ContactsTable.KEY_name));
                String email = cursor.getString(cursor.getColumnIndex(ContactsTable.KEY_email));
                String address = cursor.getString(cursor.getColumnIndex(ContactsTable.KEY_address));
                String gender = cursor.getString(cursor.getColumnIndex(ContactsTable.KEY_gender));
                String mobile = cursor.getString(cursor.getColumnIndex(ContactsTable.KEY_mobile));
                String home = cursor.getString(cursor.getColumnIndex(ContactsTable.KEY_home));
                String office = cursor.getString(cursor.getColumnIndex(ContactsTable.KEY_office));

                Log.d(TAG, "### New contact ###");
                Log.d(TAG, "id: " + id + " Name: " + name + " email: " + email + " mobile: " + mobile);
                Log.d(TAG, "#########################################################");

                ContactItem item = new ContactItem(id, name, email, address, gender, mobile, home, office);
                contactList.add(item);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return contactList;

    }

    public ContactItem getContactById(String Id){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                ContactsTable.KEY_UserID + "," +
                ContactsTable.KEY_name + "," +
                ContactsTable.KEY_email + "," +
                ContactsTable.KEY_address + "," +
                ContactsTable.KEY_gender + "," +
                ContactsTable.KEY_mobile + "," +
                ContactsTable.KEY_home + "," +
                ContactsTable.KEY_office +
                " FROM " + ContactsTable.TABLE
                + " WHERE " + ContactsTable.KEY_UserID + "=?";

        int iCount =0;
        ContactItem item = new ContactItem();

        Cursor cursor = db.rawQuery(selectQuery, new String[] { Id } );

        if (cursor.moveToFirst()) {
            do {
                item.setId(cursor.getString(cursor.getColumnIndex(ContactsTable.KEY_UserID)));
                item.setName(cursor.getString(cursor.getColumnIndex(ContactsTable.KEY_name)));
                item.setEmail(cursor.getString(cursor.getColumnIndex(ContactsTable.KEY_email)));
                item.setAddress(cursor.getString(cursor.getColumnIndex(ContactsTable.KEY_address)));
                item.setGender(cursor.getString(cursor.getColumnIndex(ContactsTable.KEY_gender)));
                item.setMobile(cursor.getString(cursor.getColumnIndex(ContactsTable.KEY_mobile)));
                item.setHome(cursor.getString(cursor.getColumnIndex(ContactsTable.KEY_home)));
                item.setOffice(cursor.getString(cursor.getColumnIndex(ContactsTable.KEY_office)));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return item;
    }
}
