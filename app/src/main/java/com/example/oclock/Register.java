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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Register extends AppCompatActivity {
    private EditText mUsername,mEmail,mPassword,mReEnterPassword;
    Button mRegisterButton;
    TextView mSignInLink;
    private FirebaseAuth fAuth;
    ProgressBar progressBar;
    private String email;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mEmail = findViewById(R.id.email);
        //mUsername = findViewById(R.id.username);
        mPassword = findViewById(R.id.password);
        //mReEnterPassword = findViewById(R.id.reEnterPassword);
        mRegisterButton = findViewById(R.id.registerBtn);
        mSignInLink = findViewById(R.id.signInLink);


        fAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBar);

        if(fAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(), Profile.class));
            finish();
        }

        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = mEmail.getText().toString();
                password = mPassword.getText().toString();

                if (email.isEmpty()) {
                    mEmail.setError("Email is Required");
                    mEmail.requestFocus();
                }

                else if (password.length() < 6) {
                    mPassword.setError("Password MUST be >= 6");
                    mEmail.requestFocus();
                }
                else {
                    fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            //Account Created

                            if(task.isSuccessful())
                            {
                                //Send to different Activity
                                FirebaseUser user = fAuth.getCurrentUser();
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                finish();
                            }


                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            //Account Not Created

                            Toast.makeText(Register.this, "Error", Toast.LENGTH_SHORT).show();

                        }
                    });

                }
            }
        });

    }
}