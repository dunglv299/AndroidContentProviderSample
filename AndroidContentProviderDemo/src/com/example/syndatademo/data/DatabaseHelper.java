package com.example.syndatademo.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.syndatademo.data.dao.StaffShema;

public class DatabaseHelper extends SQLiteOpenHelper {
	private static final int DATABASE_VERSION = 1;
	public static final String DATABASE_NAME = "staffsync.db";

	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		StaffShema.createTable(database);
	}

	@Override
	public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldv, int newv) {
		sqLiteDatabase
				.execSQL("DROP TABLE IF EXISTS " + StaffShema.TABLE_STAFF);
		onCreate(sqLiteDatabase);
	}
}