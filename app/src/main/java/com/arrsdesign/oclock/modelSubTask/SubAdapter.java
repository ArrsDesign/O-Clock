package com.arrsdesign.oclock.modelSubTask;

<<<<<<< HEAD
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.arrsdesign.oclock.SubTaskModel;
=======
import androidx.recyclerview.widget.RecyclerView;

>>>>>>> parent of fa74ef9... Setting up Nested Recycler
import com.arrsdesign.oclock.Task2_Fragments.Current;

import java.util.List;

<<<<<<< HEAD
public class SubAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<SubTaskModel> subTaskList;
    private Current activity;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder {
    }
=======
public class SubAdapter extends RecyclerView.Adapter<SubAdapter.ViewHolder> {

    private List<SubTaskModel> subTaskList;
    private Current activity;
>>>>>>> parent of fa74ef9... Setting up Nested Recycler
}
