package com.example.assignment2;

import java.util.List;

import android.app.ActionBar;
import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

public class MyCountries extends ListActivity {
	private CountriesDataSource datasource;
	private List<Country> values;
	private ArrayAdapter<Country> listAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(this);

		// Load simple properties
		int sortProp = prefs.getInt("sortProp", -1);
		boolean checkboxPref = prefs.getBoolean("checkboxPref", false);
		String colorPref = prefs.getString("colorPref", "#FFFFFF");
		String editPref = prefs.getString("editPref", "NULL");

		datasource = new CountriesDataSource(this);
		datasource.open();

		// get all countries
		switch (sortProp) {
		case 0:
			values = datasource.getAllCountriesSortedByYearASC();
			showToast("values sorted by Year ASC");
			break;
		case 1:
			values = datasource.getAllCountriesSortedByCountryASC();
			showToast("values sorted by Country ASC");
			break;
		case 2:
			values = datasource.getAllCountriesSortedByYearDESC();
			showToast("values sorted by Year DESC");
			break;
		case 3:
			values = datasource.getAllCountriesSortedByCountryDESC();
			showToast("values sorted by Country DESC");
			break;
		default:
			values = datasource.getAllCountries();
			showToast("values sorted by default");
			break;
		}

		showToast("sortProp : " + sortProp + "\ncheckboxPref: " + checkboxPref
				+ "\ncolorPref: " + colorPref + "\neditPref: " + editPref);

		// fill ListView with elements
		ListView list = getListView();

		listAdapter = new ArrayAdapter<Country>(this,
				android.R.layout.simple_list_item_1, values);
		setListAdapter(listAdapter);
		registerForContextMenu(list);

		// Use actionBar
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);

		list.setBackgroundColor(Color.parseColor(colorPref));
		showToast("backgroundColor set to: " + Color.parseColor(colorPref));
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
				listAdapter.add(countryToAdd);
				listAdapter.notifyDataSetChanged();
				break;
			case 1:
				// EDIT COUNTRY
				int infoPosition = result.getIntExtra("info.position", -1);
				String updatedYear = result.getStringExtra("year");
				String updatedCountry = result.getStringExtra("country");

				// Fetch the old country from the listAdapter
				Country countryToUpdate = listAdapter.getItem(infoPosition);

				// Update the old country in the database
				if (datasource.updateCountry(countryToUpdate.getId(),
						updatedYear, updatedCountry)) {

					// Update the old country in the listAdapter
					countryToUpdate.setYear(updatedYear);
					countryToUpdate.setCountry(updatedCountry);

					listAdapter.notifyDataSetChanged();
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
			listAdapter.clear();
			listAdapter.addAll(datasource.getAllCountriesSortedByYearASC());
			setSortProp(0);
			listAdapter.notifyDataSetChanged();
			return true;
		case R.id.sort_by_country_asc:
			listAdapter.clear();
			listAdapter.addAll(datasource.getAllCountriesSortedByCountryASC());
			setSortProp(1);
			listAdapter.notifyDataSetChanged();
			return true;
		case R.id.sort_by_year_desc:
			listAdapter.clear();
			listAdapter.addAll(datasource.getAllCountriesSortedByYearDESC());
			setSortProp(2);
			listAdapter.notifyDataSetChanged();
			return true;
		case R.id.sort_by_country_desc:
			listAdapter.clear();
			listAdapter.addAll(datasource.getAllCountriesSortedByCountryDESC());
			setSortProp(3);
			listAdapter.notifyDataSetChanged();
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
		datasource.open();
		super.onResume();
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
		menu.setHeaderTitle(values.get(info.position).toString());
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
			intentEdit.putExtra("item.year", listAdapter.getItem(info.position)
					.getYear());
			intentEdit.putExtra("item.country",
					listAdapter.getItem(info.position).getCountry());

			this.startActivityForResult(intentEdit, 1);
			return true;
		case 1:
			// DELETE
			Country country = listAdapter.getItem(info.position);

			if (!datasource.deleteCountry(country)) {
				showToast("error deleting country in database!");
			} else {
				listAdapter.remove(country);
				listAdapter.notifyDataSetChanged();
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
}
