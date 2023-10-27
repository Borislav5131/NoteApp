package com.example.noteapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    private Context context;
    private static final String DATABASE_NAME = "NoteApp.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "note";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_TITLE = "note_title";
    private static final String COLUMN_CONTENT = "note_content";
    private static final String COLUMN_STATUS = "note_status";

    private NoteAppApi api;

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
        api = new NoteAppApi();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query =
                "CREATE TABLE " + TABLE_NAME +
                        " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_TITLE + " TEXT, " +
                        COLUMN_CONTENT + " TEXT, " +
                        COLUMN_STATUS + " TEXT);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void AddNote(String title, String description, String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_TITLE, title);
        contentValues.put(COLUMN_CONTENT, description);
        contentValues.put(COLUMN_STATUS, status);

        long result = db.insert(TABLE_NAME, null, contentValues);

        if (result == -1) {
            Toast.makeText(context, "Failed adding a note!", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context, "Successfully added a note!", Toast.LENGTH_LONG).show();
        }

        api.createNote(title, description, status, new NoteAppApi.ApiCallback() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(context, "Successfully added a note to API!", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Exception e) {
                Toast.makeText(context, "Failed adding a note to API!", Toast.LENGTH_LONG).show();
            }
        });
    }

    public Cursor ReadData() {
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = null;
        if(db != null) {
            cursor = db.rawQuery(query, null);
        }

        api.getNotes(new NoteAppApi.ApiCallback() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(context, "Successfully read notes from API!", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Exception e) {
                Toast.makeText(context, "Failed to read notes from API!", Toast.LENGTH_LONG).show();
            }
        });

        return cursor;
    }

    void UpdateData(Note note){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TITLE, note.Title);
        cv.put(COLUMN_CONTENT, note.Description);
        cv.put(COLUMN_STATUS, note.Status);

        long result = db.update(TABLE_NAME, cv, "_id=?", new String[]{note.Id.toString()});

        if (result == -1){
            Toast.makeText(context, "Failed to update!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Successfully updated!", Toast.LENGTH_SHORT).show();
        }

        api.updateNote(note.Id, note.Title, note.Description, note.Status, new NoteAppApi.ApiCallback() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(context, "Successfully updated to API!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Exception e) {
                Toast.makeText(context, "Failed to update to API!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    void Delete(String row_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, "_id=?", new String[] {row_id});

        if (result == -1){
            Toast.makeText(context, "Failed to deleted!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Successfully deleted!", Toast.LENGTH_SHORT).show();
        }

        api.deleteNote(Integer.parseInt(row_id), new NoteAppApi.ApiCallback() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(context, "Successfully deleted from API!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Exception e) {
                Toast.makeText(context, "Failed to deleted from API!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
