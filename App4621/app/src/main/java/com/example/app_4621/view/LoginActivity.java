package com.example.app_4621.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.app_4621.R;
import com.example.app_4621.Util;

public class LoginActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.app_4621.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Util.themeStatusBar(this, false);
    }

    public void sendMessage(View view) {
        Intent intent = new Intent(this, GroceryListActivity.class);
        EditText editText = (EditText) findViewById(R.id.email_text);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }
}