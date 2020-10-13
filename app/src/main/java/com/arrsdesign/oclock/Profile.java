package com.arrsdesign.oclock;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class Profile extends AppCompatActivity {
    TextView pEmail,pUsername,pReadingSpeed,pWritingSpeed;
    FirebaseAuth pAuth;
    FirebaseFirestore pStore;
    String userID;
    Button pForgot;
    BottomAppBar bottomAppBar;
    FloatingActionButton fButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ImageView back = findViewById(R.id.backArrow);
        TextView edit = findViewById(R.id.edit);
        TextView title = findViewById(R.id.toolbarTitle);
        pEmail=findViewById(R.id.userEmail);
        pUsername=findViewById(R.id.userName);
        pAuth=FirebaseAuth.getInstance();
        pStore=FirebaseFirestore.getInstance();
        userID=pAuth.getCurrentUser().getUid();
        pForgot=findViewById(R.id.resetPasswordBtn);
        pReadingSpeed=findViewById(R.id.readingSpeed);
        pWritingSpeed=findViewById(R.id.writingSpeed);
        fButton = findViewById(R.id.taskCreation);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Profile.this, Task2.class));
            }
        });

        title.setText("PROFILE");


        fButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Profile.this, Task_Creation2.class));
            }
        });

        final DocumentReference documentReference = pStore.collection("Users").document(userID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                pEmail.setText(documentSnapshot.getString("Email"));
                pUsername.setText(documentSnapshot.getString("Username"));
                pWritingSpeed.setText(documentSnapshot.getString("Writing Speed"));
                pReadingSpeed.setText(documentSnapshot.getString("Reading Speed"));
            }
        });


    }

    public void logOut(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(),SignIn.class));
        finish();
    }
}