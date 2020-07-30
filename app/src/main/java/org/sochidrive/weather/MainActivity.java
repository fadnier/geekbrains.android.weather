package org.sochidrive.weather;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    ImageView imageMainWeather;
    TextView textMainDegree;
    TextView textMainCity;
    ImageButton buttonSettings;
    ImageButton buttonChangeCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        setOnClickButton();
    }

    private void initView() {
        imageMainWeather = findViewById(R.id.imageMainWeather);
        textMainDegree = findViewById(R.id.textMainDegree);
        textMainCity = findViewById(R.id.textMainCity);
        buttonSettings = findViewById(R.id.buttonSettings);
        buttonChangeCity = findViewById(R.id.buttonChangeCity);
    }

    private void setOnClickButton() {
        buttonSettings.setOnClickListener(onClickListenerSettings);
        buttonChangeCity.setOnClickListener(onClickListenerChangeCity);
    }

    private View.OnClickListener onClickListenerSettings = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(MainActivity.this,SettingsActivity.class);
            startActivity(intent);
        }
    };

    private View.OnClickListener onClickListenerChangeCity = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(MainActivity.this,WeatherActivity.class);
            startActivity(intent);
        }
    };
}