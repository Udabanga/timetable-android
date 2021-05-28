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

import com.example.timetableio.R;
import com.example.timetableio.activity.LecturerUserAddEditActivity;
import com.example.timetableio.activity.ModuleAddEditActivity;
import com.example.timetableio.model.User;
import com.squareup.picasso.Picasso;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder>{
    private Context context;
    private List<User> userList;
    private LayoutInflater layoutInflater;



    public UserAdapter(Context context, List<User> userList) {
        this.layoutInflater = LayoutInflater.from(context);
        this.context = context;
        this.userList = userList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= layoutInflater.inflate(R.layout.lecturer_layout, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.id.setText(Long.toString(userList.get(position).getId()));
        holder.email.setText(userList.get(position).getEmail());
        holder.name.setText(userList.get(position).getName());
        holder.roles.setText(userList.get(position).getRoles().toString());
        Picasso.get().load(R.drawable.profile_2).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener, PopupMenu.OnMenuItemClickListener{

        TextView id, email, name, roles;
        ImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.userIDTextView);
            email = itemView.findViewById(R.id.emailTextView);
            name = itemView.findViewById(R.id.nameTextView);
            roles = itemView.findViewById(R.id.rolesTextView);
            image = itemView.findViewById(R.id.lecturerListImageView);

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
            Intent intent = new Intent(context, LecturerUserAddEditActivity.class);
            switch (menuItem.getItemId()){
                case (R.id.action_delete):

                    intent.putExtra("SELECTION", "Delete");
                    intent.putExtra("USER_ID", String.valueOf(userList.get(getAdapterPosition()).getId()));
                    intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);

                    return true;
                case (R.id.action_edit):
                    intent.putExtra("SELECTION", "Edit");
                    intent.putExtra("USER_ID", String.valueOf(userList.get(getAdapterPosition()).getId()));
                    intent.putExtra("USER_EMAIL", String.valueOf(userList.get(getAdapterPosition()).getEmail()));
                    intent.putExtra("USER_NAME", String.valueOf(userList.get(getAdapterPosition()).getName()));
                    intent.putExtra("USER_PASSWORD", String.valueOf(userList.get(getAdapterPosition()).getPassword()));
                    intent.putExtra("USER_ROLES", String.valueOf(userList.get(getAdapterPosition()).getRoles()));
                    intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                    return true;
                default:
                    return false;
            }
        }
    }
}
