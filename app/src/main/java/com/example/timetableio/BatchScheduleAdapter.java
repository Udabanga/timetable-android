package com.example.timetableio;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class BatchScheduleAdapter extends RecyclerView.Adapter<BatchScheduleAdapter.ViewHolder>{

    String data1[], data2[];
    Context context;

    public BatchScheduleAdapter(Context ct, String module[], String lecturer[]){
        context = ct;
        data1= module;
        data2= lecturer;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.schedule_student_batch_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.moduleTextView.setText(data1[position]);
        holder.lecturerTextView.setText(data2[position]);
    }

    @Override
    public int getItemCount() {
        return data1.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView moduleTextView, lecturerTextView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            moduleTextView= itemView.findViewById(R.id.moduleTextView);
            lecturerTextView= itemView.findViewById(R.id.LecturerTextView);
        }
    }
}
