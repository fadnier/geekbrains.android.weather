package org.sochidrive.weather.ui.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.squareup.otto.Subscribe;

import org.sochidrive.weather.ChangeCityEvent;
import org.sochidrive.weather.EventBus;
import org.sochidrive.weather.Network;
import org.sochidrive.weather.R;
import org.sochidrive.weather.SingletonSave;
import org.sochidrive.weather.model.WeatherRequest;

import static android.app.Activity.RESULT_OK;

public class HomeFragment extends Fragment {
    private ImageView imageMainWeather;
    private TextView textMainDegree;
    private TextView textMainCity;
    private static final String degreeDataKey = "degreeDataKey";
    private static final String cityDataKey = "cityDataKey";
    private final int requestCodeChangeCity = 1616;
    private final int requestCodeTheme = 7877;
    private String city;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setRetainInstance(true);
        initView(view);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        String degree = textMainDegree.getText().toString();
        outState.putString(degreeDataKey, degree);
        String city = textMainCity.getText().toString();
        outState.putString(cityDataKey, city);

        SingletonSave.city = this.city;
        outState.putSerializable(cityDataKey, SingletonSave.getInstance());

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
    }

    private void initView(@NonNull View view) {
        imageMainWeather = view.findViewById(R.id.imageMainWeather);
        textMainDegree = view.findViewById(R.id.textMainDegree);
        textMainCity = view.findViewById(R.id.textMainCity);

        SingletonSave.getInstance();
        city = SingletonSave.city;
        textMainCity.setText(city);

        new Network(city,this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == this.requestCodeChangeCity && resultCode == RESULT_OK && data != null) {
            String strData = data.getStringExtra(HomeFragment.cityDataKey);
            textMainCity.setText(strData);
        }
        if (requestCode == requestCodeTheme){
            requireActivity().recreate();
        }

    }

    public void getData(WeatherRequest weatherRequest) {
        String degreeText = String.format("%+d", (int)(weatherRequest.getMain().getTemp()-273.15f));
        textMainDegree.setText(degreeText);
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
        SingletonSave.city = changeCityEvent.getCity();

        textMainCity.setText(changeCityEvent.getCity());
        city = changeCityEvent.getCity();
        new Network(changeCityEvent.getCity(),this);
    }
}