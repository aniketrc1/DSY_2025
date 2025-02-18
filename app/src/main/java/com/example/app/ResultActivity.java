package com.example.app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class ResultActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayAdapter<String> adapter;
    private List<String> eligibleColleges;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        listView = findViewById(R.id.list_view_colleges);
        eligibleColleges = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, eligibleColleges);
        listView.setAdapter(adapter);

        // Get user input from intent
        Intent intent = getIntent();
        String selectedCourse = intent.getStringExtra("course");
        String selectedPlace = intent.getStringExtra("place");
        String selectedCaste = intent.getStringExtra("caste");
        float userMarks = intent.getFloatExtra("marks", 0);

        // Fetch college data from Firebase
        fetchCollegesFromFirebase(selectedCourse, selectedPlace, selectedCaste, userMarks);
    }

    private void fetchCollegesFromFirebase(String course, String place, String caste, float userMarks) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("cutoff");

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                eligibleColleges.clear();

                // Map spinner course names to database branch names
                Map<String, String> branchMap = new HashMap<>();
                branchMap.put("Computer Engineering", "CS");
                branchMap.put("AI Engineering", "AI");
                branchMap.put("Mechanical Engineering", "ME");
                branchMap.put("Civil Engineering", "CE");
                branchMap.put("IT Engineering", "IT");
                branchMap.put("Electronics Engineering", "ENTC");

                String dbBranch = branchMap.getOrDefault(course, course);

                for (DataSnapshot collegeSnapshot : snapshot.getChildren()) {
                    String branch = collegeSnapshot.child("BRANCH").getValue(String.class);
                    String collegeName = collegeSnapshot.child("NAME OF COLLEGE").getValue(String.class);
                    String collegePlace = collegeSnapshot.child("PLACE").getValue(String.class);

                    // Ensure data exists
                    if (branch == null || collegeName == null || collegePlace == null) continue;

                    // Trim spaces and compare
                    if (!branch.equalsIgnoreCase(dbBranch.trim()) || !collegePlace.trim().equalsIgnoreCase(place.trim())) {
                        continue;
                    }

                    // Get cutoff and handle null
                    Float cutoff = collegeSnapshot.child(caste).getValue(Float.class);
                    if (cutoff == null) {
                        Log.e("FirebaseData", "Cutoff value is null for: " + collegeName);
                        continue;
                    }

                    float projectedCutoff = cutoff + 2;
                    if (userMarks >= projectedCutoff) {
                        eligibleColleges.add(collegeName);
                    }
                }

                // If no colleges found, display message
                if (eligibleColleges.isEmpty()) {
                    eligibleColleges.add("No college found");
                }

                // Refresh ListView
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ResultActivity.this, "Failed to fetch data!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
