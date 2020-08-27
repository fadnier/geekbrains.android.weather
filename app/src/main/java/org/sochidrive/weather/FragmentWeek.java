package org.sochidrive.weather;

import android.content.Context;
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

import org.sochidrive.weather.model.WeatherFiveDayRequest;

public class FragmentWeek extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerWeekDataAdapter adapter;
    private WeatherFiveDayRequest listData;
    private static final String cityWeekDataKey = "cityDataKey";
    private String city;

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

        SingletonSave.getInstance();
        city = SingletonSave.city;

        new Network(city,this);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        EventBus.getBus().register(this);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putString(cityWeekDataKey, city);
        super.onSaveInstanceState(outState);
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
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL,false);
        adapter = new RecyclerWeekDataAdapter(listData);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            city = savedInstanceState.getString(cityWeekDataKey);
        }
    }

    public void getData(WeatherFiveDayRequest weatherFiveDayRequest) {
        this.listData = weatherFiveDayRequest;
        setupRecyclerView();
    }

    @Subscribe
    @SuppressWarnings("unused")
    public void changeCityEvent(ChangeCityEvent changeCityEvent) {
        city = changeCityEvent.getCity();
        new Network(city,this);
    }
}
