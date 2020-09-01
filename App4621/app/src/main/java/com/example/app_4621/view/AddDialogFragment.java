package com.example.app_4621.view;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AlertDialog;

import com.example.app_4621.R;
import com.example.app_4621.model.ItemType;

import java.util.Arrays;

public class AddDialogFragment extends DialogFragment {

    public interface AddDialogListener {
        void onDialogPositiveClick(DialogFragment dialog, String itemName, String itemQuantity, String itemType);
        void onDialogNegativeClick(DialogFragment dialog);
    }

    AddDialogListener listener;
    Activity activity;
    Spinner typeSelection;
    EditText nameEntry;
    EditText quantityEntry;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        activity = getActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.add_dialog, null);

        nameEntry = (EditText)view.findViewById(R.id.edit_name);
        quantityEntry = (EditText)view.findViewById(R.id.edit_quantity);
        typeSelection = (Spinner)view.findViewById(R.id.spinner);

        ArrayAdapter adapter = new ArrayAdapter<>(activity, android.R.layout.simple_spinner_item, Arrays.asList((ItemType.values())));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSelection.setAdapter(adapter);

        builder.setMessage("Add item")
                .setView(view)
                .setPositiveButton("Add", (dialog, id) -> listener.onDialogPositiveClick(AddDialogFragment.this, nameEntry.getText().toString(), quantityEntry.getText().toString(), typeSelection.getSelectedItem().toString()))
                .setNegativeButton("Don't Add", (dialog, id) -> listener.onDialogNegativeClick(AddDialogFragment.this));
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (AddDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement NoticeDialogListener");
        }
    }
}
