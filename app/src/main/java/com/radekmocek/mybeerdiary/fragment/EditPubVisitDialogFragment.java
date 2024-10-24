package com.radekmocek.mybeerdiary.fragment;

import android.app.AlertDialog;
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
import com.radekmocek.mybeerdiary.model.PubVisit;

public class EditPubVisitDialogFragment extends DialogFragment {

    public static final String TAG = "EditPubVisitDialogFragment";

    private static final String idBundleKey = "id";
    private static final String pubNameBundleKey = "name";
    private static final String rvPosBundleKey = "recyclerViewPosition";

    public static EditPubVisitDialogFragment newInstance(PubVisit p, int rvPos) {
        EditPubVisitDialogFragment f = new EditPubVisitDialogFragment();

        Bundle args = new Bundle();
        args.putString(pubNameBundleKey, p.getPubName());
        args.putLong(idBundleKey, p.getId());
        args.putInt(rvPosBundleKey, rvPos);
        f.setArguments(args);

        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogStyle);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_edit_pub_visit, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle args = getArguments();
        long id;
        int rvPos;
        String name;
        if (args != null) {
            id = args.getInt(idBundleKey);
            name = args.getString(pubNameBundleKey);
            rvPos = args.getInt(rvPosBundleKey);
        } else {
            id = -1;
            name = "";
            rvPos = -1;
        }

        EditText editText = view.findViewById(R.id.editPubVisit_editText);
        Button buttonOK = view.findViewById(R.id.editPubVisit_buttonOK);
        Button buttonCancel = view.findViewById(R.id.editPubVisit_buttonCancel);
        Button buttonDelete = view.findViewById(R.id.editPubVisit_buttonDelete);

        editText.setText(name);

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

        MainActivity mainActivity = (MainActivity) requireActivity();

        buttonOK.setOnClickListener(v -> {
            mainActivity.editPubVisitName(id, String.valueOf(editText.getText()), rvPos);
            dismiss();
        });

        buttonCancel.setOnClickListener(v -> dismiss());

        buttonDelete.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setCancelable(true);
            builder.setTitle("Smazat návštěvu hospody / akce?");
            builder.setMessage("Opravdu si přejete smazat \"" + name + "\"?");
            builder.setPositiveButton("Smazat", (dialog, which) -> mainActivity.deletePubVisit(id, rvPos));
            builder.setNegativeButton("Zrušit", (dialog, which) -> {
            });
            AlertDialog dialog = builder.create();
            dialog.show();
            dismiss();
        });
    }
}
