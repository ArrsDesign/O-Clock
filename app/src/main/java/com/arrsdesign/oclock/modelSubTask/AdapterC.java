package com.arrsdesign.oclock.modelSubTask;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static android.widget.Toast.LENGTH_LONG;


public class AdapterC extends RecyclerView.Adapter<AdapterC.MyViewHolder> {

    Context context;
    ArrayList<TaskInput> taskInputs;
    FirebaseDatabase database;
    DatabaseReference reference;
    Integer number = new Random().nextInt();






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
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        holder.titleTask.setText(taskInputs.get(position).getTitleTask());
        holder.startDate.setText(taskInputs.get(position).getStartDate());
        holder.endDate.setText(taskInputs.get(position).getEndDate());

        final String getTitleTask = taskInputs.get(position).getTitleTask();
        final String getStartDate = taskInputs.get(position).getStartDate();
        final String getEndDate = taskInputs.get(position).getEndDate();

        final String key = taskInputs.get(position).getKey();

            holder.edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final DialogPlus dialogPlus = DialogPlus.newDialog(holder.titleTask.getContext())
                            .setContentHolder(new ViewHolder(R.layout.edit)).setExpanded(true, 1550)
                            .create();

                    View myView = dialogPlus.getHolderView();

                    final EditText titleTask = myView.findViewById(R.id.taskEdit);
                    final EditText startDate = myView.findViewById(R.id.startEdit);
                    final EditText endDate = myView.findViewById(R.id.endEdit);
                    final EditText difficultyNumber = myView.findViewById(R.id.difficultyEdit);
                    final EditText numberPages = myView.findViewById(R.id.pagesEdit);
                    final EditText numberSub = myView.findViewById(R.id.subTaskEdit);
                    Button save = myView.findViewById(R.id.saveEdit);


                    titleTask.setText(taskInputs.get(position).getTitleTask());
                    startDate.setText(taskInputs.get(position).getStartDate());
                    endDate.setText(taskInputs.get(position).getEndDate());
                    difficultyNumber.setText(taskInputs.get(position).getDifficultyNumber());
                    numberPages.setText(taskInputs.get(position).getNumberPages());
                    numberSub.setText(taskInputs.get(position).getNumberSub());

                    dialogPlus.show();

                        save.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Map<String, Object> map = new HashMap<>();
                                map.put("titleTask", titleTask.getText().toString());
                                map.put("startDate", startDate.getText().toString());
                                map.put("endDate", endDate.getText().toString());
                                map.put("difficultyNumber", difficultyNumber.getText().toString());
                                map.put("numberPages", numberPages.getText().toString());
                                map.put("numberSub", numberSub.getText().toString());

                                FirebaseDatabase.getInstance().getReference().child("OClock")
                                        .updateChildren(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        dialogPlus.dismiss();

                                    }
                                })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                dialogPlus.dismiss();

                                            }
                                        });


                            }
                        });




                }
            });


    }

    @Override
    public int getItemCount() {
        return taskInputs.size();
    }
    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView titleTask, startDate, endDate, key, difficultyNumber, numberPages, numberSub, timeInMinutes, timeInHours, timeInDays;
        LinearLayout linearLayout;
        ImageView edit, delete;


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

            edit = itemView.findViewById(R.id.edit);
            delete = itemView.findViewById(R.id.delete);




        }

    }

}
