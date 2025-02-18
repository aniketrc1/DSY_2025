package com.example.app;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class CollegeAdapter extends RecyclerView.Adapter<CollegeAdapter.ViewHolder> {

    private List<College> collegeList;

    public CollegeAdapter(List<College> collegeList) {
        this.collegeList = collegeList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_college, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        College college = collegeList.get(position);
        holder.tvDteCode.setText(college.getDte_code());
        holder.tvCollegeName.setText(college.getCollege_name());
    }

    @Override
    public int getItemCount() {
        return collegeList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvDteCode, tvCollegeName;

        public ViewHolder(View itemView) {
            super(itemView);
            tvDteCode = itemView.findViewById(R.id.tvDteCode);
            tvCollegeName = itemView.findViewById(R.id.tvCollegeName);
        }
    }
}

