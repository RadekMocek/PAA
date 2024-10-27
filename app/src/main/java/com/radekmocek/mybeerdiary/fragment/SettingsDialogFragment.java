package com.radekmocek.mybeerdiary.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.button.MaterialButtonToggleGroup;
import com.radekmocek.mybeerdiary.R;
import com.radekmocek.mybeerdiary.util.Const;
import com.radekmocek.mybeerdiary.util.WeightInputFilter;

public class SettingsDialogFragment extends DialogFragment {

    public static final String TAG = "SettingsDialogFragment";

    public static SettingsDialogFragment newInstance() {
        return new SettingsDialogFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogStyle);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MaterialButtonToggleGroup tg = view.findViewById(R.id.settings_toggleGroup);
        Button tgButtonMale = view.findViewById(R.id.settings_toggleGroup_0);
        Button tgButtonFemale = view.findViewById(R.id.settings_toggleGroup_1);
        EditText editTextWeight = view.findViewById(R.id.settings_editTextWeight);
        Button buttonSave = view.findViewById(R.id.settings_buttonSave);
        Button buttonCancel = view.findViewById(R.id.settings_buttonCancel);

        SharedPreferences sharedPreferences = getContext().getSharedPreferences(Const.PREFS_NAME, Context.MODE_PRIVATE);
        int weight = sharedPreferences.getInt(Const.PREFS_KEY_WEIGHT, Const.PREFS_DEFAULT_WEIGHT);
        boolean isMale = sharedPreferences.getBoolean(Const.PREFS_KEY_IS_MALE, Const.PREFS_DEFAULT_IS_MALE);

        editTextWeight.setFilters(new InputFilter[]{new WeightInputFilter()});
        editTextWeight.setText(String.valueOf(weight));

        tg.check((isMale) ? tgButtonMale.getId() : tgButtonFemale.getId());

        buttonSave.setOnClickListener(v -> {
            int newWeight;
            try {
                newWeight = Integer.parseInt(editTextWeight.getText().toString());
            } catch (Exception e) {
                newWeight = weight;
            }
            boolean newIsMale = (tg.getCheckedButtonId() == tgButtonMale.getId());

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt(Const.PREFS_KEY_WEIGHT, newWeight);
            editor.putBoolean(Const.PREFS_KEY_IS_MALE, newIsMale);
            editor.apply();

            dismiss();
        });

        buttonCancel.setOnClickListener(v -> dismiss());
    }
}
