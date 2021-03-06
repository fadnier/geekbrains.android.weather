package org.sochidrive.weather.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.sochidrive.weather.ChangeCityEvent;
import org.sochidrive.weather.CityBtnOnItemClick;
import org.sochidrive.weather.EventBus;
import org.sochidrive.weather.R;
import org.sochidrive.weather.RecyclerDataAdapter;
import org.sochidrive.weather.SingletonSave;

import java.util.ArrayList;
import java.util.Arrays;

import okhttp3.internal.Version;

public class FragmentCity extends Fragment implements CityBtnOnItemClick {
    private RecyclerView recyclerView;
    private RecyclerDataAdapter adapter;
    private ArrayList<String> listData;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_city, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listData = new ArrayList<>(Arrays.asList(getString(R.string.сurrent_city)+SingletonSave.getGeoCity(), getString(R.string.moscow),getString(R.string.saint_petersburg),getString(R.string.yekaterinburg),getString(R.string.novosibirsk),getString(R.string.sochi),getString(R.string.kazan)));
        initView(view);
        setupRecyclerView();
    }

    private void initView(View view) {
        recyclerView = view.findViewById(R.id.recyclerViewCity);
    }

    private void setupRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false);
        adapter = new RecyclerDataAdapter(listData,this);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onItemClicked(final String itemText) {
        Snackbar.make(requireView(), R.string.change_city, Snackbar.LENGTH_LONG).setAction(R.string.change, v -> EventBus.getBus().post(new ChangeCityEvent(filterName(itemText)))).show();
    }

    private String filterName(String city) {
        return city.replaceAll(getString(R.string.сurrent_city),"");
    }
}
