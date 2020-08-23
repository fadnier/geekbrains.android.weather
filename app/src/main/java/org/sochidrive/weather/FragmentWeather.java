package org.sochidrive.weather;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
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

import org.sochidrive.weather.model.WeatherRequest;

import java.util.Objects;

import static android.app.Activity.RESULT_OK;

public class FragmentWeather extends Fragment {
    private ImageView imageMainWeather;
    private TextView textMainDegree;
    private TextView textMainCity;
    private ImageButton buttonSettings;
    private ImageButton buttonChangeCity;
    private static final String degreeDataKey = "degreeDataKey";
    private static final String cityDataKey = "cityDataKey";
    private final int requestCodeChangeCity = 1616;
    private final int requestCodeTheme = 7877;
    private boolean isHorizontal;
    private String city = "Moscow";

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
        new Network(city,this);
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
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        EventBus.getBus().register(this);
    }

    @Override
    public void onDetach() {
        EventBus.getBus().unregister(this);
        super.onDetach();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            String degree = savedInstanceState.getString(degreeDataKey);
            textMainDegree.setText(degree);
            String city = savedInstanceState.getString(cityDataKey);
            textMainCity.setText(city);
            this.city = city;
        }

        isHorizontal = getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE;

        if (isHorizontal) {
            buttonChangeCity.setVisibility(View.INVISIBLE);
        } else {
            buttonChangeCity.setVisibility(View.VISIBLE);
        }
    }

    private void initView(@NonNull View view) {
        imageMainWeather = view.findViewById(R.id.imageMainWeather);
        textMainDegree = view.findViewById(R.id.textMainDegree);
        textMainCity = view.findViewById(R.id.textMainCity);
        buttonSettings = view.findViewById(R.id.buttonSettings);
        buttonChangeCity = view.findViewById(R.id.buttonChangeCity);
        textMainCity.setText("Москва");
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
        if (requestCode == requestCodeTheme){
            Objects.requireNonNull(getActivity()).recreate();
        }

    }

    private View.OnClickListener onClickListenerSettings = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(Objects.requireNonNull(getActivity()).getApplication(),SettingsActivity.class);
            startActivityForResult(intent,requestCodeTheme);
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

    public void getData(WeatherRequest weatherRequest) {
        textMainDegree.setText(String.format("%+d", (int)(weatherRequest.getMain().getTemp()-273.15f)));
        if(weatherRequest.getWeather()[0].getDescription().equals("clear sky")) {
            imageMainWeather.setImageResource(R.drawable.weather1);
        } else if(weatherRequest.getWeather()[0].getDescription().equals("few clouds")) {
            imageMainWeather.setImageResource(R.drawable.weather2);
        } else if(weatherRequest.getWeather()[0].getDescription().equals("scattered clouds")) {
            imageMainWeather.setImageResource(R.drawable.weather2);
        } else if(weatherRequest.getWeather()[0].getDescription().equals("broken clouds")) {
            imageMainWeather.setImageResource(R.drawable.weather2);
        } else if(weatherRequest.getWeather()[0].getDescription().equals("shower rain")) {
            imageMainWeather.setImageResource(R.drawable.weather3);
        } else if(weatherRequest.getWeather()[0].getDescription().equals("rain")) {
            imageMainWeather.setImageResource(R.drawable.weather3);
        } else if(weatherRequest.getWeather()[0].getDescription().equals("thunderstorm")) {
            imageMainWeather.setImageResource(R.drawable.weather4);
        } else if(weatherRequest.getWeather()[0].getDescription().equals("snow")) {
            imageMainWeather.setImageResource(R.drawable.weather5);
        } else if(weatherRequest.getWeather()[0].getDescription().equals("mist")) {
            imageMainWeather.setImageResource(R.drawable.weather2);
        } else {
            imageMainWeather.setImageResource(R.drawable.weather1);
        }
    }

    @Subscribe
    @SuppressWarnings("unused")
    public void changeCityEvent(ChangeCityEvent changeCityEvent) {
        textMainCity.setText(changeCityEvent.getCity());
        city = changeCityEvent.getCity();
        new Network(changeCityEvent.getCity(),this);
    }
}
