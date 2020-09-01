package com.example.app_4621.view;

import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.app_4621.R;
import com.example.app_4621.Util;

public class LoginActivity extends AppCompatActivity implements RegisterDialogFragment.RegisterDialogListener{
    public static final String EXTRA_MESSAGE = "com.example.app_4621.MESSAGE";
    private Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Util.themeStatusBar(this, false);
        registerButton = findViewById(R.id.register_button);
        registerButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showRegisterDialog();
            }
        });
    }

    public void sendMessage(View view) {
        Intent intent = new Intent(this, GroceryListActivity.class);
        EditText editText = (EditText) findViewById(R.id.email_text);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

    public void showRegisterDialog() {
        FragmentManager manager = getFragmentManager();
        Fragment frag = manager.findFragmentByTag("fragment_register_user");

        if (frag != null) {
            manager.beginTransaction().remove(frag).commit();
        }
        RegisterDialogFragment dialog = new RegisterDialogFragment();
        dialog.show(manager, "fragment_register_user");
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog, String username, String password) {
        // verify
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {}
}