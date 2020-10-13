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
import android.view.Menu;
import android.view.MenuInflater;
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
import java.util.concurrent.FutureTask;

public class Task_Display extends AppCompatActivity {

    FloatingActionButton fab;

    TextView titleTask, start, end, difficulty, pages, sub, minutes, hours, days;


    RecyclerView subRecycler;
    ProgressBar progress;
    FirebaseDatabase database;
    DatabaseReference reference;
    Integer number = new Random().nextInt();
    ArrayList<TaskInput> inputList;
    Context context;
    AdapterC adapterC;
    LinearLayout layout;

    FloatingActionButton subTaskAdd;

    ArrayList<SubTaskModel> list;
    SubAdapter subAdapter;

    String receiveTask;
    ArrayList<TaskInput> listOG;
    AdapterF adapterF;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task__display);

        titleTask = findViewById(R.id.titleTask);
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

        subTaskAdd = findViewById(R.id.subTaskAdd);

        subRecycler = findViewById(R.id.subTaskRecycler);

        receiveTask = getIntent().getStringExtra("task");

        ImageView back = findViewById(R.id.backArrow);
        ImageView more = findViewById(R.id.more);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        reference = FirebaseDatabase.getInstance().getReference().child("Future Task").child("Sub Task");

        subTaskAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheet bottomSheet = new BottomSheet();
                bottomSheet.show(getSupportFragmentManager(), "New Task");

            }
        });

        //Initialize nested Recycler View
        list = new ArrayList<SubTaskModel>();

        //Initialize Adapter
        LinearLayoutManager subLayoutManager = new LinearLayoutManager(this);
        //Layout Manager
        subRecycler.setLayoutManager(subLayoutManager);

        //Get Firebase Data
        listOG = new ArrayList<TaskInput>();

        //Get Firebase Data
        reference = FirebaseDatabase.getInstance().getReference().child("Future Task");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //retrieve data
                for (DataSnapshot snapshot1: snapshot.getChildren()){
                    SubTaskModel p = snapshot1.getValue(SubTaskModel.class);
                    list.add(p);
                }
                subAdapter = new SubAdapter(Task_Display.this, list);
                subRecycler.setAdapter(subAdapter);
                adapterF.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //error code
                Toast.makeText(context, "No Data", Toast.LENGTH_SHORT).show();


            }
        });

    }

}