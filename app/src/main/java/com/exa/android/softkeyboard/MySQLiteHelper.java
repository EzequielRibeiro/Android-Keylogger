package com.exa.android.softkeyboard;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteHelper extends SQLiteOpenHelper {

	public static final String TABLE_LOGGER = "logger";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_LOGGER = "comment";
	public static final String COLUMN_SCREEN = "screen";
	public static final String COLUMN_DATA = "data";
	public static final String COLUMN_SEND = "send";


	private static final String DATABASE_NAME = "logger.db";
	private static final int DATABASE_VERSION = 1;

	// Database creation sql statement
	private static final String DATABASE_CREATE = "create table "
			+ TABLE_LOGGER +
			"(" + COLUMN_ID + " integer primary key autoincrement, "
			+ COLUMN_SCREEN + " text not null,"
			+ COLUMN_LOGGER + " text not null,"
			+ COLUMN_DATA   + " text not null,"
			+ COLUMN_SEND   + " integer not null"
			+" );";

	public MySQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(DATABASE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(MySQLiteHelper.class.getName(),
				"Upgrading database from version " + oldVersion + " to "
						+ newVersion + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOGGER);
		onCreate(db);
	}

} 
