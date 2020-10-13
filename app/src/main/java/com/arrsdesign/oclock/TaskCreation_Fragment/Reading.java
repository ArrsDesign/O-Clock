package com.arrsdesign.oclock.TaskCreation_Fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.arrsdesign.oclock.R;
import com.arrsdesign.oclock.Task2;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static com.arrsdesign.oclock.Register.TAG;
import static com.google.firebase.database.FirebaseDatabase.getInstance;

public class Reading extends Fragment {
    Button createTask;
    EditText taskName, pages, subTask;
    TextView selectedDifficulty, dateStart, dateEnd, minute, hour, day;
    SeekBar difficulty;
    DatePickerDialog.OnDateSetListener dateSetListenerStart;
    DatePickerDialog.OnDateSetListener dateSetListenerEnd;
    Integer number = new Random().nextInt();
    String key = Integer.toString(number);
    DatabaseReference reference, referenceFuture;


    public Reading() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reading, container, false);

        //Task Title
        taskName = view.findViewById(R.id.taskNameR);

        //Date Selection
        dateStart = view.findViewById(R.id.editTextDateStart);
        dateEnd = view.findViewById(R.id.editTextDateEnd);

        //Pages and Sub
        pages = view.findViewById(R.id.numberOfPagesR);
        subTask = view.findViewById(R.id.numberOfSubTasksR);

        //Difficulty
        difficulty = view.findViewById(R.id.difficultySelectionR);
        selectedDifficulty = view.findViewById(R.id.selectedDifficultyR);

        //Time Calculation
        minute = view.findViewById(R.id.timeInMinutes);
        hour = view.findViewById(R.id.timeInHours);
        day = view.findViewById(R.id.timeInDays);



        //Button
        createTask = view.findViewById(R.id.createTaskBtnR);

        //Insert data to database
        reference = FirebaseDatabase.getInstance().getReference().child("Current Task").child("Task " + number);
        referenceFuture = FirebaseDatabase.getInstance().getReference().child("Future Task").child("Task " + number);


        //Start Date Selection
        dateStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(getActivity(), android.R.style.Theme_Holo_Light_Dialog_MinWidth, dateSetListenerStart, year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();


            }
        });

        dateSetListenerStart = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                String dateStartString = month + "/" + day + "/" + year;
                Log.d(TAG, "On Date Set DATE:" + year + month + day);
                dateStart.setText(dateStartString);

            }
        };

        //Deadline Selection
        dateEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year2 = calendar.get(Calendar.YEAR);
                int month2 = calendar.get(Calendar.MONTH);
                int day2 = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(getActivity(), android.R.style.Theme_Holo_Light_Dialog_MinWidth, dateSetListenerEnd, year2, month2, day2);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();


            }
        });

        dateSetListenerEnd = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year2, int month2, int day2) {
                String dateEndString = month2 + "/" + day2 + "/" + year2;
                Log.d(TAG, "On Date Set DATE:" + year2 + month2 + day2);
                dateEnd.setText(dateEndString);

            }
        };

        //Difficulty Selection
        difficulty.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                selectedDifficulty.setText(progress + "/10");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        createTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Task Created");
                builder.setMessage("Where would you like to send your task?");

                builder.setPositiveButton("Current Task", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        reference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                snapshot.getRef().child("titleTask").setValue(taskName.getText().toString());
                                snapshot.getRef().child("startDate").setValue(dateStart.getText().toString());
                                snapshot.getRef().child("endDate").setValue(dateEnd.getText().toString());
                                snapshot.getRef().child("difficultyNumber").setValue(selectedDifficulty.getText().toString());
                                snapshot.getRef().child("numberPages").setValue(pages.getText().toString());
                                snapshot.getRef().child("numberSub").setValue(subTask.getText().toString());
                                snapshot.getRef().child("timeInMinutes").setValue(minute.getText().toString());
                                snapshot.getRef().child("timeInHours").setValue(hour.getText().toString());
                                snapshot.getRef().child("timeInDays").setValue(day.getText().toString());

                                snapshot.getRef().child("key").setValue(key);

                                Intent a = new Intent(getContext(), Task2.class);
                                startActivity(a);

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });


                    }
                });


                builder.setNegativeButton("Future Task", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        referenceFuture.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                snapshot.getRef().child("titleTask").setValue(taskName.getText().toString());
                                snapshot.getRef().child("startDate").setValue(dateStart.getText().toString());
                                snapshot.getRef().child("endDate").setValue(dateEnd.getText().toString());
                                snapshot.getRef().child("difficultyNumber").setValue(selectedDifficulty.getText().toString());
                                snapshot.getRef().child("numberPages").setValue(pages.getText().toString());
                                snapshot.getRef().child("numberSub").setValue(subTask.getText().toString());
                                snapshot.getRef().child("timeInMinutes").setValue(minute.getText().toString());
                                snapshot.getRef().child("timeInHours").setValue(hour.getText().toString());
                                snapshot.getRef().child("timeInDays").setValue(day.getText().toString());

                                snapshot.getRef().child("key").setValue(key);

                                Intent b = new Intent(getContext(), Task2.class);
                                startActivity(b);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                    }
                });

                builder.show();


            }
        });

        return view;
    }
}
