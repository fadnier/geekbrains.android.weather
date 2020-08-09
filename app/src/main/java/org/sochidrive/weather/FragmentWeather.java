package org.sochidrive.weather;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.squareup.otto.Subscribe;

import java.util.Objects;

import static android.app.Activity.RESULT_OK;

public class FragmentWeather extends Fragment {
    ImageView imageMainWeather;
    TextView textMainDegree;
    TextView textMainCity;
    ImageButton buttonSettings;
    ImageButton buttonChangeCity;
    static final String degreeDataKey = "degreeDataKey";
    static final String cityDataKey = "cityDataKey";
    private final int requestCodeChangeCity = 1616;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_weather, container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setRetainInstance(true);
        initView(view);
        setOnClickButton();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        String degree = textMainDegree.getText().toString();
        outState.putString(degreeDataKey, degree);
        String city = textMainCity.getText().toString();
        outState.putString(cityDataKey, city);

        super.onSaveInstanceState(outState);
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
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            String degree = savedInstanceState.getString(degreeDataKey);
            textMainDegree.setText(degree);
            String city = savedInstanceState.getString(cityDataKey);
            textMainCity.setText(city);
        }

    }

    private void initView(@NonNull View view) {
        imageMainWeather = view.findViewById(R.id.imageMainWeather);
        textMainDegree = view.findViewById(R.id.textMainDegree);
        textMainCity = view.findViewById(R.id.textMainCity);
        buttonSettings = view.findViewById(R.id.buttonSettings);
        buttonChangeCity = view.findViewById(R.id.buttonChangeCity);
    }

    private void setOnClickButton() {
        buttonSettings.setOnClickListener(onClickListenerSettings);
        buttonChangeCity.setOnClickListener(onClickListenerChangeCity);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == this.requestCodeChangeCity && resultCode == RESULT_OK && data != null) {
            String strData = data.getStringExtra(WeatherActivity.cityDataKey);
            textMainCity.setText(strData);
        }
    }

    private View.OnClickListener onClickListenerSettings = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(Objects.requireNonNull(getActivity()).getApplication(),SettingsActivity.class);
            startActivity(intent);
        }
    };

    private View.OnClickListener onClickListenerChangeCity = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(Objects.requireNonNull(getActivity()).getApplication(),WeatherActivity.class);
            intent.putExtra(cityDataKey,textMainCity.getText().toString());
            startActivityForResult(intent,requestCodeChangeCity);
        }
    };

    @Subscribe
    @SuppressWarnings("unused")
    public void changeCityEvent(ChangeCityEvent changeCityEvent) {
        textMainCity.setText(changeCityEvent.city);
    }
}
