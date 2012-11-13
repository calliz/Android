package com.example.assignment2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class AddCountry extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_country_layout);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_add_country, menu);
		return true;
	}

	/** Called when the user clicks the add values button */
	public void addValues(View v) {
		EditText countryValue = (EditText) findViewById(R.id.add_country);
		EditText yearValue = (EditText) findViewById(R.id.add_year);

		String country = countryValue.getText().toString();
		String year = yearValue.getText().toString();

		Intent result = new Intent();
		result.putExtra("year", year);
		result.putExtra("country", country);

		// Add key/value pair
		setResult(RESULT_OK, result);

		finish();
		// Close this activity
	}

}
