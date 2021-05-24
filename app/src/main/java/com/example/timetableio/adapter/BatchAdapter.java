package com.example.timetableio.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.timetableio.activity.BatchAddEditActivity;
import com.example.timetableio.model.Batch;
import com.example.timetableio.R;

import java.util.List;

public class BatchAdapter extends RecyclerView.Adapter<BatchAdapter.ViewHolder> {
    private Context context;
    private List<Batch> batchList;
    private LayoutInflater layoutInflater;


    public BatchAdapter(Context context, List<Batch> batchList) {
        this.layoutInflater = LayoutInflater.from(context);
        this.context = context;
        this.batchList = batchList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.batch_layout, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.batchCode.setText(batchList.get(position).getBatchCode());
        holder.faculty.setText(batchList.get(position).getFaculty());
        holder.semester.setText(batchList.get(position).getSemester());
        holder.year.setText(Integer.toString(batchList.get(position).getYear()));

    }

    @Override
    public int getItemCount() {
        return batchList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener, PopupMenu.OnMenuItemClickListener {

        TextView batchCode, faculty, semester, year;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            batchCode = itemView.findViewById(R.id.batchCodeTextView);
            faculty = itemView.findViewById(R.id.batchFacultyTextView);
            semester = itemView.findViewById(R.id.semesterTextView);
            year = itemView.findViewById(R.id.yearTextView);

            itemView.setOnLongClickListener(this);
        }

        @Override
        public boolean onLongClick(View view) {
            showPopupMenu(view);
            return false;
        }

        public void showPopupMenu(View view) {
            PopupMenu popupMenu = new PopupMenu(view.getContext(), view);
            popupMenu.inflate(R.menu.popup_menu);
            popupMenu.setOnMenuItemClickListener(this);
            popupMenu.show();
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            Intent intent = new Intent(context, BatchAddEditActivity.class);
            switch (menuItem.getItemId()) {
                case (R.id.action_delete):

                    intent.putExtra("SELECTION", "Delete");
                    intent.putExtra("BATCH_ID", String.valueOf(batchList.get(getAdapterPosition()).getId()));
                    intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);

                    return true;
                case (R.id.action_edit):
                    intent.putExtra("SELECTION", "Edit");
                    intent.putExtra("BATCH_ID", String.valueOf(batchList.get(getAdapterPosition()).getId()));
                    intent.putExtra("BATCH_BATCH_CODE", String.valueOf(batchList.get(getAdapterPosition()).getBatchCode()));
                    intent.putExtra("BATCH_FACULTY", String.valueOf(batchList.get(getAdapterPosition()).getFaculty()));
                    intent.putExtra("BATCH_SEMESTER", String.valueOf(batchList.get(getAdapterPosition()).getSemester()));
                    intent.putExtra("BATCH_YEAR", String.valueOf(batchList.get(getAdapterPosition()).getYear()));
                    intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                    return true;
                default:
                    return false;
            }
        }
    }
}
