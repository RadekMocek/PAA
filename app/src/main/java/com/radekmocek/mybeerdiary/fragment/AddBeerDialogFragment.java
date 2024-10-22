package com.radekmocek.mybeerdiary.fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.slider.Slider;
import com.google.android.material.textfield.TextInputLayout;
import com.radekmocek.mybeerdiary.R;
import com.radekmocek.mybeerdiary.activity.BeersActivity;
import com.radekmocek.mybeerdiary.model.Beer;
import com.radekmocek.mybeerdiary.model.PubVisit;
import com.radekmocek.mybeerdiary.util.Conv;
import com.radekmocek.mybeerdiary.util.DecimalDigitsInputFilter;

import java.util.Calendar;

public class AddBeerDialogFragment extends DialogFragment {

    public static final String TAG = "AddBeerDialogFragment";

    private static final String pubVisitIDBundleKey = "pubVisitID";

    public static AddBeerDialogFragment newInstance(PubVisit p) {
        AddBeerDialogFragment f = new AddBeerDialogFragment();

        Bundle args = new Bundle();
        args.putInt(pubVisitIDBundleKey, p.getId());
        f.setArguments(args);

        return f;
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

        Bundle args = getArguments();
        int pubVisitID;
        if (args != null) {
            pubVisitID = args.getInt(pubVisitIDBundleKey);
        } else {
            pubVisitID = -1;
        }

        EditText editTextBreweryName = view.findViewById(R.id.newBeer_ediTextBreweryName);
        EditText editTextDescription = view.findViewById(R.id.newBeer_editTextDescription);
        EditText editTextEPM = view.findViewById(R.id.newBeer_ediTextEPM);
        EditText editTextABV = view.findViewById(R.id.newBeer_ediTextABV);
        EditText editTextPrice = view.findViewById(R.id.newBeer_ediTextPrice);
        TextView textViewArrow = view.findViewById(R.id.newBeer_textViewArrow);

        TextInputLayout textFieldABV = view.findViewById(R.id.newBeer_textFieldABV);

        MaterialButtonToggleGroup tg = view.findViewById(R.id.newBeer_toggleGroup);
        Button tgButton0 = view.findViewById(R.id.newBeer_toggleGroup_0);
        Button tgButton1 = view.findViewById(R.id.newBeer_toggleGroup_1);
        Button tgButton2 = view.findViewById(R.id.newBeer_toggleGroup_2);

        Slider slider = view.findViewById(R.id.newBeer_slider);

        CheckBox checkBox = view.findViewById(R.id.newBeer_checkbox);

        ExtendedFloatingActionButton eFabOK = view.findViewById(R.id.newBeer_eFabAdd);

        editTextEPM.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(2, 2)});
        editTextEPM.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (checkBox.isChecked()) {
                    editTextABV.setText(Conv.EPMEditText2EBMEditText(editTextEPM.getText().toString()));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        editTextABV.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(2, 2)});
        textViewArrow.setEnabled(false);

        tg.check(tgButton1.getId());

        slider.setValue(0.4f);
        slider.addOnChangeListener((slider1, value, fromUser) -> {
            tgButton2.setText(Conv.nBeerLitres2str(value));
            tg.check(tgButton2.getId());
        });

        checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            textViewArrow.setEnabled(isChecked);
            textFieldABV.setEnabled(!isChecked);
            if (isChecked) {
                editTextABV.setText(Conv.EPMEditText2EBMEditText(editTextEPM.getText().toString()));
            }
        });

        eFabOK.setOnClickListener(v -> {
            Beer b = new Beer();
            b.setPubVisitID(pubVisitID);

            b.setBreweryName(editTextBreweryName.getText().toString());
            b.setDescription(editTextDescription.getText().toString());
            b.setTimestamp(Calendar.getInstance().getTime().getTime());

            int decilitres;
            if (tg.getCheckedButtonId() == tgButton0.getId()) {
                decilitres = 3;
            } else if (tg.getCheckedButtonId() == tgButton1.getId()) {
                decilitres = 5;
            } else {
                decilitres = Math.round(slider.getValue() * 10);
            }
            b.setDecilitres(decilitres);

            double EPM;
            try {
                EPM = Double.parseDouble(editTextEPM.getText().toString());
            } catch (Exception e) {
                EPM = 12d;
            }
            b.setEPM(EPM);

            double ABV;
            try {
                ABV = Double.parseDouble(editTextABV.getText().toString());
            } catch (Exception e) {
                ABV = 4.4d;
            }
            b.setABV(ABV);

            int price;
            try {
                price = Integer.parseInt(editTextPrice.getText().toString());
            } catch (Exception e) {
                price = 0;
            }
            b.setPrice(price);

            ((BeersActivity) requireActivity()).addBeer(b);

            dismiss();
        });
    }
}
