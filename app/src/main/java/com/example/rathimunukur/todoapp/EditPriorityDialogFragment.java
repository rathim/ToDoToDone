package com.example.rathimunukur.todoapp;

/**
 * Created by rathimunukur on 7/1/16.
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RadioButton;
// ...

public class EditPriorityDialogFragment extends DialogFragment {

    public static String currPriority;
    public RadioButton btnHigh;
    public RadioButton btnMedium;
    public RadioButton btnLow;

    public EditPriorityDialogFragment() {
        // Empty constructor is required for DialogFragment
        // Make sure not to add arguments to the constructor
        // Use `newInstance` instead as shown below
    }

    public static EditPriorityDialogFragment newInstance(String title, String priority) {
        EditPriorityDialogFragment frag = new EditPriorityDialogFragment();
        currPriority = priority;
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_priority, container);
        btnHigh = (RadioButton) view.findViewById(R.id.btnHigh);
        btnMedium = (RadioButton) view.findViewById(R.id.btnMedium);
        btnLow = (RadioButton) view.findViewById(R.id.btnLow);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Fetch arguments from bundle and set title
        String title = getArguments().getString("title", "Enter Name");
        getDialog().setTitle(title);

        switch (currPriority)
        {
            case "High":
                btnHigh.setChecked(true);
                break;
            case "Medium":
                btnMedium.setChecked(true);
                break;
            case "Low":
            default:
                btnLow.setChecked(true);
                break;

        }

        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }
}
