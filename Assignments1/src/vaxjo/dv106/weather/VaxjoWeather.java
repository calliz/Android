/**
 * VaxjoWeather.java
 * Created: May 9, 2010
 * Jonas Lundberg, LnU
 */

package vaxjo.dv106.weather;

import java.io.IOException;
import java.net.URL;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.example.assignment1.R;

/**
 * This is a first prototype for a weather app. It is currently only downloading
 * weather data for Vдxjц.
 * 
 * This activity downloads weather data and constructs a WeatherReport, a data
 * structure containing weather data for a number of periods ahead.
 * 
 * The WeatherHandler is a SAX parser for the weather reports (forecast.xml)
 * produced by www.yr.no. The handler constructs a WeatherReport containing meta
 * data for a given location (e.g. city, country, last updated, next update) and
 * a sequence of WeatherForecasts. Each WeatherForecast represents a forecast
 * (weather, rain, wind, etc) for a given time period.
 * 
 * The next task is to construct a list based GUI where each row displays the
 * weather data for a single period.
 * 
 * 
 * @author jlnmsi
 * 
 */

public class VaxjoWeather extends Activity {
	private Weather[] weather_data;
	private WeatherAdapter weatherAdapter;
	private ListView listView;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.weather_list_layout);

		try {
			URL url = new URL(
					"http://www.yr.no/sted/Sverige/Sk%C3%A5ne/Malm%C3%B6/forecast.xml");
			WeatherReport report = WeatherHandler.getWeatherReport(url);

			/* Print location meta data */
			System.out.println(report);

			int i = 0;
			for (Object obj : report)
				i++;

			weather_data = new Weather[i];

			/* Print forecasts */
			int count = 0;
			for (WeatherForecast forecast : report) {
				StringBuilder sb = new StringBuilder();

				String date = forecast.getStartYYMMDD();
				String start = forecast.getStartHHMM();
				String stop = forecast.getEndHHMM();
				String temp = forecast.getTemp() + "\u00B0C";
				String weatherName = forecast.getWeatherName() + ".";
				String wind = forecast.getWindSpeed() + "m/s, "
						+ forecast.getWindSpeedName() + " fra "
						+ forecast.getWindDirectionName() + ".";
				String rain = forecast.getRain() + "mm.";

				sb.append(date + "\n");
				sb.append(start + "-" + stop + "\n");
				sb.append(temp + ", " + weatherName + "\n");
				sb.append(wind + "\n");
				sb.append(rain + "\n");

				weather_data[count] = new Weather(getIcon(
						forecast.getPeriodCode(), forecast.getWeatherCode()),
						sb.toString());
				count++;
			}

			weatherAdapter = new WeatherAdapter(this,
					R.layout.weather_item_row, weather_data);
			listView = (ListView) findViewById(R.id.listViewWeather);
			View header = (View) getLayoutInflater().inflate(
					R.layout.weather_header_row, null);
			listView.addHeaderView(header);
			listView.setAdapter(weatherAdapter);

		} catch (IOException ioe) {
			ioe.printStackTrace();
		}

	}

	private int getIcon(int periodCode, int weatherCode) {
		switch (weatherCode) {
		case 1:
			return periodCode <= 2 ? R.drawable.pic_01d : R.drawable.pic_01n;
		case 2:
			return periodCode <= 2 ? R.drawable.pic_02d : R.drawable.pic_02n;
		case 3:
			return periodCode <= 2 ? R.drawable.pic_03d : R.drawable.pic_03n;
		case 4:
			return R.drawable.pic_04;
		case 5:
			return periodCode <= 2 ? R.drawable.pic_05d : R.drawable.pic_05n;
		case 6:
			return periodCode <= 2 ? R.drawable.pic_06d : R.drawable.pic_06n;
		case 7:
			return periodCode <= 2 ? R.drawable.pic_07d : R.drawable.pic_07n;
		case 8:
			return periodCode <= 2 ? R.drawable.pic_08d : R.drawable.pic_08n;
		case 9:
			return R.drawable.pic_09;
		case 10:
			return R.drawable.pic_10;
		case 11:
			return R.drawable.pic_11;
		case 12:
			return R.drawable.pic_12;
		case 13:
			return R.drawable.pic_13;
		case 14:
			return R.drawable.pic_14;
		case 15:
			return R.drawable.pic_15;
		case 16:
			return R.drawable.pic_20d;
		case 17:
			return R.drawable.pic_21d;
		case 18:
			return R.drawable.pic_22;
		case 19:
			return R.drawable.pic_23;
		default:
			return R.drawable.pic_15;
		}
	}

}