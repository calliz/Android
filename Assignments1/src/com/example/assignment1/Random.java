package com.example.assignment1;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class Random extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_random);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_random, menu);
		return true;
	}

	/** Called when the user clicks the Generate button */
	public void generateValue(View v) {
		TextView view = (TextView) findViewById(R.id.generated_value);
		int value = (int) Math.ceil(Math.random() * 99) + 1;
		view.setText(String.valueOf(value));

	}
}
