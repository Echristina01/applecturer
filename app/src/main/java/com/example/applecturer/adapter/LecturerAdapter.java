package com.example.applecturer.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.applecturer.LecturerDetailActivity;
import com.example.applecturer.R;
import com.example.applecturer.model.LecturerModel;

import java.util.ArrayList;

public class LecturerAdapter extends RecyclerView.Adapter<LecturerAdapter.LecturerViewHolder> {
    private ArrayList<LecturerModel> lecturerList;
    private AppCompatActivity parentActivity;

    //    Inner Class
    public static class LecturerViewHolder extends RecyclerView.ViewHolder {
        public TextView textName, textExpertise, textGender;
        public String uid;

        public LecturerViewHolder(@NonNull View itemView) {
            super(itemView);
            textName = itemView.findViewById(R.id.textName);
            textExpertise = itemView.findViewById(R.id.textExpertise);
            textGender = itemView.findViewById(R.id.textGender);
        }
    }

    public LecturerAdapter(ArrayList<LecturerModel> lecturerList, AppCompatActivity parentActivity) {
        this.lecturerList = lecturerList;
        this.parentActivity = parentActivity;
    }

    @NonNull
    @Override
    public LecturerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.lecturer_list_card, parent, false);
        final LecturerViewHolder lecturerViewHolder = new LecturerViewHolder(v);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(parentActivity, LecturerDetailActivity.class);
                intent.putExtra("uid", lecturerViewHolder.uid);
                System.out.println("EDITING " + lecturerViewHolder.uid);
                parentActivity.startActivity(intent);
            }
        });

        return lecturerViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull LecturerViewHolder holder, int position) {
        LecturerModel currentLecturer = this.lecturerList.get(position);
        holder.textName.setText(currentLecturer.getName());
        holder.textExpertise.setText(currentLecturer.getExpertise());
        holder.textGender.setText(currentLecturer.getGender());
        holder.uid = currentLecturer.getId();
    }

    @Override
    public int getItemCount() {
        return this.lecturerList.size();
    }

}
