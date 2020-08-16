package org.sochidrive.weather;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;

public class FragmentCity extends Fragment implements CityBtnOnItemClick {
    private RecyclerView recyclerView;
    private RecyclerDataAdapter adapter;
    //private ArrayList<String> listData = new ArrayList<>(Arrays.asList(getString(R.string.moscow),getString(R.string.saint_petersburg),getString(R.string.yekaterinburg),getString(R.string.novosibirsk),getString(R.string.sochi)));
    private ArrayList<String> listData = new ArrayList<>(Arrays.asList("Moscow","Saint Petersburg","Yekaterinburg","Novosibirsk","Sochi"));

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_city, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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
    public void onItemClicked(String itemText) {
        EventBus.getBus().post(new ChangeCityEvent(itemText));
    }

    private View.OnClickListener onClickListenerChangeCity = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            TextView cityView = (TextView) view;
            String textFromCityView = cityView.getText().toString();
            EventBus.getBus().post(new ChangeCityEvent(textFromCityView));
        }
    };
}
