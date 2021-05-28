package com.example.timetableio.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.timetableio.R;
import com.example.timetableio.adapter.ScheduleAdapter;
import com.example.timetableio.model.Batch;
import com.example.timetableio.model.Classroom;
import com.example.timetableio.model.Module;
import com.example.timetableio.model.Schedule;
import com.example.timetableio.model.Search;
import com.example.timetableio.model.User;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.example.timetableio.api.API_BASE_URL.baseURL;

public class ScheduleListActivity extends AppCompatActivity {
    private Button addButton, selectTimeRangeButton;
    private RecyclerView recyclerView;
    private List<Schedule> scheduleList;
    private ScheduleAdapter adapter;
    private User searchUser;
    public SharedPreferences sharedPreferences;
    public String prefID;
    public static final String SHARED_PREFERENCES = "shared_preferences";
    public static final String ID_KEY = "id";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_list);
        recyclerView = findViewById(R.id.recyclerView);
        scheduleList = new ArrayList<>();
        addButton = (Button) findViewById(R.id.createButton);
        selectTimeRangeButton = (Button) findViewById(R.id.selectTimeRangeButton);

        sharedPreferences = getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE);
        prefID = sharedPreferences.getString(ID_KEY, null);
        
        selectTimeRangeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), ScheduleAddEditActivity.class);
                intent.putExtra("SELECTION", "Add");
                startActivity(intent);
                finish();
            }
        });
//        requestUserDetails();
        
        displayLecturerSchedule();


    }

    private void displayLecturerSchedule() {
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, baseURL + "api/auth/users/" + prefID, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Gson g = new Gson();
                        searchUser = g.fromJson(response.toString(), User.class);

                        Search search = new Search();

                        search.setUser(searchUser);

                        RequestQueue queueFiltered = Volley.newRequestQueue(getApplicationContext());
                        JsonArrayRequest JSONArrayRequest = new JsonArrayRequest(Request.Method.GET, baseURL+ "api/schedules/view/lecturer/"+search.getUser().getId(), null, new Response.Listener<JSONArray>() {

                            @Override
                            public void onResponse(JSONArray response) {
                                Gson gson = new Gson();
                                JsonParser parser = new JsonParser();
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZZ");

                                scheduleList = new ArrayList<>();
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
                                        Toast.makeText(ScheduleListActivity.this, String.valueOf(scheduleObject.getJSONArray("batches")), Toast.LENGTH_SHORT).show();
                                    } catch (JSONException | ParseException e) {
                                        e.printStackTrace();
                                    }

                                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                                    adapter = new ScheduleAdapter(getApplicationContext(), scheduleList);
                                    recyclerView.setAdapter(adapter);
                                }

                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d("tag", "onErrorResponse" + error.getMessage());
                            }
                        });
                        queueFiltered.add(JSONArrayRequest);


                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Toast.makeText(ScheduleListActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
        queue.add(jsonObjectRequest);
    }

    private void requestUserDetails() {
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, baseURL + "api/auth/users/" + prefID, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Gson g = new Gson();
                        searchUser = g.fromJson(response.toString(), User.class);

                        Search search = new Search();

                        search.setUser(searchUser);
                        try {
                            search.setStartDate(new SimpleDateFormat( "yyyy-MM-dd" ).parse( "2021-05-20" ));
                            search.setEndDate(new SimpleDateFormat( "yyyy-MM-dd" ).parse( "2021-05-28" ));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        JSONObject obj = new JSONObject();
                        try {
                            JSONObject user = new JSONObject();
                            user.put("id", searchUser.getId());
                            obj.put("user", user);
                            obj.put("startDate", search.getStartDate());
                            obj.put("endDate", search.getEndDate());

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        Toast.makeText(ScheduleListActivity.this, obj.toString(), Toast.LENGTH_SHORT).show();

                        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                                (Request.Method.POST, baseURL+ "api/schedules/lecturer", obj, new Response.Listener<JSONObject>() {

                                    @Override
                                    public void onResponse(JSONObject response) {
                                        Toast.makeText(ScheduleListActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
                                    }
                                }, new Response.ErrorListener() {

                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        // TODO: Handle error
                                        Toast.makeText(ScheduleListActivity.this, "Error", Toast.LENGTH_SHORT).show();
                                    }
                                });
                        queue.add(jsonObjectRequest);

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Toast.makeText(ScheduleListActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
        queue.add(jsonObjectRequest);
    }
}

