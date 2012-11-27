package com.example.assignment2;

import java.util.ArrayList;
import java.util.List;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MyCountries extends Activity {
	private CountriesDataSource datasource;
	private List<Country> countryList;
	private CountryAdapter countryAdapter;
	private ListView countryListView;

	// Properties
	private int sortProp;
	private boolean checkboxPref;
	private String textColorPref;
	private String backgroundColorPref;
	private String editPref;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.mycountries_main_layout);

		PreferenceManager.setDefaultValues(this, R.xml.preferences, false);

		loadSimpleProperties();

		openDatabase();

		countryList = new ArrayList<Country>();

		countryAdapter = new CountryAdapter(this,
				R.layout.mycountries_row_layout, countryList);

		sortAndFetchCountriesFromDatabase();

		setupAdapter();

		if (countryList != null && !countryList.isEmpty()) {
			countryAdapter.notifyDataSetChanged();
			countryAdapter.clear();
			for (int i = 0; i < countryList.size(); i++) {
				countryAdapter.add(countryList.get(i));
			}
		}

		countryAdapter.notifyDataSetChanged();

		// Use actionBar
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
	}

	private void openDatabase() {
		datasource = new CountriesDataSource(this);
		datasource.open();
	}

	private void setupAdapter() {
		countryListView = (ListView) findViewById(R.id.country_list);
		countryListView.setAdapter(countryAdapter);
		registerForContextMenu(countryListView);

	}

	private void handleBackgroundColorProperties() {
		// Handle background color prefs
		View mainView = findViewById(R.id.country_main_layout);
		mainView.setBackgroundColor(Color.parseColor(backgroundColorPref));
	}

	private int handleTextColorProperties() {
		int list_text_color = -1;

		showToast("textColorPref set to: " + textColorPref);

		if (textColorPref.equals("#FFFFFF")) {
			list_text_color = R.id.list_text_white;
		} else if (textColorPref.equals("#FFFF00")) {
			list_text_color = R.id.list_text_yellow;
		} else if (textColorPref.equals("#FF00FF")) {
			list_text_color = R.id.list_text_fuchsia;
		} else if (textColorPref.equals("#FF0000")) {
			list_text_color = R.id.list_text_red;
		} else if (textColorPref.equals("#C0C0C0")) {
			list_text_color = R.id.list_text_silver;
		} else if (textColorPref.equals("#808080")) {
			list_text_color = R.id.list_text_gray;
		} else if (textColorPref.equals("#808000")) {
			list_text_color = R.id.list_text_olive;
		} else if (textColorPref.equals("#800080")) {
			list_text_color = R.id.list_text_purple;
		} else if (textColorPref.equals("#800000")) {
			list_text_color = R.id.list_text_maroon;
		} else if (textColorPref.equals("#00FFFF")) {
			list_text_color = R.id.list_text_aqua;
		} else if (textColorPref.equals("#00FF00")) {
			list_text_color = R.id.list_text_lime;
		} else if (textColorPref.equals("#008080")) {
			list_text_color = R.id.list_text_teal;
		} else if (textColorPref.equals("#008000")) {
			list_text_color = R.id.list_text_green;
		} else if (textColorPref.equals("#0000FF")) {
			list_text_color = R.id.list_text_blue;
		} else if (textColorPref.equals("#000080")) {
			list_text_color = R.id.list_text_navy;
		} else if (textColorPref.equals("#000000")) {
			list_text_color = R.id.list_text_black;
		} else {
			list_text_color = R.id.list_text_black;

		}

		showToast("list_text_color set to: " + list_text_color);
		return list_text_color;
	}

	private void sortAndFetchCountriesFromDatabase() {
		switch (sortProp) {
		case 0:
			countryList = datasource.getAllCountriesSortedByYearASC();
			// showToast("values sorted by Year ASC");
			break;
		case 1:
			countryList = datasource.getAllCountriesSortedByCountryASC();
			// showToast("values sorted by Country ASC");
			break;
		case 2:
			countryList = datasource.getAllCountriesSortedByYearDESC();
			// showToast("values sorted by Year DESC");
			break;
		case 3:
			countryList = datasource.getAllCountriesSortedByCountryDESC();
			// showToast("values sorted by Country DESC");
			break;
		default:
			countryList = datasource.getAllCountries();
			// showToast("values sorted by default");
			break;
		}
	}

	private void loadSimpleProperties() {
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(this);
		checkboxPref = prefs.getBoolean("checkboxPref", false);
		sortProp = prefs.getInt("sortProp", -1);
		backgroundColorPref = prefs.getString("backgroundColorPref", "NULL");
		textColorPref = prefs.getString("textColorPref", "NULL");
		editPref = prefs.getString("editPref", "NULL");
	}

	@Override
	// Catch result from intents
	protected void onActivityResult(int requestCode, int resultCode,
			Intent result) {
		if (resultCode == RESULT_OK) {
			switch (requestCode) {
			case 0:
				// ADD COUNTRY
				String newYear = result.getStringExtra("year");
				String newCountry = result.getStringExtra("country");

				// Save the new country to the database
				Country countryToAdd = datasource.createCountry(newYear,
						newCountry);

				// Add the new country to the listAdapter
				countryAdapter.add(countryToAdd);
				countryAdapter.notifyDataSetChanged();
				break;
			case 1:
				// EDIT COUNTRY
				int infoPosition = result.getIntExtra("info.position", -1);
				String updatedYear = result.getStringExtra("year");
				String updatedCountry = result.getStringExtra("country");

				// Fetch the old country from the listAdapter
				Country countryToUpdate = countryAdapter.getItem(infoPosition);

				// Update the old country in the database
				if (datasource.updateCountry(countryToUpdate.getId(),
						updatedYear, updatedCountry)) {

					// Update the old country in the listAdapter
					countryToUpdate.setYear(updatedYear);
					countryToUpdate.setCountry(updatedCountry);

					countryAdapter.notifyDataSetChanged();
				} else {
					showToast("error updating, database not updated");
				}
				break;
			default:
				break;
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.action_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case R.id.add_country:
			Intent intentAdd = new Intent(this, AddCountry.class);
			this.startActivityForResult(intentAdd, 0);
			return true;
		case R.id.sort_by_year_asc:
			// get all countries by year
			countryAdapter.clear();
			countryAdapter.addAll(datasource.getAllCountriesSortedByYearASC());
			setSortProp(0);
			countryAdapter.notifyDataSetChanged();
			return true;
		case R.id.sort_by_country_asc:
			countryAdapter.clear();
			countryAdapter.addAll(datasource
					.getAllCountriesSortedByCountryASC());
			setSortProp(1);
			countryAdapter.notifyDataSetChanged();
			return true;
		case R.id.sort_by_year_desc:
			countryAdapter.clear();
			countryAdapter.addAll(datasource.getAllCountriesSortedByYearDESC());
			setSortProp(2);
			countryAdapter.notifyDataSetChanged();
			return true;
		case R.id.sort_by_country_desc:
			countryAdapter.clear();
			countryAdapter.addAll(datasource
					.getAllCountriesSortedByCountryDESC());
			setSortProp(3);
			countryAdapter.notifyDataSetChanged();
			return true;
		case R.id.settings:
			startActivity(new Intent(this, CountryPreferenceActivity.class));
			return true;
		case android.R.id.home:
			// app icon in action bar clicked ==> go home
			Intent intentHome = new Intent(this, MainList.class);
			intentHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intentHome);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void setSortProp(int i) {
		// Update sortProp
		SharedPreferences simplePrefs = PreferenceManager
				.getDefaultSharedPreferences(this);

		// Save sortProp
		SharedPreferences.Editor edit = simplePrefs.edit();
		edit.putInt("sortProp", i);

		// Commit changes
		edit.apply();
		showToast("Sort order set to " + i);
	}

	@Override
	protected void onResume() {
		// showToast("onResume");
		super.onResume();
		datasource.open();
		countryAdapter.notifyDataSetChanged();
	}

	@Override
	protected void onDestroy() {
		// showToast("onDestroy");
		datasource.close();
		super.onDestroy();
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);

		// Later, inflate from XML menu instead
		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
		menu.setHeaderTitle(countryList.get(info.position).toString());
		menu.add(0, 0, 0, "Edit");
		menu.add(0, 1, 0, "Delete");
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item
				.getMenuInfo();
		switch (item.getItemId()) {
		case 0:
			// EDIT
			Intent intentEdit = new Intent(this, EditCountry.class);
			intentEdit.putExtra("info.position", info.position);
			intentEdit.putExtra("item.year",
					countryAdapter.getItem(info.position).getYear());
			intentEdit.putExtra("item.country",
					countryAdapter.getItem(info.position).getCountry());

			this.startActivityForResult(intentEdit, 1);
			return true;
		case 1:
			// DELETE
			Country country = countryAdapter.getItem(info.position);

			if (!datasource.deleteCountry(country)) {
				showToast("error deleting country in database!");
			} else {
				countryAdapter.remove(country);
				countryAdapter.notifyDataSetChanged();
			}
			return true;
		default:
			return super.onContextItemSelected(item);
		}
	}

	/* Diagnostics while developing */
	private void showToast(String msg) {
		Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
	}
	
	private class CountryAdapter extends ArrayAdapter<Country> {
		private List<Country> values;
		private Activity context;

		public CountryAdapter(Activity context, int textViewResourceId,
				List<Country> values) {
			super(context, textViewResourceId, values);
			this.context = context;
			this.values = values;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = convertView;
			if (view == null) {
				LayoutInflater vi = (LayoutInflater) context
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				view = vi.inflate(R.layout.mycountries_row_layout, null);
			}
			
			Country country = values.get(position);
			
			if(country != null) {
				handleBackgroundColorProperties();
				
			}

			return super.getView(position, convertView, parent);
		}

	}

}
