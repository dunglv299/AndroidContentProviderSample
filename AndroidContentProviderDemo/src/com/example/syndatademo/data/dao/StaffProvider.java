/*
 * Copyright 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.syndatademo.data.dao;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.BaseColumns;
import android.text.TextUtils;
import android.util.Log;

import com.example.syndatademo.data.DatabaseHelper;

public class StaffProvider extends ContentProvider {
	private DatabaseHelper mDatabaseHelper;
	public static final String STAFF_BASE_PATH = "staff";
	public static final String AUTHORITY = "dunglv.abcxyz";
	public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY
			+ "/" + STAFF_BASE_PATH);

	private static final int ALL_STAFFS = 1;
	private static final int STAFF_ID = 2;
	public static final String STAFF_TABLE_NAME = "tblStaff";
	public static final String DEFAULT_SORT_ORDER = "_ID ASC";
	public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
			+ "/staff";
	public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE
			+ "/staffs";

	/**
	 * UriMatcher, used to decode incoming URIs.
	 */
	private static UriMatcher sUriMatcher;
	static {
		sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		sUriMatcher.addURI(AUTHORITY, STAFF_BASE_PATH, ALL_STAFFS);
		// use of the hash character indicates matching of an id
		sUriMatcher.addURI(AUTHORITY, STAFF_BASE_PATH + "/#", STAFF_ID);
	}

	@Override
	public boolean onCreate() {
		mDatabaseHelper = new DatabaseHelper(getContext());
		return true;
	}

	/**
	 * Determine the mime type for entries returned by a given URI.
	 */
	@Override
	public String getType(Uri uri) {
		final int match = sUriMatcher.match(uri);
		switch (match) {
		case ALL_STAFFS:
			return CONTENT_TYPE;
		case STAFF_ID:
			return CONTENT_ITEM_TYPE;
		default:
			throw new UnsupportedOperationException("Unknown uri: " + uri);
		}

	}

	/**
	 * Perform a database query by URI.
	 * 
	 * <p>
	 * Currently supports returning all entries (/entries) and individual
	 * entries by ID (/entries/{ID}).
	 */
	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		Log.e("query", "query");
		String orderBy;
		if (TextUtils.isEmpty(sortOrder)) {
			orderBy = DEFAULT_SORT_ORDER;
		} else {
			orderBy = sortOrder;
		}
		int uriMatch = sUriMatcher.match(uri);
		Cursor c;
		switch (uriMatch) {
		case ALL_STAFFS:
			// query the database for all staff
			c = getDb().query(STAFF_TABLE_NAME, projection, selection,
					selectionArgs, null, null, orderBy);
			c.setNotificationUri(getContext().getContentResolver(), CONTENT_URI);
			break;

		case STAFF_ID:
			// Return a single entry, by ID.
			// long staffID = ContentUris.parseId(uri);
			// c = getDb().query(
			// STAFF_TABLE_NAME,
			// projection,
			// BaseColumns._ID
			// + " = "
			// + staffID
			// + (!TextUtils.isEmpty(selection) ? " AND ("
			// + selection + ')' : ""), selectionArgs,
			// null, null, orderBy);
			// c.setNotificationUri(getContext().getContentResolver(),
			// Authority.BASE_CONTENT_URI);
			// break;
		default:
			throw new UnsupportedOperationException("Unknown uri: " + uri);
		}
		return c;
	}

	/**
	 * Insert a new entry into the database.
	 */
	@Override
	public Uri insert(Uri uri, ContentValues initialValues) {
		// Validate the requested uri
		if (sUriMatcher.match(uri) != ALL_STAFFS) {
			throw new IllegalArgumentException("Unknown URI " + uri);
		}

		ContentValues values;
		if (initialValues != null) {
			values = new ContentValues(initialValues);
		} else {
			values = new ContentValues();
		}
		verifyValues(values);
		// insert the initialValues into a new database row
		SQLiteDatabase db = getDb();
		long rowId = db.insert(STAFF_TABLE_NAME, null, values);
		if (rowId > 0) {
			Uri item = ContentUris.withAppendedId(CONTENT_URI, rowId);
			getContext().getContentResolver().notifyChange(item, null);
			Log.e("item insert", item + "");
			return item;
		}

		throw new SQLException("Failed to insert row into " + uri);
	}

	private void verifyValues(ContentValues values) {
		// Make sure that the fields are all set
		if (!values.containsKey(DatabaseHelper.NAME)) {
			Resources r = Resources.getSystem();
			values.put(DatabaseHelper.NAME,
					r.getString(android.R.string.untitled));
		}

		if (!values.containsKey(DatabaseHelper.ADDRESS)) {
			values.put(DatabaseHelper.ADDRESS, "");
		}
	}

	// The delete() method deletes rows based on the seletion or if an id is
	// provided then it deleted a single row. The methods returns the numbers
	// of records delete from the database. If you choose not to delete the data
	// physically then just update a flag here.
	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		SQLiteDatabase db = getDb();
		int deleteCount = 0;
		switch (sUriMatcher.match(uri)) {
		case ALL_STAFFS:
			// do nothing
			break;
		case STAFF_ID:
			String id = uri.getPathSegments().get(1);
			selection = BaseColumns._ID
					+ "="
					+ id
					+ (!TextUtils.isEmpty(selection) ? " AND (" + selection
							+ ')' : "");
			deleteCount = db.delete(STAFF_TABLE_NAME, selection, selectionArgs);
			break;
		default:
			throw new IllegalArgumentException("Unsupported URI: " + uri);
		}

		getContext().getContentResolver().notifyChange(uri, null);
		return deleteCount;
	}

	// The update method() is same as delete() which updates multiple rows
	// based on the selection or a single row if the row id is provided. The
	// update method returns the number of updated rows.
	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		int updateCount = 0;

		switch (sUriMatcher.match(uri)) {
		case ALL_STAFFS:
			// updateCount = getDb().update(STAFF_TABLE_NAME, values, selection,
			// selectionArgs);
			// Do nothing
			break;

		case STAFF_ID:
			String id = uri.getPathSegments().get(1);
			selection = BaseColumns._ID
					+ "="
					+ id
					+ (!TextUtils.isEmpty(selection) ? " AND (" + selection
							+ ')' : "");
			updateCount = getDb().update(STAFF_TABLE_NAME, values, selection,
					selectionArgs);
			break;
		default:
			throw new IllegalArgumentException("Unknown URI " + uri);
		}

		getContext().getContentResolver().notifyChange(uri, null);
		return updateCount;
	}

	private SQLiteDatabase getDb() {
		return mDatabaseHelper.getWritableDatabase();
	}

}
