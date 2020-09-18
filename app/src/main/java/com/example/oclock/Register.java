package com.example.oclock;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {
    EditText mUsername,mEmail,mPassword,mReEnterPassword;
    Button mRegisterButton;
    TextView mSignInLink;
    FirebaseAuth fAuth;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mEmail = findViewById(R.id.email);
        mUsername = findViewById(R.id.username);
        mPassword = findViewById(R.id.password);
        mReEnterPassword = findViewById(R.id.reEnterPassword);
        mRegisterButton = findViewById(R.id.registerButton);
        mSignInLink = findViewById(R.id.signInLink);

        fAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBar);

        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    mEmail.setError("Email is Required");
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    mPassword.setError("Password is Required");
                    return;

                }
                if (password.length() < 6) {
                    mPassword.setError("Password MUST be >= 6");
                    return;
                }
                if (fAuth.getCurrentUser() != null) {
                    startActivity(new Intent(getApplicationContext(), Home.class));
                    finish();
                }
                progressBar.setVisibility(View.VISIBLE);

                fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Register.this, "User Created", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), Home.class));
                        } else {
                            Toast.makeText(Register.this, "Error!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                /*
                }
                if (!isValidEmail(email)) {
                    mEmail.setError("Invalid email");
                    return;
                }

                if (TextUtils.isEmpty(mReEnterPassword)) {
                mReEnterPassword.setError("Confirm your Password");
                return;
                 }
                if (!password.equals(password3)) {
                mReEnterPassword.setError("Different Password");
                return;
                 }*/

                    }
                });
            }
        });
        mSignInLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });
    }
}