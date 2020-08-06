package org.sochidrive.weather;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class WeatherActivity extends AppCompatActivity {
    Button buttonSuccess;
    TextView textListPos1;
    TextView textListPos2;
    TextView textListPos3;
    TextView textListPos4;
    TextView textListPos5;
    TextView editTextSelectCity;
    CheckBox checkBoxWindSpeed;
    CheckBox checkBoxPressure;
    private final String windDataKey = "windDataKey";
    final static String cityDataKey = "cityDataKey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        initView();
        setOnClickButton();
        showDataFromFirstActivity();
    }

    private void showDataFromFirstActivity() {
        String data = getIntent().getStringExtra(MainActivity.cityDataKey);
        editTextSelectCity.setText(data);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle saveInstanceState) {
        SingletonSave.checkBoxPressure = checkBoxPressure.isChecked();
        SingletonSave.checkBoxWindSpeed = checkBoxWindSpeed.isChecked();
        saveInstanceState.putSerializable(windDataKey, SingletonSave.getInstance());

        super.onSaveInstanceState(saveInstanceState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        SingletonSave container = (SingletonSave)savedInstanceState.getSerializable(windDataKey);
        checkBoxPressure.setChecked(SingletonSave.checkBoxPressure);
        checkBoxWindSpeed.setChecked(SingletonSave.checkBoxWindSpeed);
    }

    private void initView() {
        buttonSuccess = findViewById(R.id.buttonSuccess);
        textListPos1 = findViewById(R.id.textListPos1);
        textListPos2 = findViewById(R.id.textListPos2);
        textListPos3 = findViewById(R.id.textListPos3);
        textListPos4 = findViewById(R.id.textListPos4);
        textListPos5 = findViewById(R.id.textListPos5);
        editTextSelectCity = findViewById(R.id.editTextSelectCity);
        checkBoxPressure = findViewById(R.id.checkBoxPressure);
        checkBoxWindSpeed = findViewById(R.id.checkBoxWindSpeed);
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
            String text = editTextSelectCity.getText().toString();
            Intent dataIntent = new Intent();
            dataIntent.putExtra(cityDataKey, text);
            setResult(RESULT_OK, dataIntent);
            finish();
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
