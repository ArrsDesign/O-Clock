package com.arrsdesign.oclock;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

import static com.arrsdesign.oclock.Register.TAG;

public class EditTask extends AppCompatActivity {
    TextView title;
    ImageView back;
    EditText taskName, pages, subTask;
    TextView selectedDifficulty, dateStart, dateEnd, minute, hour, day, delete;
    SeekBar difficulty;
    DatePickerDialog.OnDateSetListener dateSetListenerStart;
    DatePickerDialog.OnDateSetListener dateSetListenerEnd;
    ImageView info, infoPages, infoSubTasks, infoDuration;
    Button updateTask;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);

        back = findViewById(R.id.backArrow);
        title = findViewById(R.id.toolbarTitle);

        //Back Button
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        //Page Name
        title.setText("EDIT TASK");

        //Task Title
        taskName = findViewById(R.id.taskNameR);

        //Date Selection
        dateStart = findViewById(R.id.editTextDateStart);
        dateEnd = findViewById(R.id.editTextDateEnd);

        //Pages and Sub
        pages = findViewById(R.id.numberOfPagesR);
        subTask = findViewById(R.id.numberOfSubTasksR);

        //Difficulty
        difficulty = findViewById(R.id.difficultySelectionR);
        selectedDifficulty = findViewById(R.id.selectedDifficultyR);

        //Time Calculation
        minute = findViewById(R.id.timeInMinutes);
        hour = findViewById(R.id.timeInHours);
        day = findViewById(R.id.timeInDays);

        //Information Button
        info = findViewById(R.id.info);
        infoPages = findViewById(R.id.infoPages);
        infoSubTasks = findViewById(R.id.infoSubTasks);
        infoDuration = findViewById(R.id.infoDuration);

        //Button
        delete = findViewById(R.id.delete);
        updateTask = findViewById(R.id.updateTask);

        //Values from Task
        taskName.setText(getIntent().getStringExtra("titleTask"));
        dateStart.setText(getIntent().getStringExtra("startDate"));
        dateEnd.setText(getIntent().getStringExtra("endDate"));
        pages.setText(getIntent().getStringExtra("difficultyNumber"));
        subTask.setText(getIntent().getStringExtra("numberPages"));
        selectedDifficulty.setText(getIntent().getStringExtra("numberSub"));
        minute.setText(getIntent().getStringExtra("timeInMinutes"));
        hour.setText(getIntent().getStringExtra("timeInHours"));
        day.setText(getIntent().getStringExtra("timeInDays"));



        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(EditTask.this, "The difficulty number selected will assist in the calculation of the estimated duration of the task.", Toast.LENGTH_LONG).show();

            }
        });
        infoPages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(EditTask.this, "The number of pages you need to read will assist in the calculation of the estimated duration of the task.", Toast.LENGTH_LONG).show();

            }
        });infoSubTasks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(EditTask.this, "The number of sub task will assist in the calculation of the estimated duration of the task.", Toast.LENGTH_LONG).show();

            }
        });infoDuration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(EditTask.this, "This is an estimated duration of your task based on the difficulty, number of pages you are reading, number of sub task expected, and your reading speed.", Toast.LENGTH_LONG).show();

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

                DatePickerDialog dialog = new DatePickerDialog(EditTask.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, dateSetListenerStart, year, month, day);
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

                DatePickerDialog dialog = new DatePickerDialog(EditTask.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, dateSetListenerEnd, year2, month2, day2);
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
    }
}