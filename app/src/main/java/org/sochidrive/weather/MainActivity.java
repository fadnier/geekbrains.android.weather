package org.sochidrive.weather;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    ImageView imageMainWeather;
    TextView textMainDegree;
    TextView textMainCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        imageMainWeather = findViewById(R.id.imageMainWeather);
        textMainDegree = findViewById(R.id.textMainDegree);
        textMainCity = findViewById(R.id.textMainCity);
    }
}