package com.example.timetableio.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.timetableio.model.Classroom;
import com.example.timetableio.R;

import java.util.List;

public class ClassroomAdapter extends RecyclerView.Adapter<ClassroomAdapter.ViewHolder>{
    private Context context;
    private List<Classroom> classroomList;
    private LayoutInflater layoutInflater;



    public ClassroomAdapter(Context context, List<Classroom> classroomList) {
        this.layoutInflater = LayoutInflater.from(context);
        this.context = context;
        this.classroomList = classroomList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= layoutInflater.inflate(R.layout.classroom_layout, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


//        holder.date.setText(dateFormat.format(classroomList.get(position).getDate()));
        holder.id.setText(Long.toString(classroomList.get(position).getId()));
        holder.building.setText(classroomList.get(position).getBuilding());
        holder.floor.setText(Integer.toString(classroomList.get(position).getFloor()));
        holder.roomNumber.setText(Integer.toString(classroomList.get(position).getRoomNumber()));
        holder.type.setText(classroomList.get(position).getType());


    }

    @Override
    public int getItemCount() {
        return classroomList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView id, building, floor, roomNumber, type;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.moduleIDTextView);
            building = itemView.findViewById(R.id.moduleTextView);
            floor = itemView.findViewById(R.id.floorTextView);
            roomNumber = itemView.findViewById(R.id.roomNumberTextView);
            type = itemView.findViewById(R.id.typeTextView);
        }
    }
}
