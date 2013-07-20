/**
 * 
 */
package com.example.syndatademo.data.dao;

import android.content.ContentResolver;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * @author quynhlt
 * 
 */
public class StaffShema {
	// public static final String ID = "staffId";
	public static final String NAME = "name";
	public static final String ADDRESS = "address";
	public static final String TABLE_STAFF = "tblStaff";

	private static final String CREATE_TABLE_STAFF = "CREATE TABLE "
			+ TABLE_STAFF + "(" + BaseColumns._ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT," + NAME + " TEXT," + ADDRESS
			+ " TEXT)";

	public static void createTable(SQLiteDatabase database) {
		database.execSQL(CREATE_TABLE_STAFF);
	}

}
