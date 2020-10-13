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
import android.widget.LinearLayout;
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

    TextView title, start, end, difficulty, pages, sub, minutes, hours, days;


    RecyclerView subRecycler;
    ProgressBar progress;
    FirebaseDatabase database;
    DatabaseReference reference;
    Integer number = new Random().nextInt();
    ArrayList<TaskInput> inputList;
    Context context;
    AdapterC adapterC;
    LinearLayout layout;

    ArrayList<TaskInput> taskInputs;

    String receiveTask;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task__display);

        title = findViewById(R.id.titleTask);
        start = findViewById(R.id.startDate);
        end = findViewById(R.id.endDate);
        difficulty = findViewById(R.id.difficultyNumber);
        pages = findViewById(R.id.numberPages);
        sub = findViewById(R.id.numberSub);
        minutes = findViewById(R.id.timeInMinutes);
        hours = findViewById(R.id.timeInHours);
        days = findViewById(R.id.timeInDays);

        inputList = new ArrayList<TaskInput>();
        layout = findViewById(R.id.dataHolder);

        subRecycler = findViewById(R.id.subTaskRecycler);

        database = FirebaseDatabase.getInstance();

        receiveTask = getIntent().getStringExtra("task");

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

        //Initialize Adapter
        SubAdapter subAdapter = new SubAdapter((ArrayList<SubTaskModel>) list);
        LinearLayoutManager subLayoutManager = new LinearLayoutManager(this);
        //Layout Manager
        subRecycler.setLayoutManager(subLayoutManager);
        //set adapter
        subRecycler.setAdapter(subAdapter);

        //Get Firebase Data
        reference = FirebaseDatabase.getInstance().getReference().child("OClock");

        reference.child(receiveTask).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1: snapshot.getChildren()){
                    String titleTask = snapshot1.child("titleTask").getValue().toString();
                    String startDate = snapshot1.child("startDate").getValue().toString();
                    String end = snapshot1.child("endDate").getValue().toString();
                    String difficulty = snapshot1.child("difficultyNumber").getValue().toString();
                    String pages = snapshot1.child("numberPages").getValue().toString();
                    String sub = snapshot1.child("numberSub").getValue().toString();
                    String minutes = snapshot1.child("timeInMinutes").getValue().toString();
                    String hours = snapshot1.child("timeInHours").getValue().toString();
                    String days = snapshot1.child("timeInDays").getValue().toString();

                    title.setText(titleTask);
                    start.setText(startDate);


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

}