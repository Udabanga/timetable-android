package com.example.timetableio.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.timetableio.R;
import com.example.timetableio.adapter.UserAdapter;
import com.example.timetableio.model.Role;
import com.example.timetableio.model.User;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashSet;
import java.util.Set;

import static com.example.timetableio.api.API_BASE_URL.baseURL;

public class LecturerHomeActivity extends AppCompatActivity {
    public static final String SHARED_PREFERENCES = "shared_preferences" ;
    public static final String EMAIL_KEY = "email";
    public static final String ID_KEY = "id";
    public SharedPreferences sharedPreferences;
    public String prefEmail, prefID;

    private Toolbar toolbar;
    public TextView id, email, name;
    public CardView lecturerScheduleCard ,batchLongCardView, lecturerLongCardView, classroomLongCardView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_lecturer);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        id = findViewById(R.id.idDisplay);
        email = findViewById(R.id.emailDisplay);
        name = findViewById(R.id.lecturerNameDisplay);
        lecturerScheduleCard = findViewById(R.id.lecturerScheduleCardView);

        batchLongCardView = (CardView) findViewById(R.id.batchLongCardView);
        lecturerLongCardView = (CardView) findViewById(R.id.lecturerLongCardView);
        classroomLongCardView = (CardView) findViewById(R.id.classroomLongCardView);


        setSupportActionBar(toolbar);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        sharedPreferences = getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE);

        prefID = sharedPreferences.getString(ID_KEY, null);
        loadLecturerDetails();

        lecturerScheduleCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), ScheduleListActivity.class);
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

    private void loadLecturerDetails() {
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, baseURL+ "api/auth/users/"+prefID, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Gson g = new Gson();

                            id.setText(Integer.toString(response.getInt("id")));
                            email.setText(response.getString("email"));
                            name.setText(response.getString("name"));


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Toast.makeText(LecturerHomeActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
        queue.add(jsonObjectRequest);
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
