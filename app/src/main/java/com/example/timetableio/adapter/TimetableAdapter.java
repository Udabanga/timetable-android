package com.example.timetableio.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.timetableio.model.Schedule;
import com.example.timetableio.R;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class TimetableAdapter extends RecyclerView.Adapter<TimetableAdapter.ViewHolder>{
    private Context context;
    private List<Schedule> scheduleList;
    private LayoutInflater layoutInflater;

    SimpleDateFormat dateFormat=new SimpleDateFormat("dd-MM-yyyy");
    SimpleDateFormat timeFormat=new SimpleDateFormat("HH:mm");


    public TimetableAdapter(Context context, List<Schedule> scheduleList) {
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

    public class ViewHolder extends RecyclerView.ViewHolder{

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

        }
    }
}
