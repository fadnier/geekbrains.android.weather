package org.sochidrive.weather;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class WeatherActivity extends AppCompatActivity {
    Button buttonSuccess;
    TextView textListPos1;
    TextView textListPos2;
    TextView textListPos3;
    TextView textListPos4;
    TextView textListPos5;
    TextView editTextSelectCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        initView();
        setOnClickButton();
    }

    private void initView() {
        buttonSuccess = findViewById(R.id.buttonSuccess);
        textListPos1 = findViewById(R.id.textListPos1);
        textListPos2 = findViewById(R.id.textListPos2);
        textListPos3 = findViewById(R.id.textListPos3);
        textListPos4 = findViewById(R.id.textListPos4);
        textListPos5 = findViewById(R.id.textListPos5);
        editTextSelectCity = findViewById(R.id.editTextSelectCity);
    }

    private void setOnClickButton() {
        buttonSuccess.setOnClickListener(onClickListenerMain);
        textListPos1.setOnClickListener(onClickListenerChangeCity);
        textListPos2.setOnClickListener(onClickListenerChangeCity);
        textListPos3.setOnClickListener(onClickListenerChangeCity);
        textListPos4.setOnClickListener(onClickListenerChangeCity);
        textListPos5.setOnClickListener(onClickListenerChangeCity);
    }

    private View.OnClickListener onClickListenerMain = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(WeatherActivity.this,MainActivity.class);
            startActivity(intent);
        }
    };

    private View.OnClickListener onClickListenerChangeCity = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            TextView cityView = (TextView) view;
            String textFromCityView = cityView.getText().toString();
            editTextSelectCity.setText(textFromCityView);
        }
    };
}
