package com.arrsdesign.oclock.modelSubTask;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.arrsdesign.oclock.R;
import com.arrsdesign.oclock.Register;
import com.arrsdesign.oclock.Task2;
import com.arrsdesign.oclock.TaskInput;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import static android.widget.Toast.LENGTH_LONG;


public class AdapterC extends RecyclerView.Adapter<AdapterC.MyViewHolder> {

    Context context;
    ArrayList<TaskInput> taskInputs;
    FirebaseDatabase database;
    DatabaseReference reference;





    public AdapterC(Context c, ArrayList<TaskInput> p) {
        context = c;
        taskInputs = p;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_task, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.titleTask.setText(taskInputs.get(position).getTitleTask());
        holder.startDate.setText(taskInputs.get(position).getStartDate());
        holder.endDate.setText(taskInputs.get(position).getEndDate());

        final String getTitleTask = taskInputs.get(position).getTitleTask();
        final String getStartDate = taskInputs.get(position).getStartDate();
        final String getEndDate = taskInputs.get(position).getEndDate();

        final String key = taskInputs.get(position).getKey();

        

    }

    @Override
    public int getItemCount() {
        return taskInputs.size();
    }
    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView titleTask, startDate, endDate, key, difficultyNumber, numberPages, numberSub, timeInMinutes, timeInHours, timeInDays;
        LinearLayout linearLayout;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTask = itemView.findViewById(R.id.titleTask);
            startDate = itemView.findViewById(R.id.startDate);
            endDate = itemView.findViewById(R.id.endDate);
            difficultyNumber = itemView.findViewById(R.id.difficultyNumber);
            numberPages = itemView.findViewById(R.id.numberPages);
            numberSub = itemView.findViewById(R.id.numberSub);
            timeInMinutes = itemView.findViewById(R.id.timeInMinutes);
            timeInHours = itemView.findViewById(R.id.timeInHours);
            timeInDays = itemView.findViewById(R.id.timeInDays);


        }

    }

}
