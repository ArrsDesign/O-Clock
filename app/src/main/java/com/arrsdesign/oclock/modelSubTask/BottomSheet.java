package com.arrsdesign.oclock.modelSubTask;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.arrsdesign.oclock.R;
import com.arrsdesign.oclock.Task2;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

public class BottomSheet extends BottomSheetDialogFragment {
    DatabaseReference reference;
    Integer number = new Random().nextInt();
    String key = Integer.toString(number);

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.new_task, container, false);

        Button newSubTaskButton = v.findViewById(R.id.newSubTaskButton);
        final EditText newTaskText = v.findViewById(R.id.newTaskText);
        reference = FirebaseDatabase.getInstance().getReference().child("Future Task").child("Sub Task " + number);
        newSubTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String task = newTaskText.getText().toString();


                        if (TextUtils.isEmpty(task)) {
                            newTaskText.setError("Enter Sub Task Name");
                            return;}


                        snapshot.getRef().child("subTask").setValue(newTaskText.getText().toString());

                        Intent a = new Intent(getContext(), Task_Display.class);
                        startActivity(a);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });
        return v;
    }
}
