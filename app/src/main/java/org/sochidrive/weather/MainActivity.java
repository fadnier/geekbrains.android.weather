package org.sochidrive.weather;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    ImageView imageMainWeather;
    TextView textMainDegree;
    TextView textMainCity;
    ImageButton buttonSettings;
    ImageButton buttonChangeCity;
    Button tempButton;
    static final String degreeDataKey = "degreeDataKey";
    static final String cityDataKey = "cityDataKey";
    private final int requestCodeChangeCity = 0016;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        setOnClickButton();
        logAndToast(R.string.onCreate);
    }

    @Override
    protected void onStart() {
        super.onStart();
        logAndToast(R.string.onStart);
    }

    @Override
    protected void onResume() {
        super.onResume();
        logAndToast(R.string.onResume);
    }

    @Override
    protected void onPause() {
        super.onPause();
        logAndToast(R.string.onPause);
    }

    @Override
    protected void onStop() {
        super.onStop();
        logAndToast(R.string.onStop);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        logAndToast(R.string.onDestroy);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle saveInstanceState) {
        logAndToast(R.string.onSaveInstanceState);
        String degree = textMainDegree.getText().toString();
        saveInstanceState.putString(degreeDataKey, degree);
        String city = textMainCity.getText().toString();
        saveInstanceState.putString(cityDataKey, city);

        super.onSaveInstanceState(saveInstanceState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        String degree = savedInstanceState.getString(degreeDataKey);
        textMainDegree.setText(degree);
        String city = savedInstanceState.getString(cityDataKey);
        textMainCity.setText(city);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == this.requestCodeChangeCity && resultCode == RESULT_OK && data != null) {
            String strData = data.getStringExtra(WeatherActivity.cityDataKey);
            textMainCity.setText(strData);
        }
    }

    private void logAndToast(int string) {
        Log.d(getString(R.string.myLogs), getString(string));
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
    }

    private void initView() {
        imageMainWeather = findViewById(R.id.imageMainWeather);
        textMainDegree = findViewById(R.id.textMainDegree);
        textMainCity = findViewById(R.id.textMainCity);
        buttonSettings = findViewById(R.id.buttonSettings);
        buttonChangeCity = findViewById(R.id.buttonChangeCity);
        tempButton = findViewById(R.id.tempButton);
    }

    private void setOnClickButton() {
        buttonSettings.setOnClickListener(onClickListenerSettings);
        buttonChangeCity.setOnClickListener(onClickListenerChangeCity);
        tempButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://yandex.ru/pogoda/");
                Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                startActivity(intent);
            }
        });
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
            intent.putExtra(cityDataKey,textMainCity.getText().toString());
            startActivityForResult(intent,requestCodeChangeCity);
        }
    };
}