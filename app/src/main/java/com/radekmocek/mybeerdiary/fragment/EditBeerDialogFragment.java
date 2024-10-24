package com.radekmocek.mybeerdiary.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.radekmocek.mybeerdiary.R;

public class EditBeerDialogFragment extends DialogFragment {

    public static final String TAG = "EditBeerDialogFragment";

    public static EditBeerDialogFragment newInstance() {
        EditBeerDialogFragment f = new EditBeerDialogFragment();

        //Bundle args = new Bundle();
        //f.setArguments(args);

        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogStyle);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_edit_beer, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView textView = view.findViewById(R.id.editBeer_header);
        ExtendedFloatingActionButton eFabRepeat = view.findViewById(R.id.editBeer_eFabRepeat);
        ExtendedFloatingActionButton eFabEdit = view.findViewById(R.id.editBeer_eFabEdit);
        Button buttonDismiss = view.findViewById(R.id.editBeer_buttonDismiss);

        buttonDismiss.setOnClickListener(v -> dismiss());
    }
}
