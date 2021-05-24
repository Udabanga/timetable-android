package com.example.timetableio.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import com.example.timetableio.R;

public class AdminHomeActivity extends AppCompatActivity {
    private Toolbar toolbar_admin;
    private CardView batchesCardView, lecturersCardView, classroomsCardView, modulesCardView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_admin);

        toolbar_admin = (Toolbar) findViewById(R.id.toolbar_admin);
        batchesCardView = (CardView)  findViewById(R.id.batchesCardView);
        lecturersCardView = (CardView)  findViewById(R.id.lecturersCardView);
        classroomsCardView = (CardView)  findViewById(R.id.classroomsCardView);
        modulesCardView = (CardView)  findViewById(R.id.modulesClassView);


        setSupportActionBar(toolbar_admin);
        Toolbar toolbar = findViewById(R.id.toolbar_admin);
        setSupportActionBar(toolbar);

        batchesCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), BatchListActivity.class);
                startActivity(intent);
            }
        });
        lecturersCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), LecturerUserListActivity.class);
                startActivity(intent);
            }
        });
        classroomsCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), ClassroomListActivity.class);
                startActivity(intent);
            }
        });
        modulesCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), ModuleListActivity.class);
                startActivity(intent);
            }
        });
    }
}
