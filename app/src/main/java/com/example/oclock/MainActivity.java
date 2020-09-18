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

public class MainActivity extends AppCompatActivity {
    private EditText emailEt,passwordEt;
    private Button logInB;
    private TextView registerL;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseAuth = firebaseAuth.getInstance();
        emailEt = findViewById(R.id.email);
        passwordEt = findViewById(R.id.password);
        logInB = findViewById(R.id.logIn);
        registerL = findViewById(R.id.register);
        logInB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,Home.class);
                startActivity(intent);
                finish();
            }
        });
        registerL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,Register.class);
                startActivity(intent);
                finish();
            }
        });
    }
    private void Login(){
        String email = emailEt.getText().toString();
        String password = passwordEt.getText().toString();
        if(TextUtils.isEmpty(email)){
            emailEt.setError("Enter your Email");
            return;
        } else if(TextUtils.isEmpty(password)){
            passwordEt.setError("Enter your Password");
            return;
        }
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(MainActivity.this,"Successful Log In",Toast.LENGTH_LONG).show();
                    Intent intent=new Intent(MainActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    Toast.makeText(MainActivity.this,"Log In Failed",Toast.LENGTH_LONG).show();

                }
            }
        });

    }
}