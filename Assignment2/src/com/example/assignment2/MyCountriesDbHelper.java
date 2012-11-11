package com.example.assignment2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MyCountriesDbHelper extends SQLiteOpenHelper {

	public static final String COUNTRIES_TABLE_NAME = "countries";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_YEAR = "year";
	public static final String COLUMN_COUNTRY = "country";

	private static final String DATABASE_NAME = "countries.db";
	private static final int DATABASE_VERSION = 1;

	private static final String DATABASE_CREATE = "create table "
			+ COUNTRIES_TABLE_NAME + " (" + COLUMN_ID
			+ " integer primary key autoincrement, " + COLUMN_YEAR
			+ " text not null, " + COLUMN_COUNTRY + " text not null);";

	public MyCountriesDbHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(DATABASE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(MyCountriesDbHelper.class.getName(),
				"Upgrading database from version " + oldVersion + " to "
						+ newVersion + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + COUNTRIES_TABLE_NAME);
		onCreate(db);
	}

}
