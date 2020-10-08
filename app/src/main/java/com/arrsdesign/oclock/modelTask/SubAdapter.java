package com.arrsdesign.oclock.modelTask;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.recyclerview.widget.RecyclerView;

import com.arrsdesign.oclock.R;

import java.util.ArrayList;
import java.util.List;

public class SubAdapter extends RecyclerView.Adapter<SubAdapter.ViewHolder> {

    private List<SubTaskModel> subTaskList;

    public SubAdapter(AdapterC subTaskList){
        this.subTaskList = subTaskList;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.subtask_layout, parent, false);
        return new ViewHolder(itemView);
    }

    public void onBindViewHolder(ViewHolder holder, int position){
        SubTaskModel item = subTaskList.get(position);
        holder.task.setText(item.getTask());
        holder.task.setChecked(toBoolean(item.getStatus()));

    }

    public int getItemCount(){
        return subTaskList.size();
    }

    private boolean toBoolean(int n){
        return n!=0;
    }

    public void setSubTaskList(List<SubTaskModel> list){
        this.subTaskList = list;
        notifyDataSetChanged();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder{
        CheckBox task;

        ViewHolder(View view){
            super(view);
            task = view.findViewById(R.id.checkbox);
        }
    }
}
