package com.example.content_providers;

import java.util.HashMap;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.ContentUris;
import android.content.Context;
import android.content.UriMatcher;

import android.database.Cursor;
import android.database.SQLException;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;

import android.net.Uri;
import android.text.TextUtils;


public class StudentProvider extends ContentProvider{

    static final String PROVIDER_NAME = "com.example.content_providers.StudentProvider";
    static final String URL = "content://" + PROVIDER_NAME + "/students";
    static final Uri CONTENT_URI = Uri.parse(URL);

    static final String _ID = "id";
    static final String NAME = "name";
    static final String GRADE = "grade";

    private static HashMap<String, String> STUDENT_PROJECTION_MAP;

    static final int STUDENTS = 1;
    static final int STUDENT_ID = 2;

    static final UriMatcher uriMatcher;
    static{
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME, "students", STUDENTS);
        uriMatcher.addURI(PROVIDER_NAME, "student/#", STUDENT_ID);
    }

    private SQLiteDatabase db;

    static final String DATABASE_NAME = "college";
    static final String STUDENT_TABLE_NAME = "students";
    static final int DATABASE_VERSION = 1;
    static final String CREATE_STUDENT_TABLE =
            "CREATE TABLE " + STUDENT_TABLE_NAME + "(id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "name TEXT NOT NULL, " + "grade TEXT NOT NULL);";


    private static class DatabaseHelper extends SQLiteOpenHelper{

        DatabaseHelper(Context context){
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db){
            db.execSQL(CREATE_STUDENT_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
            db.execSQL("DROP TABLE if EXISTS " + STUDENT_TABLE_NAME);
        }
    }

    @Override
    public boolean onCreate(){
        Context context = getContext();
        DatabaseHelper dbHelp = new DatabaseHelper(context);

        db = dbHelp.getWritableDatabase();

        return (db == null)? false:true;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values){

        long rowid =  db.insert(STUDENT_TABLE_NAME, "", values);

        if (rowid > 0){
            Uri _uri = ContentUris.withAppendedId(CONTENT_URI, rowid);
            getContext().getContentResolver().notifyChange(_uri, null);
            return uri;
        }
        throw new SQLException("Failed to add record" +uri);
    }

    public Cursor query(Uri uri, String[] projections, String selection, String[] selectionargs, String sortorder){
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(STUDENT_TABLE_NAME);

        switch (uriMatcher.match(uri)) {
            case STUDENTS:
                qb.setProjectionMap(STUDENT_PROJECTION_MAP);
                break;

            case STUDENT_ID:
                qb.appendWhere( _ID + "=" + uri.getPathSegments().get(1));
                break;

            default:
        }

        if (sortorder == null || sortorder == ""){
            /**
             * By default sort on student names
             */
            sortorder = NAME;
        }

        Cursor c = qb.query(db,	projections,	selection,
                selectionargs,null, null, sortorder);
        /**
         * register to watch a content URI for changes
         */
        c.setNotificationUri(getContext().getContentResolver(), uri);
        return c;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int count = 0;
        switch (uriMatcher.match(uri)){
            case STUDENTS:
                count = db.delete(STUDENT_TABLE_NAME, selection, selectionArgs);
                break;

            case STUDENT_ID:
                String id = uri.getPathSegments().get(1);
                count = db.delete( STUDENT_TABLE_NAME, _ID +  " = " + id +
                                (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public int update(Uri uri, ContentValues values,
                      String selection, String[] selectionArgs) {
        int count = 0;
        switch (uriMatcher.match(uri)) {
            case STUDENTS:
                count = db.update(STUDENT_TABLE_NAME, values, selection, selectionArgs);
                break;

            case STUDENT_ID:
                count = db.update(STUDENT_TABLE_NAME, values,
                        _ID + " = " + uri.getPathSegments().get(1) +
                                (!TextUtils.isEmpty(selection) ? " AND (" +selection + ')' : ""), selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri );
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }


    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)) {
            /**
             * Get all student records
             */
            case STUDENTS:
                return "vnd.android.cursor.dir/vnd.example.students";
            /**
             * Get a particular student
             */
            case STUDENT_ID:
                return "vnd.android.cursor.item/vnd.example.students";
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
    }
}


