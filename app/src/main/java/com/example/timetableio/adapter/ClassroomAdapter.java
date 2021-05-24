package com.example.timetableio.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.timetableio.activity.ClassroomAddEditActivity;
import com.example.timetableio.model.Classroom;
import com.example.timetableio.R;

import java.util.List;

import static android.content.ContentValues.TAG;

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

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener, PopupMenu.OnMenuItemClickListener {

        TextView id, building, floor, roomNumber, type;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.semesterTextView);
            building = itemView.findViewById(R.id.batchCodeTextView);
            floor = itemView.findViewById(R.id.yearTextView);
            roomNumber = itemView.findViewById(R.id.roomNumberTextView);
            type = itemView.findViewById(R.id.typeTextView);

            itemView.setOnLongClickListener(this);
        }

        @Override
        public boolean onLongClick(View view) {
            showPopupMenu(view);
            return false;
        }

        public void showPopupMenu(View view){
            PopupMenu popupMenu = new PopupMenu(view.getContext(), view);
            popupMenu.inflate(R.menu.popup_menu);
            popupMenu.setOnMenuItemClickListener(this);
            popupMenu.show();
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            Intent intent = new Intent(context, ClassroomAddEditActivity.class);
            switch (menuItem.getItemId()){
                case (R.id.action_delete):
                    intent.putExtra("SELECTION", "Delete");
                    intent.putExtra("CLASSROOM_ID", String.valueOf(classroomList.get(getAdapterPosition()).getId()));
                    intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);

                    return true;
                case (R.id.action_edit):
                    Log.d(TAG, "onMenuItemClick: action_edit"+ getAdapterPosition());
                    intent.putExtra("SELECTION", "Edit");
                    intent.putExtra("CLASSROOM_ID", String.valueOf(classroomList.get(getAdapterPosition()).getId()));
                    intent.putExtra("CLASSROOM_BUILDING", String.valueOf(classroomList.get(getAdapterPosition()).getBuilding()));
                    intent.putExtra("CLASSROOM_FLOOR", String.valueOf(classroomList.get(getAdapterPosition()).getFloor()));
                    intent.putExtra("CLASSROOM_ROOM_NUMBER", String.valueOf(classroomList.get(getAdapterPosition()).getRoomNumber()));
                    intent.putExtra("CLASSROOM_TYPE", String.valueOf(classroomList.get(getAdapterPosition()).getType()));
                    intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                    return true;
                default:
                    return false;
            }
        }
    }
}

