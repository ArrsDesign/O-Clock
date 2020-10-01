package com.arrsdesign.oclock;

import android.app.DatePickerDialog;
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

public class Reading extends Fragment {
    private Button createTask;
    private EditText taskName, pages, subTask;
    private TextView selectedDifficulty, dateStart, dateEnd, reading;
    private SeekBar difficulty;
    private DatePickerDialog.OnDateSetListener dateSetListenerStart;
    private DatePickerDialog.OnDateSetListener dateSetListenerEnd;
    private Integer number = new Random().nextInt();
    private FirebaseAuth mAuth;
    private FirebaseFirestore mStore;
    String userID;
    private Object time;


    public Reading() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reading, container, false);

        createTask = view.findViewById(R.id.createTaskBtnR);
        taskName = view.findViewById(R.id.taskNameR);
        dateStart = view.findViewById(R.id.editTextDateStart);
        dateEnd = view.findViewById(R.id.editTextDateEnd);
        pages = view.findViewById(R.id.numberOfPagesR);
        subTask = view.findViewById(R.id.numberOfSubTasksR);
        difficulty = view.findViewById(R.id.difficultySelectionR);
        selectedDifficulty = view.findViewById(R.id.selectedDifficultyR);
        mAuth = FirebaseAuth.getInstance();
        mStore = FirebaseFirestore.getInstance();
        reading = view.findViewById(R.id.reading);

        //Deadline Selection
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

        return view;
    }
}
