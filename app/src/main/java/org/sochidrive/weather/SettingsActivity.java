package org.sochidrive.weather;

import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.switchmaterial.SwitchMaterial;

public class SettingsActivity extends BaseActivity {
    private MaterialButton buttonBack;
    private SwitchMaterial switchTheme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        initView();
        setOnClickButton();
    }

    private void initView() {
        buttonBack = findViewById(R.id.buttonBack);
        switchTheme = findViewById(R.id.switchTheme);
        switchTheme.setChecked(isDarkTheme());
    }

    private void setOnClickButton() {
        buttonBack.setOnClickListener(onClickListenerMain);
        switchTheme.setOnCheckedChangeListener(onCheckedChangeListener);
    }

    private View.OnClickListener onClickListenerMain = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            finish();
        }
    };

    private CompoundButton.OnCheckedChangeListener onCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
            setDarkTheme(isChecked);
            recreate();
        }
    };
}
