package com.arrsdesign.oclock.modelSubTask;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;

import com.arrsdesign.oclock.R;

public class SubTask extends AppCompatActivity {

    RecyclerView subTaskRecycler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_task);

        subTaskRecycler = findViewById(R.id.subTaskRecycler);
        subTaskRecycler.setLayoutManager(new LinearLayoutManager(this));


    }

}