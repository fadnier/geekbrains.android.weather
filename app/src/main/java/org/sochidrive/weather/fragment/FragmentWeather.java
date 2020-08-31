package org.sochidrive.weather.fragment;

import android.content.Context;
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

import java.util.Locale;

public class FragmentWeather extends Fragment {

    private ImageView imageMainWeather;
    private TextView textMainDegree;
    private TextView textMainCity;

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
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        SingletonSave.getInstance();
        if(SingletonSave.getWeatherRequestCurrent() != null) {
            String degreeText = String.format(Locale.getDefault(),"%+d", (int)(SingletonSave.getWeatherRequestCurrent().getMain().getTemp()-273.15f));
            textMainDegree.setText(degreeText);
            textMainCity.setText(SingletonSave.getCity());
            setImageWeather(SingletonSave.getWeatherRequestCurrent().getWeather()[0].getDescription());
        }
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

    private void initView(@NonNull View view) {
        imageMainWeather = view.findViewById(R.id.imageMainWeather);
        textMainDegree = view.findViewById(R.id.textMainDegree);
        textMainCity = view.findViewById(R.id.textMainCity);
    }

    public void getData(WeatherRequest weatherRequest) {
        SingletonSave.setWeatherRequestCurrent(weatherRequest);
        String degreeText = String.format(Locale.getDefault(),"%+d", (int)(weatherRequest.getMain().getTemp()-273.15f));
        textMainDegree.setText(degreeText);
        textMainCity.setText(SingletonSave.getCity());
        setImageWeather(weatherRequest.getWeather()[0].getDescription());
    }

    private void setImageWeather(String nameWeather) {
        switch (nameWeather) {
            case "few clouds":
            case "scattered clouds":
            case "broken clouds":
            case "mist":
                imageMainWeather.setImageResource(R.drawable.weather2);
                break;
            case "shower rain":
            case "rain":
                imageMainWeather.setImageResource(R.drawable.weather3);
                break;
            case "thunderstorm":
                imageMainWeather.setImageResource(R.drawable.weather4);
                break;
            case "snow":
                imageMainWeather.setImageResource(R.drawable.weather5);
                break;
            case "clear sky":
            default:
                imageMainWeather.setImageResource(R.drawable.weather1);
                break;
        }
    }

    @Subscribe
    @SuppressWarnings("unused")
    public void changeCityEvent(ChangeCityEvent changeCityEvent) {
        textMainCity.setText(changeCityEvent.getCity());
        new Network(changeCityEvent.getCity(),this);
    }
}
