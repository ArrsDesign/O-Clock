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

import com.arrsdesign.oclock.R;
import com.arrsdesign.oclock.TaskInput;
import com.arrsdesign.oclock.modelSubTask.AdapterC;
import com.arrsdesign.oclock.modelSubTask.AdapterF;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class Future extends Fragment {

    DatabaseReference reference;
    RecyclerView recyclerView;
    ArrayList<TaskInput> list;
    AdapterF adapterF;
    private FirebaseFirestore mStore;
    private FirebaseAuth mAuth;
    String userID;


    public Future() {
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_future, container, false);

        //Working with data
        recyclerView = view.findViewById(R.id.recyclerC);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        list = new ArrayList<TaskInput>();

        //Get Firebase Data
        mAuth = FirebaseAuth.getInstance();
        mStore = FirebaseFirestore.getInstance();
        userID = mAuth.getCurrentUser().getUid();
        reference = FirebaseDatabase.getInstance().getReference().child("Future Task" + userID);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //retrieve data
                for (DataSnapshot snapshot1: snapshot.getChildren()){
                    TaskInput p = snapshot1.getValue(TaskInput.class);
                    list.add(p);
                }
                adapterF = new AdapterF(getContext(), list);
                recyclerView.setAdapter(adapterF);
                adapterF.notifyDataSetChanged();

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