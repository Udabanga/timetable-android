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

import com.example.timetableio.activity.ModuleAddEditActivity;
import com.example.timetableio.model.Module;
import com.example.timetableio.R;

import java.util.List;

public class ModuleAdapter extends RecyclerView.Adapter<ModuleAdapter.ViewHolder> {
    private Context context;
    private List<Module> moduleList;
    private LayoutInflater layoutInflater;



    public ModuleAdapter(Context context, List<Module> moduleList) {
        this.layoutInflater = LayoutInflater.from(context);
        this.context = context;
        this.moduleList = moduleList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= layoutInflater.inflate(R.layout.module_layout, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.id.setText(Long.toString(moduleList.get(position).getId()));
        holder.module.setText(moduleList.get(position).getModuleName());

    }

    @Override
    public int getItemCount() {
        return moduleList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener, PopupMenu.OnMenuItemClickListener {

        TextView id, module;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.semesterTextView);
            module = itemView.findViewById(R.id.batchCodeTextView);

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
                    intent.putExtra("MODULE_ID", String.valueOf(moduleList.get(getAdapterPosition()).getId()));
                    intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);

                    return true;
                case (R.id.action_edit):
                    intent.putExtra("SELECTION", "Edit");
                    intent.putExtra("MODULE_ID", String.valueOf(moduleList.get(getAdapterPosition()).getId()));
                    intent.putExtra("MODULE_NAME", String.valueOf(moduleList.get(getAdapterPosition()).getModuleName()));
                    intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                    return true;
                default:
                    return false;
            }
        }
    }




}
