package com.radekmocek.mybeerdiary.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputLayout;
import com.radekmocek.mybeerdiary.R;
import com.radekmocek.mybeerdiary.activity.BeersActivity;
import com.radekmocek.mybeerdiary.model.Beer;
import com.radekmocek.mybeerdiary.util.Const;
import com.radekmocek.mybeerdiary.util.Conv;
import com.radekmocek.mybeerdiary.util.DecimalDigitsInputFilter;

import java.util.Calendar;

public class AddBeerDialogFragment extends DialogFragment {

    public static final String TAG = "AddBeerDialogFragment";

    private static final String pubVisitIDBundleKey = "pubVisitID";
    private static final String isEditModeBundleKey = "isEditMode";
    private static final String beerBundleKey = "beer";
    private static final String rvPosBundleKey = "recyclerViewPosition";

    public static AddBeerDialogFragment newInstanceAddMode(long pubVisitID) {
        AddBeerDialogFragment f = new AddBeerDialogFragment();

        Bundle args = new Bundle();
        args.putBoolean(isEditModeBundleKey, false);
        args.putLong(pubVisitIDBundleKey, pubVisitID);
        f.setArguments(args);

        return f;
    }

    public static AddBeerDialogFragment newInstanceEditMode(Beer b, int rvPos) {
        AddBeerDialogFragment f = new AddBeerDialogFragment();

        Bundle args = new Bundle();
        args.putBoolean(isEditModeBundleKey, true);
        args.putSerializable(beerBundleKey, b);
        args.putInt(rvPosBundleKey, rvPos);
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
        boolean isEditMode;
        long pubVisitID;
        Beer b;
        int rvPos;
        if (args != null) {
            isEditMode = args.getBoolean(isEditModeBundleKey);
            if (!isEditMode) {
                pubVisitID = args.getLong(pubVisitIDBundleKey);
                b = null;
                rvPos = -1;
            } else {
                pubVisitID = -1;
                b = (Beer) args.getSerializable(beerBundleKey);
                rvPos = args.getInt(rvPosBundleKey);
            }
        } else {
            isEditMode = false;
            pubVisitID = -1;
            b = null;
            rvPos = -1;
        }

        if (isEditMode && b == null) {
            dismiss();
            return; // So the warnings go away
        }

        BeersActivity beersActivity = (BeersActivity) requireActivity();

        Button iconButton = view.findViewById(R.id.newBeer_iconButton);
        MaterialAutoCompleteTextView editTextBreweryName = view.findViewById(R.id.newBeer_editTextBreweryName);
        EditText editTextDescription = view.findViewById(R.id.newBeer_editTextDescription);
        EditText editTextEPM = view.findViewById(R.id.newBeer_editTextEPM);
        EditText editTextABV = view.findViewById(R.id.newBeer_editTextABV);
        EditText editTextPrice = view.findViewById(R.id.newBeer_editTextPrice);
        TextView textViewArrow = view.findViewById(R.id.newBeer_textViewArrow);
        TextInputLayout textFieldABV = view.findViewById(R.id.newBeer_textFieldABV);
        MaterialButtonToggleGroup tg = view.findViewById(R.id.newBeer_toggleGroup);
        Button tgButton0 = view.findViewById(R.id.newBeer_toggleGroup_0);
        Button tgButton1 = view.findViewById(R.id.newBeer_toggleGroup_1);
        Button tgButton2 = view.findViewById(R.id.newBeer_toggleGroup_2);
        Slider slider = view.findViewById(R.id.newBeer_slider);
        CheckBox checkBox = view.findViewById(R.id.newBeer_checkbox);
        ExtendedFloatingActionButton eFabAdd = view.findViewById(R.id.newBeer_eFabAdd);
        ExtendedFloatingActionButton eFabDelete = view.findViewById(R.id.newBeer_eFabDelete);

        if (isEditMode) {
            editTextBreweryName.setText(b.getBreweryName());
            editTextDescription.setText(b.getDescription());
            editTextEPM.setText(String.valueOf(b.getEPM()));
            editTextABV.setText(String.valueOf(b.getABV()));
            editTextPrice.setText(String.valueOf(b.getPrice()));
        }

        // IconButton – Close the dialog on click; "cancel" (X)
        iconButton.setOnClickListener(v -> dismiss());

        // EditText Brewery name – set autocomplete suggestions
        editTextBreweryName.setSimpleItems(Const.BREWERIES);

        // EditText EPM & ABV – set input regex check and EPM→ABV logic
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

        // Slider
        slider.addOnChangeListener((slider1, value, fromUser) -> {
            tgButton2.setText(Conv.nBeerLitres2str(value));
            //tg.check(tgButton2.getId());
        });
        slider.setEnabled(isEditMode);
        slider.setValue((!isEditMode) ? Const.DEFAULT_SLIDER_LITRES : (b.getDecilitres() / 10f));

        // ButtonToggleGroup – default when adding is 0.5l beer, so tick the middle button; when editing it could be anything, so tick the right button
        tg.check((!isEditMode) ? tgButton1.getId() : tgButton2.getId());
        // Slider is enabled only when right button is ticked
        tg.addOnButtonCheckedListener((group, checkedId, isChecked) -> slider.setEnabled(checkedId == tgButton2.getId() && isChecked));

        // Checkbox
        checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            textViewArrow.setEnabled(isChecked);
            textFieldABV.setEnabled(!isChecked);
            if (isChecked) {
                editTextABV.setText(Conv.EPMEditText2EBMEditText(editTextEPM.getText().toString()));
            }
        });

        // Button to confirm adding new beer / editing existing beer
        if (isEditMode) {
            eFabAdd.setText("Potvrdit");
            eFabAdd.setIconResource(R.drawable.ico_check);
        }
        eFabAdd.setOnClickListener(v -> {
            Beer newB = new Beer();
            newB.setPubVisitID(pubVisitID);

            newB.setBreweryName(editTextBreweryName.getText().toString());
            newB.setDescription(editTextDescription.getText().toString());
            newB.setTimestamp(Calendar.getInstance().getTime().getTime());

            int decilitres;
            if (tg.getCheckedButtonId() == tgButton0.getId()) {
                decilitres = 3;
            } else if (tg.getCheckedButtonId() == tgButton1.getId()) {
                decilitres = 5;
            } else {
                decilitres = Math.round(slider.getValue() * 10);
            }
            newB.setDecilitres(decilitres);

            double EPM;
            try {
                EPM = Double.parseDouble(editTextEPM.getText().toString());
            } catch (Exception e) {
                EPM = Const.DEFAULT_EPM;
            }
            newB.setEPM(EPM);

            double ABV;
            try {
                ABV = Double.parseDouble(editTextABV.getText().toString());
            } catch (Exception e) {
                ABV = Const.DEFAULT_ABV;
            }
            newB.setABV(ABV);

            int price;
            try {
                price = Integer.parseInt(editTextPrice.getText().toString());
            } catch (Exception e) {
                price = 0;
            }
            newB.setPrice(price);

            if (!isEditMode) {
                beersActivity.addBeer(newB);
            } else {
                //newB.setPubVisitID(b.getPubVisitID());
                //newB.setTimestamp(b.getTimestamp());
                beersActivity.editBeer(b.getId(), newB, rvPos);
            }

            dismiss();
        });

        // Button to delete beer when editing
        if (!isEditMode) {
            eFabDelete.setVisibility(View.GONE);
        }
        eFabDelete.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setCancelable(true);
            builder.setTitle("Smazat Pivo?");
            builder.setMessage("Opravdu si přejete smazat \"" + Conv.beer2str(b) + "\"?");
            builder.setPositiveButton("Smazat", (dialog, which) -> beersActivity.deleteBeer(b, rvPos));
            builder.setNegativeButton("Zrušit", (dialog, which) -> {
            });
            AlertDialog dialog = builder.create();
            dialog.show();
            dismiss();
        });
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        ((BeersActivity) requireActivity()).enableFAB();
    }
}
