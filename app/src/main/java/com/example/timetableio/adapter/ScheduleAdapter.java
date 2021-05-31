package com.example.timetableio.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.timetableio.activity.ModuleAddEditActivity;
import com.example.timetableio.model.Schedule;
import com.example.timetableio.R;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ViewHolder>{
    private Context context;
    private List<Schedule> scheduleList;
    private LayoutInflater layoutInflater;

    SimpleDateFormat dateFormat=new SimpleDateFormat("dd-MM-yyyy");
    SimpleDateFormat timeFormat=new SimpleDateFormat("HH:mm");


    public ScheduleAdapter(Context context, List<Schedule> scheduleList) {
        this.layoutInflater = LayoutInflater.from(context);
        this.context = context;
        this.scheduleList = scheduleList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= layoutInflater.inflate(R.layout.schedule_student_batch_layout, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        holder.date.setText(dateFormat.format(scheduleList.get(position).getDate()));
//        holder.startTime.setText(scheduleList.get(position).getStartTime().toString());
        holder.startTime.setText(timeFormat.format(scheduleList.get(position).getEndTime()));
        holder.endTime.setText(timeFormat.format(scheduleList.get(position).getEndTime()));
        holder.classroom.setText(Long.toString(scheduleList.get(position).getClassroom().getId()));
        holder.module.setText(scheduleList.get(position).getModule().getModuleName());
        holder.user.setText(scheduleList.get(position).getUser().getName());
        holder.batches.setText(scheduleList.get(position).getBatches().toString());

        Calendar c = Calendar.getInstance();
        c.setTime(scheduleList.get(position).getDate());
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);

        switch (dayOfWeek) {
            case 1: //Sunday
                Picasso.get().load(R.drawable.sunday).into(holder.dateImage);
                break;
            case 2: //Monday
                Picasso.get().load(R.drawable.monday).into(holder.dateImage);
                break;
            case 3: //Tuesday
                Picasso.get().load(R.drawable.tuesday).into(holder.dateImage);
                break;
            case 4: //Wednesday
                Picasso.get().load(R.drawable.wednesday).into(holder.dateImage);
                break;
            case 5: //Thursday
                Picasso.get().load(R.drawable.thursday).into(holder.dateImage);
                break;
            case 6: //Friday
                Picasso.get().load(R.drawable.friday).into(holder.dateImage);
                break;
            case 7: // Saturday
                Picasso.get().load(R.drawable.saturday).into(holder.dateImage);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return scheduleList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener, PopupMenu.OnMenuItemClickListener {

        TextView date, startTime, endTime, classroom, module, user, batches;
        ImageView dateImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.dateTextView);
            startTime = itemView.findViewById(R.id.startTimeTextView);
            endTime = itemView.findViewById(R.id.endTimeTextView);
            classroom = itemView.findViewById(R.id.classroomTextView);
            module = itemView.findViewById(R.id.batchCodeTextView);
            user = itemView.findViewById(R.id.lecturerTextView);
            batches = itemView.findViewById(R.id.batchListTextView);
            dateImage = itemView.findViewById(R.id.dayImageView);

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
            Intent intent = new Intent(context, ModuleAddEditActivity.class);
            switch (menuItem.getItemId()){
                case (R.id.action_delete):

                    intent.putExtra("SELECTION", "Delete");
                    intent.putExtra("SCHEDULE_ID", String.valueOf(scheduleList.get(getAdapterPosition()).getId()));
                    intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);

                    return true;
                case (R.id.action_edit):
                    intent.putExtra("SELECTION", "Edit");
                    intent.putExtra("SCHEDULE_ID", String.valueOf(scheduleList.get(getAdapterPosition()).getId()));
                    intent.putExtra("SCHEDULE_DATE", String.valueOf(scheduleList.get(getAdapterPosition()).getDate()));
                    intent.putExtra("SCHEDULE_START_TIME", String.valueOf(scheduleList.get(getAdapterPosition()).getStartTime()));
                    intent.putExtra("SCHEDULE_END_TIME", String.valueOf(scheduleList.get(getAdapterPosition()).getEndTime()));
                    intent.putExtra("SCHEDULE_CLASSROOM", String.valueOf(scheduleList.get(getAdapterPosition()).getClassroom().getId()));
                    intent.putExtra("SCHEDULE_MODULE", String.valueOf(scheduleList.get(getAdapterPosition()).getModule().getId()));
                    intent.putExtra("SCHEDULE_USER", String.valueOf(scheduleList.get(getAdapterPosition()).getUser().getId()));
                    intent.putExtra("SCHEDULE_BATCHES", String.valueOf(scheduleList.get(getAdapterPosition()).getBatches()));

                    intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                    return true;
                default:
                    return false;
            }
        }
    }
}
