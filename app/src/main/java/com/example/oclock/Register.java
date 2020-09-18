package com.example.oclock;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {
    private EditText emailEt,passwordEt,reEnterPasswordEt;
    private Button registerButtonB;
    private TextView signInLinkL;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        firebaseAuth = firebaseAuth.getInstance();
        emailEt = findViewById(R.id.email);
        passwordEt = findViewById(R.id.password);
        reEnterPasswordEt = findViewById(R.id.reEnterPassword);
        registerButtonB = findViewById(R.id.registerButton);
        signInLinkL = findViewById(R.id.signInLink);
        registerButtonB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Register.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        signInLinkL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Register.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
    private void Register() {
        String email = emailEt.getText().toString();
        String password = passwordEt.getText().toString();
        String password2 = reEnterPasswordEt.getText().toString();
        if (TextUtils.isEmpty(email)) {
            emailEt.setError("Enter your Email");
            return;
        } else if (TextUtils.isEmpty(password)) {
            passwordEt.setError("Enter your Password");
            return;
        } else if (TextUtils.isEmpty(password2)) {
            reEnterPasswordEt.setError("Confirm your Password");
            return;
        } else if (!password.equals(password2)) {
            reEnterPasswordEt.setError("Different Password");
            return;
        } else if (password.length() < 4) {
            reEnterPasswordEt.setError("Length should be greater than 4 characters");
            return;

        } else if (!isValidEmail(email)) {
            emailEt.setError("Invalid email");
            return;
        }

        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(Register.this,"Successful Registration",Toast.LENGTH_LONG).show();
                    Intent intent=new Intent(Register.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    Toast.makeText(Register.this,"Registration Failed",Toast.LENGTH_LONG).show();

                }
            }
        });
    }
    private Boolean isValidEmail(CharSequence target){
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }
}