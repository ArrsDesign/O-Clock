package com.example.oclock;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {
    private EditText mEmail, mPassword, mPasswordAgain;
    private Button mRegister;
    private TextView mSignIn;
    private ProgressDialog progressDialog;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mEmail = findViewById(R.id.emailRegister);
        mPassword = findViewById(R.id.passwordRegister);
        mPasswordAgain = findViewById(R.id.reEnterPassword);
        mSignIn = findViewById(R.id.signInLink);
        mRegister = findViewById(R.id.registerBtn);
        progressDialog = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();

        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Register();

            }
        });
        mSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Register.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
    private void Register() {
        String email = mEmail.getText().toString();
        String password = mPassword.getText().toString();
        String password2 = mPasswordAgain.getText().toString();
        if (TextUtils.isEmpty(email)) {
            mEmail.setError("Enter your Email");
            return;
        } else if (TextUtils.isEmpty(password)) {
            mPassword.setError("Enter your Password");
            return;
        } else if (TextUtils.isEmpty(password2)) {
            mPasswordAgain.setError("Confirm your Password");
            return;
        } else if (!password.equals(password2)) {
            mPasswordAgain.setError("Different Password");
            return;
        } else if (password.length() < 6) {
            mPasswordAgain.setError("Length should be greater than 6 Characters");
            return;
        } else if (!isValidEmail(email)) {
            mEmail.setError("Invalid Email");
            return;
        }
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(Register.this, "Successfully Registered", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(Register.this, Home.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    Toast.makeText(Register.this, "Registration Failed", Toast.LENGTH_LONG).show();
                }
                progressDialog.dismiss();

            }
        });
    }
    private Boolean isValidEmail(CharSequence target){
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }
}
