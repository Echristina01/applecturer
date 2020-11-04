package com.example.applecturer;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.applecturer.adapter.LecturerAdapter;
import com.example.applecturer.model.LecturerModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class LecturerListActivity extends AppCompatActivity {
    public static ArrayList<LecturerModel> lecturerList;

    private RecyclerView rv;
    private RecyclerView.Adapter rvAdapter;
    private RecyclerView.LayoutManager rvLayout;
    private DatabaseReference dblecturer;
    private LecturerListActivity parentclass = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lecturer_list_recycler);
        renderView();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        renderView();
    }

    public void renderView(){

        dblecturer = FirebaseDatabase.getInstance().getReference("lecturers");

        lecturerList = new ArrayList<LecturerModel>();

        dblecturer.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                lecturerList.clear();
                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    LecturerModel lecturerModel = childSnapshot.getValue(LecturerModel.class);
                    lecturerList.add(lecturerModel);
                    System.out.println("ADDING TO LECTURER LIST");
                }
                System.out.println("LECTURER LIST SIZE " + lecturerList.size());

                rv = (RecyclerView) findViewById(R.id.recyclerView);
                rv.setHasFixedSize(true);
                rvLayout = new LinearLayoutManager(parentclass);
                rvAdapter = new LecturerAdapter(lecturerList, parentclass);

                rv.setLayoutManager(rvLayout);
                rv.setAdapter(rvAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
