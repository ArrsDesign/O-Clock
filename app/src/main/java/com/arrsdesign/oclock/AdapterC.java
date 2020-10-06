package com.arrsdesign.oclock;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterC extends RecyclerView.Adapter<AdapterC.MyViewHolder> {

    Context context;
    ArrayList<TaskInput> taskInputs;

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
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.titleTask.setText(taskInputs.get(position).getTitleTask());
        holder.startDate.setText(taskInputs.get(position).getStartDate());
        holder.endDate.setText(taskInputs.get(position).getEndDate());
        holder.difficultyNumber.setText(taskInputs.get(position).getDifficultyNumber());
        holder.numberPages.setText(taskInputs.get(position).getNumberPages());
        holder.numberSub.setText(taskInputs.get(position).getNumberSub());
        holder.timeInMinutes.setText(taskInputs.get(position).getTimeInMinutes());
        holder.timeInHours.setText(taskInputs.get(position).getTimeInHours());
        holder.timeInDays.setText(taskInputs.get(position).getTimeInDays());


        final String getTitleTask = taskInputs.get(position).getTitleTask();
        final String getStartDate = taskInputs.get(position).getStartDate();
        final String getEndDate = taskInputs.get(position).getEndDate();
        final String difficultyNumber = taskInputs.get(position).getDifficultyNumber();
        final String numberPages = taskInputs.get(position).getNumberPages();
        final String numberSub = taskInputs.get(position).getNumberSub();
        final String timeInMinutes = taskInputs.get(position).getTimeInMinutes();
        final String timeInHours = taskInputs.get(position).getTimeInHours();
        final String timeInDays = taskInputs.get(position).getTimeInDays();

        final String key = taskInputs.get(position).getKey();




    }

    @Override
    public int getItemCount() {
        return taskInputs.size();
    }
    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView titleTask, startDate, endDate, key, difficultyNumber, numberPages, numberSub, timeInMinutes, timeInHours, timeInDays;

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
