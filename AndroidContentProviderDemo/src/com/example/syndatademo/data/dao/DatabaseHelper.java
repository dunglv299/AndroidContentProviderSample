package com.example.syndatademo.data.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class DatabaseHelper extends SQLiteOpenHelper {
	private static final int DATABASE_VERSION = 1;
	public static final String DATABASE_NAME = "staffsync.db";

	public static final String NAME = "name";
	public static final String ADDRESS = "address";
	public static final String TABLE_STAFF = "tblStaff";

	private static final String CREATE_TABLE_STAFF = "CREATE TABLE "
			+ TABLE_STAFF + "(" + BaseColumns._ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT," + NAME + " TEXT," + ADDRESS
			+ " TEXT)";

	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(CREATE_TABLE_STAFF);
	}

	@Override
	public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldv, int newv) {
		sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_STAFF);
		onCreate(sqLiteDatabase);
	}
}