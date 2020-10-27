package com.arrsdesign.oclock.TaskCreation_Fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.arrsdesign.oclock.R;
import com.arrsdesign.oclock.Task2;
import com.arrsdesign.oclock.Task2_Fragments.Current;
import com.arrsdesign.oclock.Task2_Fragments.Future;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.annotation.Nullable;

import static com.arrsdesign.oclock.Register.TAG;

public class Writing extends Fragment {
    Button createTask;
    EditText taskName, pages, subTask;
    TextView selectedDifficulty, dateStart, dateEnd, minute, hour, day;
    SeekBar difficulty;
    DatePickerDialog.OnDateSetListener dateSetListenerStart;
    DatePickerDialog.OnDateSetListener dateSetListenerEnd;
    Integer number = new Random().nextInt();
    String key = Integer.toString(number);
    DatabaseReference reference, referenceFuture;
    ImageView info, infoPages, infoSubTasks, infoDuration;
    private FirebaseFirestore mStore;
    private FirebaseAuth mAuth;
    String userID;


    public Writing() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_writing, container, false);

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

        //Information Button
        info = view.findViewById(R.id.info);
        infoPages = view.findViewById(R.id.infoPages);
        infoSubTasks = view.findViewById(R.id.infoSubTasks);
        infoDuration = view.findViewById(R.id.infoDuration);

        //Button
        createTask = view.findViewById(R.id.createTaskBtnR);

        //Insert data to database
        mAuth = FirebaseAuth.getInstance();
        mStore = FirebaseFirestore.getInstance();
        userID = mAuth.getCurrentUser().getUid();
        reference = FirebaseDatabase.getInstance().getReference().child("Current Task" + userID).child("Task"+number);
        referenceFuture = FirebaseDatabase.getInstance().getReference().child("Future Task" + userID).child("Task"+number);

        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "The difficulty number selected will assist in the calculation of the estimated duration of the task.", Toast.LENGTH_LONG).show();

            }
        });
        infoPages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "The number of pages you need to write will assist in the calculation of the estimated duration of the task.", Toast.LENGTH_LONG).show();

            }
        });infoSubTasks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "The number of sub task will assist in the calculation of the estimated duration of the task.", Toast.LENGTH_LONG).show();

            }
        });infoDuration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "This is an estimated duration of your task based on the difficulty, number of pages you are reading, number of sub task expected, and your reading speed.", Toast.LENGTH_LONG).show();

            }
        });


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
                String name = taskName.getText().toString();
                String start = dateStart.getText().toString();
                String end = dateEnd.getText().toString();
                String difficulty = selectedDifficulty.getText().toString();
                String pagesNumber = pages.getText().toString();
                String subNumber = subTask.getText().toString();


                if (TextUtils.isEmpty(name)) {
                    taskName.setError("Enter Task Name");
                    return;
                } else if (TextUtils.isEmpty(start)) {
                    dateStart.setError("Enter Start Date");
                    return;
                } else if (TextUtils.isEmpty(end)) {
                    dateEnd.setError("Enter Deadline");
                    return;
                } else if (TextUtils.isEmpty(difficulty)) {
                    selectedDifficulty.setError("Enter Difficulty");
                    return;
                } else if (TextUtils.isEmpty(pagesNumber)) {
                    pages.setError("Enter number of pages to read.");
                    return;
                } else if (TextUtils.isEmpty(subNumber)) {
                    subTask.setError("Enter number of expected sub task.");
                    return;
                }
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

                                Intent a = new Intent(getContext(), Current.class);
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

                                Intent b = new Intent(getContext(), Future.class);
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
