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
import com.radekmocek.mybeerdiary.activity.BeersActivity;
import com.radekmocek.mybeerdiary.model.Beer;
import com.radekmocek.mybeerdiary.util.Conv;

import java.util.Calendar;

public class EditBeerDialogFragment extends DialogFragment {

    public static final String TAG = "EditBeerDialogFragment";

    private static final String beerBundleKey = "beer";
    private static final String rvPosBundleKey = "recyclerViewPosition";

    public static EditBeerDialogFragment newInstance(Beer b, int rvPos) {
        EditBeerDialogFragment f = new EditBeerDialogFragment();

        Bundle args = new Bundle();
        args.putSerializable(beerBundleKey, b);
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
        return inflater.inflate(R.layout.dialog_edit_beer, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle args = getArguments();
        Beer b;
        int rvPos;
        if (args != null) {
            b = (Beer) args.getSerializable(beerBundleKey);
            rvPos = args.getInt(rvPosBundleKey);
        } else {
            b = null;
            rvPos = -1;
        }

        TextView textView = view.findViewById(R.id.editBeer_header);
        ExtendedFloatingActionButton eFabRepeat = view.findViewById(R.id.editBeer_eFabRepeat);
        ExtendedFloatingActionButton eFabEdit = view.findViewById(R.id.editBeer_eFabEdit);
        Button buttonDismiss = view.findViewById(R.id.editBeer_buttonDismiss);

        textView.setText(Conv.beer2str(b));

        eFabRepeat.setOnClickListener(v -> {
            if (b != null) {
                Beer newB = b.cloneWithoutIDAndTimestamp();
                newB.setTimestamp(Calendar.getInstance().getTime().getTime());
                ((BeersActivity) requireActivity()).addBeer(newB);
            }
            dismiss();
        });

        eFabEdit.setOnClickListener(v -> {
            AddBeerDialogFragment.newInstanceEditMode(b, rvPos).show(getParentFragmentManager(), AddBeerDialogFragment.TAG);
            dismiss();
        });

        buttonDismiss.setOnClickListener(v -> dismiss());
    }
}
