package com.arrsdesign.oclock;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class CurrentTask extends FirestoreRecyclerAdapter<TasCard, CurrentTask.CurrentHolder> {

    public CurrentTask(@NonNull FirestoreRecyclerOptions<TasCard> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull CurrentHolder holder, int position, @NonNull TasCard model) {
        holder.textViewTitle.setText(model.getTitle());
        holder.textViewStart.setText(model.getStart());
        holder.textViewEnd.setText(model.getDeadline());
        holder.textViewDiff.setText(String.valueOf(model.getDifficulty()));
        holder.textViewPages.setText(String.valueOf(model.getPages()));
        holder.textViewSub.setText(String.valueOf(model.getSubTask()));
        holder.textViewMin.setText(String.valueOf(model.getMin()));
        holder.textViewHrs.setText(String.valueOf(model.getHrs()));
        holder.textViewDays.setText(String.valueOf(model.getDays()));
    }

    @NonNull
    @Override
    public CurrentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task, parent, false);
        return new CurrentHolder(v);
    }

    class CurrentHolder extends RecyclerView.ViewHolder {
        TextView textViewTitle;
        TextView textViewStart;
        TextView textViewEnd;
        //TextView textViewDaysLeft;
        TextView textViewDiff;
        EditText textViewPages;
        EditText textViewSub;
        TextView textViewMin;
        TextView textViewHrs;
        TextView textViewDays;

        public CurrentHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.taskNameR);
            textViewStart = itemView.findViewById(R.id.editTextDateStart);
            textViewEnd = itemView.findViewById(R.id.editTextDateEnd);
            //textViewDaysLeft = itemView.findViewById(R.id.);
            textViewDiff = itemView.findViewById(R.id.selectedDifficultyR);
            textViewPages = itemView.findViewById(R.id.numberOfPagesR);
            textViewSub = itemView.findViewById(R.id.numberOfSubTasksR);
            textViewMin = itemView.findViewById(R.id.timeInMinutes);
            textViewHrs = itemView.findViewById(R.id.timeInHours);
            textViewDays = itemView.findViewById(R.id.timeInDays);

        }
    }
}
