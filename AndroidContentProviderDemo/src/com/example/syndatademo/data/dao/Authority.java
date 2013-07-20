package com.example.syndatademo.data.dao;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public class Authority {
	public static final String ITEM_NAME = "staff";
	public static final String CONTENT_AUTHORITY = "dunglv.abcxyz";
	public static final Uri BASE_CONTENT_URI = Uri.parse("content://"
			+ CONTENT_AUTHORITY + "/" + ITEM_NAME);

	public static final class StaffColumn implements BaseColumns {
		public static final String DEFAULT_SORT_ORDER = "_ID ASC";

		public static final int ID_COLUMN_NUMBER = 0;
		public static final int NAME_COLUMN_NUMBER = 1;
		public static final int ADDRESS_COLUMN_NUMBER = 2;
		public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE
				+ "/vnd.basicsyncadapter.entries";
		public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
				+ "/vnd.basicsyncadapter.entry";
	}
}
