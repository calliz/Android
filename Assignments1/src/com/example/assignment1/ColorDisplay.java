package com.example.assignment1;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Color;
import android.view.Menu;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ColorDisplay extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_color_display);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_color_display, menu);
		return true;
	}

	/** Called when the user clicks the Display Color button */
	public void changeColor(View v) {
		TextView redView = (TextView) findViewById(R.id.edit_red);
		TextView greenView = (TextView) findViewById(R.id.edit_green);
		TextView blueView = (TextView) findViewById(R.id.edit_blue);
		RelativeLayout mainView = (RelativeLayout) findViewById(R.id.main_pane);

		int red = Integer.parseInt(String.valueOf(redView.getText()));
		int green = Integer.parseInt(String.valueOf(greenView.getText()));
		int blue = Integer.parseInt(String.valueOf(blueView.getText()));
		
		mainView.setBackgroundColor(Color.rgb(red, green, blue));
	}	
}
