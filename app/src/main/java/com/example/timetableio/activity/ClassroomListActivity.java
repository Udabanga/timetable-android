package com.example.timetableio.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.timetableio.R;
import com.example.timetableio.adapter.ClassroomAdapter;
import com.example.timetableio.model.Classroom;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ClassroomListActivity extends AppCompatActivity {
    private Button addButton;
    private RecyclerView recyclerView;
    private List<Classroom> classroomList;
    private ClassroomAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classroom_list);
        recyclerView = findViewById(R.id.batchRecyclerView);
        classroomList = new ArrayList<>();
        addButton = (Button) findViewById(R.id.datePickerButton);

        requestClassrooms();


    }

    private void requestClassrooms() {
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonArrayRequest JSONArrayRequest = new JsonArrayRequest(Request.Method.GET, "http://192.168.8.104:8080/api/classrooms", null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                for(int i=0; i<response.length(); i++){
                    try {
                        JSONObject classroomObject = response.getJSONObject(i);
                        
                        Classroom classroom = new Classroom();
                        classroom.setId(classroomObject.getLong("id"));
                        classroom.setBuilding(classroomObject.getString("building"));
                        classroom.setFloor(classroomObject.getInt("floor"));
                        classroom.setRoomNumber(classroomObject.getInt("roomNumber"));
                        classroom.setType(classroomObject.getString("type"));
                        classroomList.add(classroom);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    adapter = new ClassroomAdapter(getApplicationContext(), classroomList);
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
