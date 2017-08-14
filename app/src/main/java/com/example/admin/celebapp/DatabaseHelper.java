package com.example.admin.celebapp;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "MyDatabase";
    private static final int DATABASE_VERSION = 4;

    public static final String TABLE_NAME = "Celebs";
    public static final String FIRST_NAME = "FirstNames";
    public static final String LAST_NAME = "LastNames";
    public static final String HEIGHT = "Height";
    public static final String AGE = "Age";
    public static final String DESCRIPTION = "Description";
    public static final String PICTURE = "Picture";
    //put picture here
    public static final String TAG = "DatabaseHelper";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
                FIRST_NAME + " TEXT , " +
                LAST_NAME + " TEXT, " +
                HEIGHT + " TEXT, " +
                AGE + " TEXT, " +
                DESCRIPTION + " TEXT, " +
                PICTURE + " BLOB, " +
                "PRIMARY KEY (" + FIRST_NAME + ", " + LAST_NAME + "))";
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public long saveNewCelebEntry(celebEntry entry) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(FIRST_NAME, entry.getFirstName());
        contentValues.put(LAST_NAME, entry.getLastName());
        contentValues.put(HEIGHT, entry.getHeight());
        contentValues.put(AGE, entry.getAge());
        contentValues.put(DESCRIPTION, entry.getDescription());
        contentValues.put(PICTURE, entry.getPicture());
        long saved = database.insert(TABLE_NAME, null, contentValues);
        Log.d(TAG, "saveNewCelebEntry: ");
        return saved;
    }

    public boolean checkIfEntryExits(String firstName, String lastName) {
        SQLiteDatabase db = this.getReadableDatabase();
        String Query = "SELECT * FROM " + TABLE_NAME + " WHERE "
                + FIRST_NAME + " = '" + firstName + "' AND "
                + LAST_NAME + " = '" + lastName + "'";
        Cursor cursor = db.rawQuery(Query, null);
        if (cursor.getCount() <= 0) {
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }

    public boolean updateEntry(celebEntry entry) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(FIRST_NAME, entry.getFirstName());
        cv.put(LAST_NAME, entry.getLastName());
        cv.put(HEIGHT, entry.getHeight());
        cv.put(AGE, entry.getAge());
        cv.put(DESCRIPTION, entry.getDescription());
        cv.put(PICTURE, entry.getPicture());
        db.update(TABLE_NAME, cv,
                FIRST_NAME + " = '" + entry.getFirstName() + "' AND " +
                        LAST_NAME + " = '" + entry.getLastName() + "'", null);
        return true;
    }

    public Integer deleteEntry(String firstName, String lastName) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, FIRST_NAME + " = '" + firstName + "' AND " +
                LAST_NAME + " = '" + lastName + "'", null);
    }

    public ArrayList<celebEntry> getEntries() {
        SQLiteDatabase database = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        ArrayList<celebEntry> entryList = new ArrayList<>();
        Cursor cursor = database.rawQuery(query, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                celebEntry entry = new celebEntry(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getBlob(5));
                entryList.add(entry);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return entryList;
    }

    public celebEntry getEntry(String firstName, String lastName) {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT * FROM " + TABLE_NAME + " WHERE "
                + FIRST_NAME + " = '" + firstName + "' AND "
                + LAST_NAME + " = '" + lastName + "'";

        Cursor cursor = db.rawQuery(query, null);
        if(cursor != null && cursor.moveToFirst()) {
            return new celebEntry(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getBlob(5));
        }
        else{
            return new celebEntry("","","","","", null);
        }
    }

}