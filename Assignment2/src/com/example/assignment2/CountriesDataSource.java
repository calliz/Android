package com.example.assignment2;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class CountriesDataSource {

	// Database fields
	private SQLiteDatabase database;
	private MyCountriesDbHelper dbHelper;
	private String[] allColumns = { MyCountriesDbHelper.COLUMN_ID,
			MyCountriesDbHelper.COLUMN_YEAR, MyCountriesDbHelper.COLUMN_COUNTRY };

	public CountriesDataSource(Context context) {
		dbHelper = new MyCountriesDbHelper(context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	public Country createCountry(String year, String country) {
		ContentValues values = new ContentValues();
		values.put(MyCountriesDbHelper.COLUMN_YEAR, year);
		values.put(MyCountriesDbHelper.COLUMN_COUNTRY, country);
		long insertId = database.insert(
				MyCountriesDbHelper.COUNTRIES_TABLE_NAME, null, values);
		Cursor cursor = database.query(
				MyCountriesDbHelper.COUNTRIES_TABLE_NAME, allColumns,
				MyCountriesDbHelper.COLUMN_ID + " = " + insertId, null, null,
				null, null);
		cursor.moveToFirst();
		Country newCountry = cursorToCountry(cursor);
		cursor.close();
		return newCountry;
	}

	public boolean deleteCountry(Country country) {
		long id = country.getId();
		System.out.println("Country deleted with id: " + id);
		return (database.delete(MyCountriesDbHelper.COUNTRIES_TABLE_NAME,
				MyCountriesDbHelper.COLUMN_ID + " = " + id, null) > 0);
	}

	public Country getCountry(long countryId) {
		String restrict = MyCountriesDbHelper.COLUMN_ID + "=" + countryId;
		Cursor cursor = database.query(true,
				MyCountriesDbHelper.COUNTRIES_TABLE_NAME, allColumns, restrict,
				null, null, null, null, null);
		if (cursor != null && cursor.getCount() > 0) {
			cursor.moveToFirst();
			Country country = cursorToCountry(cursor);
			return country;
		}
		// Make sure to close the cursor
		cursor.close();
		return null;
	}

	public boolean updateCountry(long countryId, String year, String country) {
		ContentValues args = new ContentValues();
		args.put(MyCountriesDbHelper.COLUMN_YEAR, year);
		args.put(MyCountriesDbHelper.COLUMN_COUNTRY, country);

		String restrict = MyCountriesDbHelper.COLUMN_ID + "=" + countryId;
		return database.update(MyCountriesDbHelper.COUNTRIES_TABLE_NAME, args,
				restrict, null) > 0;
	}

	public List<Country> getAllCountries() {
		List<Country> countries = new ArrayList<Country>();

		Cursor cursor = database.query(
				MyCountriesDbHelper.COUNTRIES_TABLE_NAME, allColumns, null,
				null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Country country = cursorToCountry(cursor);
			countries.add(country);
			cursor.moveToNext();
		}
		// Make sure to close the cursor
		cursor.close();
		return countries;
	}

	public List<Country> getAllCountriesSortedByYearASC() {
		List<Country> countries = new ArrayList<Country>();

		String orderBy = MyCountriesDbHelper.COLUMN_YEAR;

		Cursor cursor = database.query(

		MyCountriesDbHelper.COUNTRIES_TABLE_NAME, allColumns, null, null, null,
				null, orderBy);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Country country = cursorToCountry(cursor);
			countries.add(country);
			cursor.moveToNext();
		}
		// Make sure to close the cursor
		cursor.close();
		return countries;
	}

	public List<Country> getAllCountriesSortedByCountryASC() {
		List<Country> countries = new ArrayList<Country>();

		String orderBy = MyCountriesDbHelper.COLUMN_COUNTRY;

		Cursor cursor = database.query(

		MyCountriesDbHelper.COUNTRIES_TABLE_NAME, allColumns, null, null, null,
				null, orderBy);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Country country = cursorToCountry(cursor);
			countries.add(country);
			cursor.moveToNext();
		}
		// Make sure to close the cursor
		cursor.close();
		return countries;
	}

	public List<Country> getAllCountriesSortedByYearDESC() {
		List<Country> countries = new ArrayList<Country>();

		String orderBy = MyCountriesDbHelper.COLUMN_YEAR + " DESC";

		Cursor cursor = database.query(

		MyCountriesDbHelper.COUNTRIES_TABLE_NAME, allColumns, null, null, null,
				null, orderBy);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Country country = cursorToCountry(cursor);
			countries.add(country);
			cursor.moveToNext();
		}
		// Make sure to close the cursor
		cursor.close();
		return countries;
	}

	public List<Country> getAllCountriesSortedByCountryDESC() {
		List<Country> countries = new ArrayList<Country>();

		String orderBy = MyCountriesDbHelper.COLUMN_COUNTRY + " DESC";

		Cursor cursor = database.query(

		MyCountriesDbHelper.COUNTRIES_TABLE_NAME, allColumns, null, null, null,
				null, orderBy);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Country country = cursorToCountry(cursor);
			countries.add(country);
			cursor.moveToNext();
		}
		// Make sure to close the cursor
		cursor.close();
		return countries;
	}

	private Country cursorToCountry(Cursor cursor) {
		Country country = new Country();
		// Log.v("Debug", "setId: " + cursor.getLong(0));
		country.setId(cursor.getLong(0));
		// Log.v("Debug", "setYear: " + cursor.getString(1));
		country.setYear(cursor.getString(1));
		// Log.v("Debug", "setCountry: " + cursor.getString(2));
		country.setCountry(cursor.getString(2));
		return country;
	}

}
