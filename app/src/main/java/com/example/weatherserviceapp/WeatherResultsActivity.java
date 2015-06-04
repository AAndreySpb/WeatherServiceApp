package com.example.weatherserviceapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;


public class WeatherResultsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_resuts);

        String Name = getIntent().getStringExtra("Name");
        String Icon = getIntent().getStringExtra("Icon");
        double Temp = getIntent().getDoubleExtra("Temp", 0.0);
        double Speed = getIntent().getDoubleExtra("Speed", 0.0);
        double Deg = getIntent().getDoubleExtra("Deg", 0.0);
        double Humidity = getIntent().getLongExtra("Humidity", 0);

        TextView nameText = (TextView) findViewById(R.id.nameText);
        nameText.setText(Name);

        TextView tempText = (TextView) findViewById(R.id.tempText);
        tempText.setText(String.format("%.1f", (Temp - 273)) + (char) 0x00B0 + " C");

        ImageView iconImage = (ImageView) findViewById(R.id.imageView);
        switch(Icon)
        {
            case "01d":
                iconImage.setImageResource(R.drawable.sunny);
                break;
            case "02d":
                iconImage.setImageResource(R.drawable.light_cloudy);
                break;
            case "03d":
                iconImage.setImageResource(R.drawable.cloudy);
                break;
            case "04d":
                iconImage.setImageResource(R.drawable.rain);
                break;
            case "05d":
                iconImage.setImageResource(R.drawable.heavy_rain);
                break;
        }

        TextView humText = (TextView) findViewById(R.id.humText);
        humText.setText("Humidity: " + Humidity + "%");

        String windDirection = "";
        if(Deg >= 337 && Deg < 22.5)
            windDirection = "N";
        else if (Deg >= 22.5 && Deg < 67.5)
            windDirection = "NE";
        else if (Deg >= 67.5 && Deg < 112.5)
            windDirection = "E";
        else if (Deg >= 112.5 && Deg < 157.5)
            windDirection = "SE";
        else if (Deg >= 157.5 && Deg < 202.5)
            windDirection = "S";
        else if (Deg >= 202.5 && Deg < 247.5)
            windDirection = "SW";
        else if (Deg >= 247.5 && Deg < 292.5)
            windDirection = "W";
        else if (Deg >= 292.5 && Deg < 337)
            windDirection = "NW";

        TextView windText = (TextView) findViewById(R.id.WindText);
        windText.setText("Wind: " + Speed + "m/s " + windDirection);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_weather_resuts, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
