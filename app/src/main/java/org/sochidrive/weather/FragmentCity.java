package org.sochidrive.weather;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FragmentCity extends Fragment {
    TextView textListPos1;
    TextView textListPos2;
    TextView textListPos3;
    TextView textListPos4;
    TextView textListPos5;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_city, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        setOnClickButton();
    }

    private void initView(View view) {
        textListPos1 = view.findViewById(R.id.textListPos1);
        textListPos2 = view.findViewById(R.id.textListPos2);
        textListPos3 = view.findViewById(R.id.textListPos3);
        textListPos4 = view.findViewById(R.id.textListPos4);
        textListPos5 = view.findViewById(R.id.textListPos5);
    }

    private void setOnClickButton() {
        textListPos1.setOnClickListener(onClickListenerChangeCity);
        textListPos2.setOnClickListener(onClickListenerChangeCity);
        textListPos3.setOnClickListener(onClickListenerChangeCity);
        textListPos4.setOnClickListener(onClickListenerChangeCity);
        textListPos5.setOnClickListener(onClickListenerChangeCity);
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
