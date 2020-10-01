package com.arrsdesign.oclock;

import android.content.Intent;
import android.os.Bundle;

import com.arrsdesign.oclock.ui.main.SectionsPagerAdapterTask;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.arrsdesign.oclock.ui.main.SectionsPagerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class Task2 extends AppCompatActivity {
    ImageView back;
    FirebaseAuth pAuth;
    FirebaseFirestore pStore;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task2);
        SectionsPagerAdapterTask sectionsPagerAdapter = new SectionsPagerAdapterTask(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        back = findViewById(R.id.backArrow);
        pAuth= FirebaseAuth.getInstance();
        pStore= FirebaseFirestore.getInstance();
        userID=pAuth.getCurrentUser().getUid();
        ImageView profile = findViewById(R.id.profile);
        final TextView title = findViewById(R.id.name);

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Task2.this, Profile.class));
            }
        });

        //Page Name
        final DocumentReference documentReference = pStore.collection("Users").document(userID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                title.setText(documentSnapshot.getString("Username"));
            }
        });

        FloatingActionButton floatingActionButton = findViewById(R.id.taskCreation);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Task2.this, Task_Creation2.class));
            }
        });

    }
}