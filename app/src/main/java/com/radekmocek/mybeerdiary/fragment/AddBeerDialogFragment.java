package com.radekmocek.mybeerdiary.fragment;

import android.os.Bundle;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.radekmocek.mybeerdiary.R;
import com.radekmocek.mybeerdiary.activity.BeersActivity;
import com.radekmocek.mybeerdiary.util.DecimalDigitsInputFilter;

public class AddBeerDialogFragment extends DialogFragment {

    public static final String TAG = "AddBeerDialogFragment";

    public static AddBeerDialogFragment newInstance() {
        return new AddBeerDialogFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.FullscreenDialogStyle);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_new_beer, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        EditText editTextBreweryName = view.findViewById(R.id.newBeer_ediTextBreweryName);
        EditText editTextDescription = view.findViewById(R.id.newBeer_editTextDescription);
        EditText editTextEPM = view.findViewById(R.id.newBeer_ediTextEPM);
        EditText editTextABV = view.findViewById(R.id.newBeer_ediTextABV);
        EditText editTextPrice = view.findViewById(R.id.newBeer_ediTextPrice);
        ExtendedFloatingActionButton eFabOK = view.findViewById(R.id.newBeer_eFabAdd);

        editTextEPM.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(2, 2)});
        editTextABV.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(2, 2)});

        eFabOK.setOnClickListener(v -> {
            ((BeersActivity) requireActivity()).addBeer(String.valueOf(editTextBreweryName.getText()));
            dismiss();
        });
    }
}
