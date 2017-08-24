package com.codepath.todoapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by rashmisharma on 8/17/17.
 */

public class ReminderDataBaseHandler extends SQLiteOpenHelper {
    // Database Info
    private static final String DATABASE_NAME = "tododb";
    private static final int DATABASE_VERSION = 4;

    // Table Names
    private static final String TABLE_REMINDERS = "reminders";

    // Post Table Columns
    private static final String KEY_ID = "id";
    private static final String NAME = "name";
    private static final String DESCRIPTION = "description";
    private static final String PRIORITY = "priority";
    private static final String DATE = "date";




    private static ReminderDataBaseHandler sInstance;

    public static synchronized ReminderDataBaseHandler getInstance(Context context) {
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = new ReminderDataBaseHandler(context.getApplicationContext());
        }
        return sInstance;
    }

    /**
     * Constructor should be private to prevent direct instantiation.
     * Make a call to the static method "getInstance()" instead.
     */
    private ReminderDataBaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Called when the database connection is being configured.
    // Configure database settings for things like foreign key support, write-ahead logging, etc.
    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    // Called when the database is created for the FIRST time.
    // If a database already exists on disk with the same DATABASE_NAME, this method will NOT be called.
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_REMINDER_TABLE = "CREATE TABLE " + TABLE_REMINDERS +
                "(" +
                KEY_ID + " INTEGER PRIMARY KEY," + // Define a primary key

                NAME + " TEXT, " +
                DESCRIPTION + " TEXT, " +
                DATE + " INTEGER, " +
                PRIORITY + " TEXT"+
                ")";
        db.execSQL(CREATE_REMINDER_TABLE);
    }

    // Called when the database needs to be upgraded.
    // This method will only be called if a database already exists on disk with the same DATABASE_NAME,
    // but the DATABASE_VERSION is different than the version of the database that exists on disk.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            // Simplest implementation is to drop all old tables and recreate them
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_REMINDERS);
            onCreate(db);
        }
    }

    // Insert a post into the database
    public void addReminder(Reminder reminder) {
        // Create and/or open the database for writing
        SQLiteDatabase db = getWritableDatabase();

        // It's a good idea to wrap our insert in a transaction. This helps with performance and ensures
        // consistency of the database.
        db.beginTransaction();
        try {
            // The user might already exist in the database (i.e. the same user created multiple posts).

            ContentValues values = new ContentValues();
            values.put(NAME, reminder.getName());
            values.put(DESCRIPTION, reminder.getName());
            values.put(DATE, reminder.getDate().toString());
            values.put(PRIORITY, reminder.getPriority());


            // Notice how we haven't specified the primary key. SQLite auto increments the primary key column.
            db.insertOrThrow(TABLE_REMINDERS, null, values);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to add post to database");
        } finally {
            db.endTransaction();
        }
    }

    // Insert or update a user in the database
    // Since SQLite doesn't support "upsert" we need to fall back on an attempt to UPDATE (in case the
    // user already exists) optionally followed by an INSERT (in case the user does not already exist).
    // Unfortunately, there is a bug with the insertOnConflict method
    // (https://code.google.com/p/android/issues/detail?id=13045) so we need to fall back to the more
    // verbose option of querying for the user's primary key if we did an update.
    public long updateReminder(Reminder reminder) {
        // The database connection is cached so it's not expensive to call getWriteableDatabase() multiple times.
        SQLiteDatabase db = getWritableDatabase();
        long userId = -1;

        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(KEY_ID, reminder.getId());
            values.put(NAME, reminder.getName());

            values.put(DESCRIPTION, reminder.getName());
            values.put(DATE, reminder.getDate().toString());
            values.put(PRIORITY, reminder.getPriority());

            // First try to update the user in case the user already exists in the database
            // This assumes userNames are unique
            int rows = db.update(TABLE_REMINDERS, values, KEY_ID + "= ?", new String[]{reminder.getId()+""});


        } catch (Exception e) {
            Log.d(TAG, "Error while trying to add or update user");
        } finally {
            db.endTransaction();
        }
        return reminder.getId();
    }

    // Get all posts in the database
    public List<Reminder> getAllReminders() {
        List<Reminder> reminders = new ArrayList<>();

        // SELECT * FROM POSTS
        // LEFT OUTER JOIN USERS
        // ON POSTS.KEY_POST_USER_ID_FK = USERS.KEY_USER_ID
        String REMINDER_SELECT_QUERY ="Select * from "+TABLE_REMINDERS;


        // "getReadableDatabase()" and "getWriteableDatabase()" return the same object (except under low
        // disk space scenarios)
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(REMINDER_SELECT_QUERY, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    Reminder reminder = new Reminder();
                    reminder.setName(cursor.getString(cursor.getColumnIndex(NAME)));
                    reminder.setDate(new java.util.Date(cursor.getInt(cursor.getColumnIndex(DATE))));
                    reminder.setPriority(cursor.getString(cursor.getColumnIndex(PRIORITY)));
                    reminder.setId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
                    //reminder.setName(cursor.getString(cursor.getColumnIndex(DESCRIPTION)));
                    reminders.add(reminder);
                } while(cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to get posts from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return reminders;
    }



    // Delete all posts and users in the database
    public void deleteReminder(int id) {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {
            // Order of deletions is important when foreign key relationships exist.
            db.delete(TABLE_REMINDERS,KEY_ID+" = ?", new String[]{id+""});
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to delete all posts and users");
        } finally {
            db.endTransaction();
        }
    }
}
