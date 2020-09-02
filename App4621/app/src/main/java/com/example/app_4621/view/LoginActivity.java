package com.example.app_4621.view;

import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.app_4621.R;
import com.example.app_4621.Util;
import com.example.app_4621.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity implements RegisterDialogFragment.RegisterDialogListener {
    public static final String EXTRA_MESSAGE = "com.example.app_4621.MESSAGE";
    private Button registerButton;
    private EditText emailEditText;
    private FirebaseAuth auth;
    private FirebaseDatabase db;

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

//        FirebaseOptions options = new FirebaseOptions.Builder()
//                .setApplicationId("1:337003824867:android:46e3d81cfd083b4ec6e7d6") // Required for Analytics.
//                .setApiKey("AIzaSyAGLPj6d_uHqmTBRLtg-I9Cy2_8s0MIAXM") // Required for Auth.
//                .setDatabaseUrl("https://twowayuserproject.firebaseio.com") // Required for RTDB.
//                .build();
//        FirebaseApp.initializeApp(this /* Context */, options, "secondary");

       // FirebaseApp app = FirebaseApp.getInstance("secondary");
        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();

    }

    public void login(View view) {
        Intent intent = new Intent(this, GroceryListActivity.class);

        emailEditText = (EditText) findViewById(R.id.email_text);
        String email = emailEditText.getText().toString();
        EditText passwordEditText = (EditText) findViewById(R.id.password_text);
        String password = passwordEditText.getText().toString();
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "login:success with uid " + task.getResult().getUser().getUid());
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "login:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
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

    private void saveUserToDatabase(String email) {
        String uid = FirebaseAuth.getInstance().getUid();
        if (uid == null) {
            uid = "";
        }
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users/" + uid);
        ref.setValue(new User(uid, email))
                .addOnSuccessListener(aVoid -> {
                    Log.d("TAG", "successfuly added user to database ");
                })
                .addOnFailureListener(e -> Log.d("TAG", "failed to add user to database: "));
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog, String email, String password) {
        if (email.equals("") || password.equals("")) {
            Toast.makeText(LoginActivity.this, "Must provide email and password to register",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "createUserWithEmail:success with uid " + task.getResult().getUser().getUid());
                            FirebaseUser user = auth.getCurrentUser();
                            saveUserToDatabase(email);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }
                    }
                });
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
    }
}