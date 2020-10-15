package com.arrsdesign.oclock.modelSubTask;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.arrsdesign.oclock.R;
import com.arrsdesign.oclock.TaskInput;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.google.firebase.database.FirebaseDatabase.getInstance;


public class AdapterF extends RecyclerView.Adapter<AdapterF.MyViewHolder> {

    Context context;
    ArrayList<TaskInput> taskInputs;
    DatabaseReference reference;

    public AdapterF(Context c, ArrayList<TaskInput> p) {
        context = c;
        taskInputs = p;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_task, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
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

        TaskInput input = taskInputs.get(position);
        boolean isExpanded = taskInputs.get(position).isExpanded();
        holder.expandableLayout.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
    }

    @Override
    public int getItemCount() {
        return taskInputs.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView text_progress, titleTask, startDate, endDate, key, difficultyNumber, numberPages, numberSub, timeInMinutes, timeInHours, timeInDays;
        RelativeLayout expandableLayout;
        ImageView delete;
        ProgressBar progress_circular;



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

            expandableLayout = itemView.findViewById(R.id.expandableLayout);

            //Progress Tracking
            text_progress = itemView.findViewById(R.id.text_progress);
            progress_circular = itemView.findViewById(R.id.progress_circular);
            final float value = progress_circular.getProgress();
            final CheckBox checkbox = itemView.findViewById(R.id.checkbox);
            final CheckBox checkbox1 = itemView.findViewById(R.id.checkbox1);
            final CheckBox checkbox2 = itemView.findViewById(R.id.checkbox2);
            final CheckBox checkbox3 = itemView.findViewById(R.id.checkbox3);
            final CheckBox checkbox4 = itemView.findViewById(R.id.checkbox4);

            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
            final SharedPreferences.Editor editor = preferences.edit();
            if (preferences.contains("checkbox") && preferences.getBoolean("checkbox", false)) {
                checkbox.setChecked(true);
            }else {
                checkbox.setChecked(false);
            }
            checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (checkbox.isChecked()){
                        editor.putBoolean("checkbox", true);
                        progress_circular.setProgress(progress_circular.getProgress()+20);
                        text_progress.setText(String.valueOf(progress_circular.getProgress()) + "%");

                    } else {
                        editor.putBoolean("checkbox", false);
                        progress_circular.setProgress(progress_circular.getProgress()-20);
                        text_progress.setText(String.valueOf(progress_circular.getProgress()) + "%");

                    }
                    editor.apply();
                }
            });

            if (preferences.contains("checkbox1") && preferences.getBoolean("checkbox1", false)) {
                checkbox1.setChecked(true);
            }else {
                checkbox1.setChecked(false);
            }
            checkbox1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (checkbox1.isChecked()){
                        editor.putBoolean("checkbox1", true);
                        progress_circular.setProgress(progress_circular.getProgress()+20);
                        text_progress.setText(String.valueOf(progress_circular.getProgress()) + "%");

                    } else {
                        editor.putBoolean("checkbox1", false);
                        progress_circular.setProgress(progress_circular.getProgress()-20);
                        text_progress.setText(String.valueOf(progress_circular.getProgress()) + "%");

                    }
                    editor.apply();
                }
            });

            if (preferences.contains("checkbox2") && preferences.getBoolean("checkbox2", false)) {
                checkbox1.setChecked(true);
            }else {
                checkbox1.setChecked(false);
            }
            checkbox2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (checkbox2.isChecked()){
                        editor.putBoolean("checkbox2", true);
                        progress_circular.setProgress(progress_circular.getProgress()+20);
                        text_progress.setText(String.valueOf(progress_circular.getProgress()) + "%");

                    } else {
                        editor.putBoolean("checkbox2", false);
                        progress_circular.setProgress(progress_circular.getProgress()-20);
                        text_progress.setText(String.valueOf(progress_circular.getProgress()) + "%");

                    }
                    editor.apply();
                }
            });

            if (preferences.contains("checkbox3") && preferences.getBoolean("checkbox3", false)) {
                checkbox1.setChecked(true);
            }else {
                checkbox1.setChecked(false);
            }
            checkbox3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (checkbox3.isChecked()){
                        editor.putBoolean("checkbox3", true);
                        progress_circular.setProgress(progress_circular.getProgress()+20);
                        text_progress.setText(String.valueOf(progress_circular.getProgress()) + "%");

                    } else {
                        editor.putBoolean("checkbox3", false);
                        progress_circular.setProgress(progress_circular.getProgress()-20);
                        text_progress.setText(String.valueOf(progress_circular.getProgress()) + "%");

                    }
                    editor.apply();
                }
            });

            if (preferences.contains("checkbox4") && preferences.getBoolean("checkbox4", false)) {
                checkbox1.setChecked(true);
            }else {
                checkbox1.setChecked(false);
            }
            checkbox4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (checkbox4.isChecked()){
                        editor.putBoolean("checkbox4", true);
                        progress_circular.setProgress(progress_circular.getProgress()+20);
                        text_progress.setText(String.valueOf(progress_circular.getProgress()) + "%");

                    } else {
                        editor.putBoolean("checkbox4", false);
                        progress_circular.setProgress(progress_circular.getProgress()-20);
                        text_progress.setText(String.valueOf(progress_circular.getProgress()) + "%");

                    }
                    editor.apply();
                }
            });

            if (progress_circular.getProgress() == 100){

            }

            titleTask.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TaskInput input = taskInputs.get(getAdapterPosition());
                    input.setExpanded(!input.isExpanded());
                    notifyItemChanged(getAdapterPosition());
                }
            });

        }

    }

}
