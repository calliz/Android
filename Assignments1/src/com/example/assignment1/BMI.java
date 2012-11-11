package com.example.assignment1;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class BMI extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bmi);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_bmi, menu);
		return true;
	}

	/** Called when the user clicks the Reset button */
	public void resetResult(View v) {
		TextView lengthView = (TextView) findViewById(R.id.edit_length);
		TextView weightView = (TextView) findViewById(R.id.edit_weight);
		TextView genView = (TextView) findViewById(R.id.bmi_result);
		lengthView.setText("");
		weightView.setText("");
		genView.setText("");

	}

	/** Called when the user clicks the Generate button */
	public void computeResult(View v) {
		TextView lengthView = (TextView) findViewById(R.id.edit_length);
		TextView weightView = (TextView) findViewById(R.id.edit_weight);
		TextView genView = (TextView) findViewById(R.id.bmi_result);
		double length = 0;
		double weight = 0;
		double bmi = 0;
		try {
			length = Double.parseDouble(String.valueOf(lengthView.getText())) / 100;
			weight = Double.parseDouble(String.valueOf(weightView.getText()));
		} catch (NumberFormatException e) {
			Log.v("Debug", e.getMessage());

		}
		Log.v("Debug", "bmi length: " + length);
		Log.v("Debug", "bmi weight: " + weight);
		
		if (weight != 0 && length != 0) {
			bmi = weight / Math.pow(length, 2);
		}
		Log.v("Debug", "bmi value: " + bmi);
		genView.setText(String.valueOf(String.format("%.2f", bmi)));
	}
}
