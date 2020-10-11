package com.arrsdesign.oclock.modelSubTask;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.arrsdesign.oclock.R;
import com.arrsdesign.oclock.Register;
import com.arrsdesign.oclock.TaskInput;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Task_Display extends AppCompatActivity {

    FloatingActionButton fab;

    TextView titleTask, startDate, endDate, difficultyNumber, numberPages, numberSub, timeInMinutes, timeInHours, timeInDays;


    RecyclerView subRecycler;
    ProgressBar progress;
    FirebaseDatabase database;
    DatabaseReference reference;
    Integer number = new Random().nextInt();
    ArrayList<TaskInput> inputList;
    Context context;
    AdapterC adapterC;

    ArrayList<TaskInput> taskInputs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task__display);

        titleTask = findViewById(R.id.toolbarTitle);
        startDate = findViewById(R.id.startDate);
        endDate = findViewById(R.id.endDate);
        difficultyNumber = findViewById(R.id.difficultyNumber);
        numberPages = findViewById(R.id.numberPages);
        numberSub = findViewById(R.id.numberSub);
        timeInMinutes = findViewById(R.id.timeInMinutes);
        timeInHours = findViewById(R.id.timeInHours);
        timeInDays = findViewById(R.id.timeInDays);

        inputList = new ArrayList<TaskInput>();

        subRecycler = findViewById(R.id.subTaskRecycler);

        database = FirebaseDatabase.getInstance();

        ImageView back = findViewById(R.id.backArrow);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //Initialize nested Recycler View
        List<SubTaskModel> list = new ArrayList<>();
        SubTaskModel task = new SubTaskModel();
        task.setTask("This is a Test");
        task.setStatus(0);
        task.setId(1);

        list.add(task);
        list.add(task);
        list.add(task);
        list.add(task);
        list.add(task);

        //Initialize Adapter
        SubAdapter subAdapter = new SubAdapter((ArrayList<SubTaskModel>) list);
        LinearLayoutManager subLayoutManager = new LinearLayoutManager(this);
        //Layout Manager
        subRecycler.setLayoutManager(subLayoutManager);
        //set adapter
        subRecycler.setAdapter(subAdapter);

        //Get Firebase Data
        reference = FirebaseDatabase.getInstance().getReference().child("OClock");
        reference.child("OClock").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){

                    String titleTask = snapshot.child("titleTask").getValue().toString();
                    String startDate = snapshot.child("startDate").getValue().toString();
                    String endDate = snapshot.child("endDate").getValue().toString();
                    String difficultyNumber = snapshot.child("difficultyNumber").getValue().toString();
                    String numberPages = snapshot.child("numberPages").getValue().toString();
                    String numberSub = snapshot.child("numberSub").getValue().toString();
                    String timeInMinutes = snapshot.child("timeInMinutes").getValue().toString();
                    String timeInHours = snapshot.child("timeInHours").getValue().toString();
                    String timeInDays = snapshot.child("timeInDays").getValue().toString();

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }

}