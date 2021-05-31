package com.example.timetableio.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.timetableio.R;

public class MainActivity extends AppCompatActivity {
    private TextView loginText;
    private Button viewBatchTimetableButton, viewLecturerTimetableButton;
    private Spinner facultySpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginText = (TextView) findViewById(R.id.loginTextView);
        viewBatchTimetableButton = (Button) findViewById(R.id.viewBatchTimetableButton);
        viewLecturerTimetableButton = (Button) findViewById(R.id.viewLectuerTimetableButton);

        loginText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Login", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getBaseContext(), LoginActivity.class);
                startActivity(intent);
            }
        });

        viewBatchTimetableButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "View Batch Timetable", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getBaseContext(), ViewBatchTimetableActivity.class);

                startActivity(intent);
            }
        });

        viewLecturerTimetableButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), ViewLecturerTimetableActivity.class);

                startActivity(intent);
            }
        });
    }


}
