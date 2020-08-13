package org.sochidrive.weather;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.squareup.otto.Subscribe;

public class WeatherActivity extends AppCompatActivity {
    Button buttonSuccess;
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
        String data = getIntent().getStringExtra(cityDataKey);
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
    public void onStart() {
        super.onStart();
        EventBus.getBus().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getBus().unregister(this);
        super.onStop();
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        savedInstanceState.getSerializable(windDataKey);
        checkBoxPressure.setChecked(SingletonSave.checkBoxPressure);
        checkBoxWindSpeed.setChecked(SingletonSave.checkBoxWindSpeed);
    }

    private void initView() {
        buttonSuccess = findViewById(R.id.buttonSuccess);
        editTextSelectCity = findViewById(R.id.editTextSelectCity);
        checkBoxPressure = findViewById(R.id.checkBoxPressure);
        checkBoxWindSpeed = findViewById(R.id.checkBoxWindSpeed);
    }

    private void setOnClickButton() {
        buttonSuccess.setOnClickListener(onClickListenerMain);
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

    @Subscribe
    @SuppressWarnings("unused")
    public void changeCityEvent(ChangeCityEvent changeCityEvent) {
        editTextSelectCity.setText(changeCityEvent.getCity());
    }
}
