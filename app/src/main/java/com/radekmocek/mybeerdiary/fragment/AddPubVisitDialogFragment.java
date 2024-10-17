package com.radekmocek.mybeerdiary.fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.radekmocek.mybeerdiary.R;
import com.radekmocek.mybeerdiary.activity.MainActivity;

public class AddPubVisitDialogFragment extends DialogFragment {

    public static final String TAG = "AddPubVisitDialogFragment";

    public static AddPubVisitDialogFragment newInstance() {
        return new AddPubVisitDialogFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogStyle);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_new_pub_visit, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        EditText editText = view.findViewById(R.id.newPubVisit_editText);
        Button buttonOK = view.findViewById(R.id.newPubVisit_buttonOK);
        Button buttonCancel = view.findViewById(R.id.newPubVisit_buttonCancel);

        editText.requestFocus();
        buttonOK.setEnabled(false);

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                buttonOK.setEnabled(!s.toString().isEmpty());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        buttonOK.setOnClickListener(v -> {
            ((MainActivity) requireActivity()).addPubVisit(String.valueOf(editText.getText()));
            dismiss();
        });

        buttonCancel.setOnClickListener(v -> dismiss());
    }
}
