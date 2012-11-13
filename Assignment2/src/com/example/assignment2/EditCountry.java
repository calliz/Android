package com.example.assignment2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class EditCountry extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_country_layout);

		EditText yearValue = (EditText) findViewById(R.id.edit_year);
		EditText countryValue = (EditText) findViewById(R.id.edit_country);

		String currYear = getIntent().getStringExtra("item.year");
		String currCountry = getIntent().getStringExtra("item.country");

		yearValue.setText(currYear);
		countryValue.setText(currCountry);
	}

	/** Called when the user clicks the edit values button */
	public void editValues(View v) {

		int infoPosition = getIntent().getIntExtra("info.position", -1);

		EditText yearValue = (EditText) findViewById(R.id.edit_year);
		EditText countryValue = (EditText) findViewById(R.id.edit_country);

		if (infoPosition != -1) {
			String country = countryValue.getText().toString();
			String year = yearValue.getText().toString();

			Intent result = new Intent();

			result.putExtra("info.position", infoPosition);
			result.putExtra("year", year);
			result.putExtra("country", country);

			// Add key/value pair
			setResult(RESULT_OK, result);
		} else {
			setResult(RESULT_CANCELED);
		}

		finish();
		// Close this activity
	}
}
