package com.arrsdesign.oclock;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.core.DatabaseInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Register extends AppCompatActivity {
    public static final String TAG = "TAG";
    private EditText mEmail, mPassword, mPasswordAgain, mUserName;
    private Spinner mReadingSpeed,mWritingSpeed;
    private Button mRegister;
    private ProgressDialog progressDialog;
    private FirebaseAuth mAuth;
    private TextView mSignInLink, mReadingWPM, mWritingWPM;
    private FirebaseFirestore mStore;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mEmail = findViewById(R.id.emailRegister);
        mUserName = findViewById(R.id.username);
        mReadingSpeed = findViewById(R.id.readingDropDown);
        mWritingSpeed = findViewById(R.id.writingDropDown);
        mPassword = findViewById(R.id.passwordRegister);
        mPasswordAgain = findViewById(R.id.reEnterPassword);
        mSignInLink = findViewById(R.id.signInLink);
        mReadingWPM = findViewById(R.id.wpmReading);
        mWritingWPM = findViewById(R.id.wpmWriting);
        mRegister = findViewById(R.id.registerBtn);
        progressDialog = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        mStore = FirebaseFirestore.getInstance();


        List<String> reading = new ArrayList<>();
        reading.add(0, "N/A");
        reading.add("100");
        reading.add("150");
        reading.add("200");
        reading.add("250");
        reading.add("300");

        List<String> writing = new ArrayList<>();
        writing.add(0, "N/A");
        writing.add("100");
        writing.add("150");
        writing.add("200");
        writing.add("250");
        writing.add("300");

        final ArrayAdapter<String> dataReadingAdapter;
        dataReadingAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, reading);

        ArrayAdapter<String> dataWritingAdapter;
        dataWritingAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, writing);

        dataReadingAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataWritingAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        mReadingSpeed.setAdapter(dataReadingAdapter);
        mWritingSpeed.setAdapter(dataReadingAdapter);

        mReadingSpeed.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String readingItem = parent.getItemAtPosition(position).toString();
                    mReadingWPM.setText(readingItem);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                //

            }
        });


        mWritingSpeed.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String writingItem = parent.getItemAtPosition(position).toString();
                    mWritingWPM.setText(writingItem);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                //

            }
        });


        mSignInLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(Register.this, SignIn.class);
                startActivity(intent1);
                finish();
            }
        });
        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newUser();
            }

        });
    }private void newUser() {
        final String email = mEmail.getText().toString();
        String password = mPassword.getText().toString();
        String password2 = mPasswordAgain.getText().toString();
        final String writingSpeed = mWritingWPM.getText().toString();
        final String readingSpeed = mReadingWPM.getText().toString();
        final String username = mUserName.getText().toString();


        if (TextUtils.isEmpty(username)) {
            mUserName.setError("Enter your Username");
            return;
        } else if (TextUtils.isEmpty(email)) {
            mEmail.setError("Enter your Email");
            return;
        }  else if (!isValidEmail(email)) {
            mEmail.setError("Invalid Email");
            return;
        } else if (TextUtils.isEmpty(password)) {
            mPassword.setError("Enter your Password");
            return;
        } else if (password.length() < 6) {
            mPassword.setError("Length should be greater than 6 Characters");
            return;
        } else if (TextUtils.isEmpty(password2)) {
            mPasswordAgain.setError("Confirm your Password");
            return;
        } else if (!password.equals(password2)) {
            mPasswordAgain.setError("Different Password");
            return;
        }
        progressDialog.setMessage("Please wait...");
        progressDialog.show();


        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(Register.this, "Successfully Registered", Toast.LENGTH_LONG).show();
                    userID = mAuth.getCurrentUser().getUid();
                    DocumentReference documentReference = mStore.collection("Users").document(userID);
                    Map<String, Object> user = new HashMap<>();
                    user.put("Username", username);
                    user.put("Email", email);
                    user.put("Reading Speed", readingSpeed);
                    user.put("Writing Speed", writingSpeed);
                    documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d(TAG, "On Success: User profile is created for " + userID);
                        }
                    });
                    startActivity(new Intent(getApplicationContext(),Task2.class));

                } else {
                    Toast.makeText(Register.this, "Error! " + task.getException().getMessage(), Toast.LENGTH_LONG).show();

                }
                progressDialog.dismiss();
            }
        });
    }
    private Boolean isValidEmail(CharSequence target){
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

}



