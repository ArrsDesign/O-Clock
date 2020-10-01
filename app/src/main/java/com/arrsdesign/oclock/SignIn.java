package com.arrsdesign.oclock;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignIn extends AppCompatActivity {
    private EditText mEmailLogIn, mPasswordLogIn;
    private Button mLogIn;
    private ProgressDialog progressDialog;
    private FirebaseAuth mAuth;
    private TextView mRegisterLink, mForgot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        mEmailLogIn = findViewById(R.id.emailLogIn);
        mPasswordLogIn = findViewById(R.id.passwordLogIn);
        mRegisterLink = findViewById(R.id.registerLink);
        mLogIn = findViewById(R.id.logInBtn);
        progressDialog = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        mForgot = findViewById(R.id.forgotPassword);



        if(mAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(), Task2.class));
            finish();
        }



        mRegisterLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerLink = new Intent(SignIn.this, Register.class);
                startActivity(registerLink);
                finish();

            }
        });
        mLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentUser();
            }

        });
    }


    private void currentUser() {
        String email1 = mEmailLogIn.getText().toString();
        String password1 = mPasswordLogIn.getText().toString();
        if (TextUtils.isEmpty(email1)) {
            mEmailLogIn.setError("Enter your Email");
            return;
        } else if (TextUtils.isEmpty(password1)) {
            mPasswordLogIn.setError("Enter your Password");
            return;
        } else if (password1.length() < 6) {
            mPasswordLogIn.setError("Length should be greater than 6 Characters");
            return;
        }
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        mAuth.signInWithEmailAndPassword(email1, password1).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(SignIn.this, "Successfully Logged In", Toast.LENGTH_LONG).show();
                    Intent logIn = new Intent(SignIn.this, Task2.class);
                    startActivity(logIn);
                } else {
                    Toast.makeText(SignIn.this, "Error! " + task.getException().getMessage(), Toast.LENGTH_LONG).show();

                }
                progressDialog.dismiss();
            }
        });

        mForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText resetPass = new EditText(v.getContext());
                final AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(v.getContext());
                passwordResetDialog.setTitle("Reset Password?");
                passwordResetDialog.setMessage("Enter your Email to receive reset link.");
                passwordResetDialog.setView(resetPass);

                passwordResetDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Extract Email and send link
                        String mail = resetPass.getText().toString();
                        mAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(SignIn.this, "Reset Link sent to your Email.", Toast.LENGTH_LONG).show();

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(SignIn.this, "ERROR! Reset Link NOT sent." + e.getMessage(), Toast.LENGTH_LONG).show();

                            }
                        });

                    }
                });

                passwordResetDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Close Dialog
                    }
                });
            }
        });
    }
}