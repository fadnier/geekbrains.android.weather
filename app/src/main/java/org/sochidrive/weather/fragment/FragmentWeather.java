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
import org.sochidrive.weather.model.WeatherData;

import java.util.Locale;

public class FragmentWeather extends Fragment {

    private ImageView imageMainWeather;
    private TextView textMainDegree;
    private TextView textMainCity;
    private TextView textPressure;
    private TextView textWindSpeed;

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
        textWindSpeed.setVisibility(SingletonSave.getCheckBoxWindSpeed() ? View.VISIBLE : View.INVISIBLE);
        textPressure.setVisibility(SingletonSave.getCheckBoxPressure() ? View.VISIBLE : View.INVISIBLE);

        if(SingletonSave.getWeatherData() != null) {
            textMainDegree.setText(SingletonSave.getWeatherData().getDegree());
            textMainCity.setText(SingletonSave.getCity());
            textPressure.setText(String.format(Locale.getDefault(),"%s %s", getString(R.string.pressure), SingletonSave.getWeatherData().getPressure()));
            textWindSpeed.setText(String.format(Locale.getDefault(),"%s %s", getString(R.string.wind_speed), SingletonSave.getWeatherData().getWindSpeed()));
            setImageWeather(SingletonSave.getWeatherData().getIcon());
        } else {
            textMainDegree.setText(SingletonSave.getDegree());
            textMainCity.setText(SingletonSave.getCity());
            setImageWeather(SingletonSave.getIcon());
            new Network(SingletonSave.getCity(),this);
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
        textPressure = view.findViewById(R.id.textPressure);
        textWindSpeed = view.findViewById(R.id.textWindSpeed);

    }

    public void getData(WeatherData weatherData) {
        SingletonSave.setWeatherData(weatherData);
        SingletonSave.setIcon(weatherData.getIcon());
        SingletonSave.setDegree(weatherData.getDegree());
        textMainDegree.setText(weatherData.getDegree());
        textMainCity.setText(SingletonSave.getCity());
        textPressure.setText(String.format(Locale.getDefault(),"%s %s", getString(R.string.pressure), weatherData.getPressure()));
        textWindSpeed.setText(String.format(Locale.getDefault(),"%s %s", getString(R.string.wind_speed), weatherData.getWindSpeed()));
        setImageWeather(weatherData.getIcon());

    }

    private void setImageWeather(String nameWeather) {
        switch (nameWeather) {
            case "02n":
            case "03n":
            case "04n":
            case "50n":
                imageMainWeather.setImageResource(R.drawable.weather2);
                break;
            case "09n":
            case "10n":
                imageMainWeather.setImageResource(R.drawable.weather3);
                break;
            case "11n":
                imageMainWeather.setImageResource(R.drawable.weather4);
                break;
            case "13n":
                imageMainWeather.setImageResource(R.drawable.weather5);
                break;
            case "01n":
            default:
                imageMainWeather.setImageResource(R.drawable.weather1);
                break;
        }
    }

    @Subscribe
    @SuppressWarnings("unused")
    public void changeCityEvent(ChangeCityEvent changeCityEvent) {
        new Network(changeCityEvent.getCity(),this);
    }
}
