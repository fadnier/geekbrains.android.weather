package org.sochidrive.weather.fragment;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.otto.Subscribe;

import org.sochidrive.weather.ChangeCityEvent;
import org.sochidrive.weather.EventBus;
import org.sochidrive.weather.Network;
import org.sochidrive.weather.R;
import org.sochidrive.weather.RecyclerWeekDataAdapter;
import org.sochidrive.weather.SingletonSave;
import org.sochidrive.weather.model.WeatherFiveDayRequest;

public class FragmentWeek extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerWeekDataAdapter adapter;
    private WeatherFiveDayRequest listData;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_week, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setRetainInstance(true);
        initView(view);
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

    private void initView(View view) {
        recyclerView = view.findViewById(R.id.recyclerViewWeek);

    }

    private void setupRecyclerView() {
        LinearLayoutManager layoutManager;
        int orientation = this.getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
            recyclerView.setLayoutManager(layoutManager);
        } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(layoutManager);
        }
        adapter = new RecyclerWeekDataAdapter(listData);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(SingletonSave.getWeatherFiveDayRequestCurrent() != null) {
            listData = SingletonSave.getWeatherFiveDayRequestCurrent();
        }
        setupRecyclerView();
    }

    public void getData(WeatherFiveDayRequest weatherFiveDayRequest) {
        this.listData = weatherFiveDayRequest;
        SingletonSave.setWeatherFiveDayRequestCurrent(weatherFiveDayRequest);
        setupRecyclerView();
    }

    @Subscribe
    @SuppressWarnings("unused")
    public void changeCityEvent(ChangeCityEvent changeCityEvent) {
        new Network(changeCityEvent.getCity(),this);
    }
}
