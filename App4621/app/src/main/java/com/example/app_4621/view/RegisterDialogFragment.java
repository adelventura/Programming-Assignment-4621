package com.example.app_4621.view;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;

import com.example.app_4621.R;

public class RegisterDialogFragment extends DialogFragment {

    public interface RegisterDialogListener {
        void onDialogPositiveClick(DialogFragment dialog, String username, String password);
        void onDialogNegativeClick(DialogFragment dialog);
    }

    RegisterDialogListener listener;
    Activity activity;
    EditText usernameEntry;
    EditText passwordEntry;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        activity = getActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.register_dialog, null);

        usernameEntry = (EditText)view.findViewById(R.id.edit_username);
        passwordEntry = (EditText)view.findViewById(R.id.edit_password);

        builder.setMessage("Register as new user")
                .setView(view)
                .setPositiveButton("Register", (dialog, id) -> listener.onDialogPositiveClick(RegisterDialogFragment.this, usernameEntry.getText().toString(), passwordEntry.getText().toString()))
                .setNegativeButton("Cancel", (dialog, id) -> listener.onDialogNegativeClick(RegisterDialogFragment.this));
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (RegisterDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement NoticeDialogListener");
        }
    }
}
