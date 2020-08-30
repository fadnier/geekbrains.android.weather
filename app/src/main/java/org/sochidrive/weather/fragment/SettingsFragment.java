package org.sochidrive.weather.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.switchmaterial.SwitchMaterial;

import org.sochidrive.weather.BaseActivity;
import org.sochidrive.weather.R;

import static android.content.Context.MODE_PRIVATE;

public class SettingsFragment extends Fragment {
    private SwitchMaterial switchTheme;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        setOnClickButton();
    }

    private void initView(@NonNull View view) {
        switchTheme = view.findViewById(R.id.switchTheme);
        switchTheme.setChecked(isDarkTheme());
    }

    private void setOnClickButton() {
        switchTheme.setOnCheckedChangeListener(onCheckedChangeListener);
    }

    protected boolean isDarkTheme() {
        SharedPreferences sharedPref = getActivity().getSharedPreferences(BaseActivity.nameSharedPreference, MODE_PRIVATE);
        return sharedPref.getBoolean(BaseActivity.isDarkTheme, true);
    }

    protected void setDarkTheme(boolean isDarkTheme) {
        SharedPreferences sharedPref = getActivity().getSharedPreferences(BaseActivity.nameSharedPreference, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(BaseActivity.isDarkTheme, isDarkTheme);
        editor.apply();
    }

    private CompoundButton.OnCheckedChangeListener onCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
            setDarkTheme(isChecked);
            getActivity().recreate();
        }
    };
}