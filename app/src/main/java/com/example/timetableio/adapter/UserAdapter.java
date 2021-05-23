package com.example.timetableio.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.timetableio.R;
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

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView id, email, name, roles;
        ImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.userIDTextView);
            email = itemView.findViewById(R.id.emailTextView);
            name = itemView.findViewById(R.id.nameTextView);
            roles = itemView.findViewById(R.id.rolesTextView);
            image = itemView.findViewById(R.id.lecturerListImageView);
        }
    }
}
