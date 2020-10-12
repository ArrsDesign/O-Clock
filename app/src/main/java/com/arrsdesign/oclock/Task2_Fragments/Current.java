package com.arrsdesign.oclock.Task2_Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.arrsdesign.oclock.modelSubTask.AdapterC;
import com.arrsdesign.oclock.R;
import com.arrsdesign.oclock.TaskInput;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Current extends Fragment {

    DatabaseReference reference;
    RecyclerView recyclerView;
    ArrayList<TaskInput> list;
    AdapterC adapterC;


    public Current() {
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_current, container, false);

        //Working with data
        recyclerView = view.findViewById(R.id.recyclerC);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        list = new ArrayList<TaskInput>();

        //Get Firebase Data
        reference = FirebaseDatabase.getInstance().getReference().child("OClock");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //retrieve data
                for (DataSnapshot snapshot1: snapshot.getChildren()){
                    TaskInput p = snapshot1.getValue(TaskInput.class);
                    list.add(p);
                }
                adapterC = new AdapterC(getContext(), list);
                recyclerView.setAdapter(adapterC);
                adapterC.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //error code
                Toast.makeText(getContext(), "No Data", Toast.LENGTH_SHORT).show();


            }
        });



        return view;
    }
}




