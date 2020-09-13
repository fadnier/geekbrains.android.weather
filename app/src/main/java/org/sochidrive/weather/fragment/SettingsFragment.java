package org.sochidrive.weather.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.switchmaterial.SwitchMaterial;

import org.sochidrive.weather.BaseActivity;
import org.sochidrive.weather.R;
import org.sochidrive.weather.SingletonSave;

import static android.content.Context.MODE_PRIVATE;

public class SettingsFragment extends Fragment {
    private SwitchMaterial switchTheme;
    private CheckBox checkBoxWindSpeed;
    private CheckBox checkBoxPressure;

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

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        checkBoxPressure.setChecked(SingletonSave.getCheckBoxPressure());
        checkBoxWindSpeed.setChecked(SingletonSave.getCheckBoxWindSpeed());

    }

    private void initView(@NonNull View view) {
        switchTheme = view.findViewById(R.id.switchTheme);
        switchTheme.setChecked(isDarkTheme());
        checkBoxPressure = view.findViewById(R.id.checkBoxPressure);
        checkBoxWindSpeed = view.findViewById(R.id.checkBoxWindSpeed);
    }

    private void setOnClickButton() {
        switchTheme.setOnCheckedChangeListener(onCheckedChangeListener);

        checkBoxPressure.setOnCheckedChangeListener((compoundButton, b) -> SingletonSave.setCheckBoxPressure(b));
        checkBoxWindSpeed.setOnCheckedChangeListener((compoundButton, b) -> SingletonSave.setCheckBoxWindSpeed(b));
    }

    protected boolean isDarkTheme() {
        if(getActivity() != null) {
            SharedPreferences sharedPref = getActivity().getSharedPreferences(BaseActivity.isDarkTheme, MODE_PRIVATE);
            return sharedPref.getBoolean(BaseActivity.isDarkTheme, true);
        }
        return false;
    }

    protected void setDarkTheme(boolean isDarkTheme) {
        if(getActivity() != null) {
            SharedPreferences sharedPref = getActivity().getSharedPreferences(BaseActivity.isDarkTheme, MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putBoolean(BaseActivity.isDarkTheme, isDarkTheme);
            editor.apply();
        }
    }

    private CompoundButton.OnCheckedChangeListener onCheckedChangeListener = (compoundButton, isChecked) -> {
        setDarkTheme(isChecked);
        if(getActivity() != null){
            getActivity().recreate();
            ((NavigationView) getActivity().findViewById(R.id.nav_view)).setCheckedItem(R.id.nav_home);
        }
    };
}