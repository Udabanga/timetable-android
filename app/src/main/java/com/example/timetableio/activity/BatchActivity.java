package com.example.timetableio.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pair;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.timetableio.R;
import com.example.timetableio.adapter.BatchTimetableAdapter;
import com.example.timetableio.model.Batch;
import com.example.timetableio.model.Classroom;
import com.example.timetableio.model.Module;
import com.example.timetableio.model.Schedule;
import com.example.timetableio.model.User;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;

public class BatchActivity extends AppCompatActivity {
    List<Schedule> scheduleList;
    RecyclerView recyclerView;
    BatchTimetableAdapter adapter;
    private Button datePickerButton;
    private TextView selectDate;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_batch_list);
        recyclerView = findViewById(R.id.batchRecyclerView);
        scheduleList = new ArrayList<>();
        requestSchedules();
    }

    private void requestSchedules(){
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonArrayRequest JSONArrayRequest = new JsonArrayRequest(Request.Method.GET, "http://192.168.8.104:8080/api/schedules", null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                Gson gson = new Gson();
                JsonParser parser = new JsonParser();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZZ");
                for(int i=0; i<response.length(); i++){
                    try {
                        JSONObject scheduleObject = response.getJSONObject(i);
                        Schedule schedule = new Schedule();
                        schedule.setId(scheduleObject.getLong("id"));
                        schedule.setDate(sdf.parse(scheduleObject.getString("date")));
                        schedule.setDate(sdf.parse(scheduleObject.getString("date")));
                        schedule.setStartTime(sdf.parse(scheduleObject.getString("startTime")));
                        schedule.setEndTime(sdf.parse(scheduleObject.getString("endTime")));
                        JsonElement classroomJson =  parser.parse(String.valueOf(scheduleObject.getJSONObject("classroom")));
                        JsonElement moduleJson =  parser.parse(String.valueOf(scheduleObject.getJSONObject("module")));
                        JsonElement userJson =  parser.parse(String.valueOf(scheduleObject.getJSONObject("user")));
                        schedule.setClassroom(gson.fromJson(classroomJson, Classroom.class));
                        schedule.setModule(gson.fromJson(moduleJson, Module.class));
                        schedule.setUser(gson.fromJson(userJson, User.class));
                        Set<Batch> batchList = new Gson().fromJson(String.valueOf(scheduleObject.getJSONArray("batches")), new TypeToken<HashSet<Batch>>(){}.getType());
                        schedule.setBatches(batchList);
                        scheduleList.add(schedule);
                        Toast.makeText(BatchActivity.this, String.valueOf(scheduleObject.getJSONArray("batches")), Toast.LENGTH_SHORT).show();
                    } catch (JSONException | ParseException e) {
                        e.printStackTrace();
                    }

                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    adapter = new BatchTimetableAdapter(getApplicationContext(), scheduleList);
                    recyclerView.setAdapter(adapter);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("tag", "onErrorResponse" + error.getMessage());
            }
        });
        queue.add(JSONArrayRequest);
    }
}
