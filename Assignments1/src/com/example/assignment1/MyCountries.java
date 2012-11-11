package com.example.assignment1;

import java.util.ArrayList;

import android.app.ActionBar;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;

public class MyCountries extends ListActivity {
	private ArrayList<String> list;
	private ArrayAdapter<String> adapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ArrayList<Map<String, String>> list = buildData();
		// String[] from = { "name", "purpose" };
		// int[] to = { android.R.id.text1, android.R.id.text2 };
		// SimpleAdapter adapter = new SimpleAdapter(this, list,
		// android.R.layout.simple_list_item_2, from, to);
		// setListAdapter(adapter);
		list = new ArrayList<String>();
		list.add("1984 Sweden");
		setContentView(R.layout.activity_my_contry_list);

		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, list);
		setListAdapter(adapter);

		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
	}

	// private ArrayList<Map<String, String>> buildData() {
	// ArrayList<Map<String, String>> list = new ArrayList<Map<String,
	// String>>();
	// list.add(putData("Sweden", "1984"));
	// list.add(putData("Denmark", "1992"));
	// list.add(putData("Norway", "1993"));
	// return list;
	// }

	// private HashMap<String, String> putData(String country, String year) {
	// HashMap<String, String> item = new HashMap<String, String>();
	// item.put("country", country);
	// item.put("year", year);
	// return item;
	//
	// }

	@Override
	// Catch result
	protected void onActivityResult(int requestCode, int resultCode,
			Intent result) {
		if (resultCode == RESULT_OK) {
			String year = result.getStringExtra("year");
			String country = result.getStringExtra("country");
			Log.v("Debug", "country and year: " + country + " " + year);
			list.add(year + " " + country);
			adapter.notifyDataSetChanged();
			// putData(country, year);
		}
	}

	/** Called when the user clicks the Add Country button */
	// public void addCountry(View v) {
	// Intent intent = new Intent(this, AddCountry.class);
	// this.startActivityForResult(intent, 0);
	// }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// An XML based menu specification.
		// See res/menu/action_menu.xml for details
		getMenuInflater().inflate(R.menu.action_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.add_country:
			Intent intent = new Intent(this, AddCountry.class);
			this.startActivityForResult(intent, 0);
			return true;
		case android.R.id.home:
			// app icon in action bar clicked ==> go home
			Intent intent2 = new Intent(this, MainList.class);
			intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent2);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}
