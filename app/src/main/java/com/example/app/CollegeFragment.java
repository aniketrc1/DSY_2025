package com.example.app;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class CollegeFragment extends Fragment {

    private RecyclerView recyclerView;
    private CollegeAdapter adapter;
    private List<College> collegeList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_collage, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        collegeList = new ArrayList<>();
        adapter = new CollegeAdapter(collegeList);
        recyclerView.setAdapter(adapter);

        fetchColleges(); // Fetch data
        return view;
    }

    private void fetchColleges() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("collage");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                collegeList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    College college = dataSnapshot.getValue(College.class);
                    if (college != null) { // Add null check
                        collegeList.add(college);
                    }
                }
                adapter.notifyDataSetChanged(); // Notify RecyclerView
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Firebase", "Database Error: " + error.getMessage());
            }
        });
    }
}
