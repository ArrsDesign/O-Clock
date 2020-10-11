package com.arrsdesign.oclock.modelSubTask;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SeekBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.arrsdesign.oclock.R;
import com.arrsdesign.oclock.Task2;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.Random;

import static com.arrsdesign.oclock.Register.TAG;

public class AddNewTask extends BottomSheetDialogFragment {

    private EditText newTaskText;
    private Button newTaskSaveButton;
    DatabaseReference reference;
    Integer number = new Random().nextInt();

    public AddNewTask() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.new_task, container, false);

        //Task Title
        newTaskText = view.findViewById(R.id.newTaskText);

        //Button
        newTaskSaveButton = view.findViewById(R.id.newSubTaskButton);

        newTaskSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Insert data to database
                reference = FirebaseDatabase.getInstance().getReference().child("OClock").child("Current Task-Sub Task" + number);
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        snapshot.getRef().child("task").setValue(newTaskText.getText().toString());

                        Intent b = new Intent(getContext(), AdapterC.class);
                        startActivity(b);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        return view;

    }

}



