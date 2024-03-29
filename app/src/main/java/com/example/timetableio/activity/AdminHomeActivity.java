package com.example.timetableio.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import com.example.timetableio.R;

public class AdminHomeActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private CardView batchesCardView, lecturersCardView, classroomsCardView, modulesCardView, batchLongCardView, lecturerLongCardView, classroomLongCardView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_admin);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        batchesCardView = (CardView)  findViewById(R.id.batchesCardView);
        lecturersCardView = (CardView)  findViewById(R.id.lecturerScheduleCardView);
        classroomsCardView = (CardView)  findViewById(R.id.classroomsCardView);
        modulesCardView = (CardView)  findViewById(R.id.modulesClassView);

        batchLongCardView = (CardView) findViewById(R.id.batchLongCardView);
        lecturerLongCardView = (CardView) findViewById(R.id.lecturerLongCardView);
        classroomLongCardView = (CardView) findViewById(R.id.classroomLongCardView);


        setSupportActionBar(toolbar);
        Toolbar toolbar = findViewById(R.id.toolbar);
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

        batchLongCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), ViewBatchTimetableActivity.class);
                startActivity(intent);
            }
        });

        lecturerLongCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), ViewLecturerTimetableActivity.class);
                startActivity(intent);
            }
        });

        classroomLongCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), ViewClassroomTimetableActivity.class);
                startActivity(intent);
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.logout){
            Toast.makeText(getApplicationContext(), "Logged Out", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(getBaseContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }
        return true;
    }
}
