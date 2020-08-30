package org.sochidrive.weather.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;
import com.squareup.otto.Subscribe;

import org.sochidrive.weather.ChangeCityEvent;
import org.sochidrive.weather.EventBus;
import org.sochidrive.weather.R;
import org.sochidrive.weather.SingletonSave;


public class ChangeCityFragment extends Fragment {

    private TextInputEditText editTextSelectCity;
    private CheckBox checkBoxWindSpeed;
    private CheckBox checkBoxPressure;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_changecity, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
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
        checkBoxPressure.setChecked(SingletonSave.getCheckBoxPressure());
        checkBoxWindSpeed.setChecked(SingletonSave.getCheckBoxWindSpeed());
    }

    private void initView(@NonNull View view) {
        editTextSelectCity = view.findViewById(R.id.editTextSelectCity);
        checkBoxPressure = view.findViewById(R.id.checkBoxPressure);
        checkBoxWindSpeed = view.findViewById(R.id.checkBoxWindSpeed);
    }

    @Subscribe
    @SuppressWarnings("unused")
    public void changeCityEvent(ChangeCityEvent changeCityEvent) {
        editTextSelectCity.setText(changeCityEvent.getCity());
    }

}