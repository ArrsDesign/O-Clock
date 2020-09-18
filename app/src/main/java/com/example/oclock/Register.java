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
    private EditText email,password,reEnterPassword;
    private Button registerButton;
    private TextView signInLink;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        firebaseAuth = firebaseAuth.getInstance();
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        reEnterPassword = findViewById(R.id.reEnterPassword);
        registerButton = findViewById(R.id.registerButton);
        signInLink = findViewById(R.id.signInLink);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Register();

            }
        });
        signInLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Register.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
    private void Register(){
        String email1 = email.getText().toString();
        String password1 = password.getText().toString();
        String password2 = reEnterPassword.getText().toString();
        if(TextUtils.isEmpty(email)){
            email.setError("Enter your Email");
            return;
        } else if(TextUtils.isEmpty(password)){
            password.setError("Enter your Password");
            return;
        } else if(TextUtils.isEmpty(reEnterPassword)){
            reEnterPassword.setError("Confirm your Password");
            return;
        } else if(!password.equals(reEnterPassword)){
            reEnterPassword.setError("Different Password");
            return;
        } else if(password.length()<4){
            reEnterPassword.setError("Length should be greater than 4 characters");
            return;
        } else if(!isValidEmail(email)) {
            email.setError("Invalid email");
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