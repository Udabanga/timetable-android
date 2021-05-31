package com.example.timetableio.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
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
import com.example.timetableio.adapter.TimetableAdapter;
import com.example.timetableio.model.Batch;
import com.example.timetableio.model.Classroom;
import com.example.timetableio.model.Module;
import com.example.timetableio.model.Schedule;
import com.example.timetableio.model.Search;
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
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;

import static com.example.timetableio.api.API_BASE_URL.baseURL;

public class ViewClassroomTimetableActivity extends AppCompatActivity {
    private List<Schedule> scheduleList;
    private ArrayList<Classroom> classroomList;
    private RecyclerView recyclerView;
    private TimetableAdapter adapter;
    private Button datePickerButton, searchButton;
    private TextView selectDates;
    private Spinner classroomSpinner;
    private Long longStartDate, longEndDate;
    private Classroom selectedClassroom;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_classroom_schedule);
        recyclerView = findViewById(R.id.recyclerView);
        datePickerButton = findViewById(R.id.selectDateRangeButton);
        selectDates = findViewById(R.id.selectedDates);
        classroomSpinner= findViewById(R.id.spinner);
        searchButton = findViewById(R.id.searchButton);

        requestClassroomDropdown();
        requestSchedules();

        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("IST"));
        calendar.clear();

        //Builder
        MaterialDatePicker.Builder<Pair<Long, Long>> builder = MaterialDatePicker.Builder.dateRangePicker();
        builder.setTheme(R.style.Theme_MaterialComponents);
        builder.setTitleText("Select Date Range");
        final MaterialDatePicker materialDatePicker = builder.build();


        datePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                materialDatePicker.show(getSupportFragmentManager(), "DATE_RANGE_PICKER");
            }
        });

        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Pair<Long, Long>>() {
            @Override
            public void onPositiveButtonClick(Pair<Long,Long> selection) {
                selectDates.setText("Selected Date: "+ materialDatePicker.getHeaderText());

                longStartDate = selection.first;
                longEndDate = selection.second;
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date startDate = new Date(longStartDate);
                Date endDate = new Date(longEndDate);
                SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");

                Search search= new Search();

                search.setClassroom(selectedClassroom);

                try {
                    search.setStartDate(new SimpleDateFormat( "yyyy-MM-dd" ).parse( df2.format(startDate) ));
                    search.setEndDate(new SimpleDateFormat( "yyyy-MM-dd" ).parse( df2.format(endDate) ));
                } catch (ParseException e) {
                    e.printStackTrace();
                }


                try {
                    requestSchedulesFiltered(search);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void requestSchedulesFiltered(Search search) throws JSONException {
        RequestQueue queue = Volley.newRequestQueue(this);

        JSONObject obj = new JSONObject();
        try {
            JSONObject classroom = new JSONObject();
            classroom.put("id", selectedClassroom.getId());
            obj.put("classroom", classroom);
            obj.put("startDate", search.getStartDate());
            obj.put("endDate", search.getEndDate());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JSONArray jsonArray = new JSONArray();
        jsonArray.put(obj);



        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                (Request.Method.POST, baseURL+"api/schedules/classroom", jsonArray, new Response.Listener<JSONArray >() {

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
                            } catch (JSONException | ParseException e) {
                                e.printStackTrace();
                            }

                            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                            adapter = new TimetableAdapter(getApplicationContext(), scheduleList);
                            recyclerView.setAdapter(adapter);
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Log.d("tag", "onErrorResponse" + error.getMessage());
                        Toast.makeText(ViewClassroomTimetableActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        queue.add(jsonArrayRequest);


    }
    private void requestClassroomDropdown() {
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonArrayRequest JSONArrayRequest = new JsonArrayRequest(Request.Method.GET, baseURL+"api/classrooms", null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                classroomList = new ArrayList<>();
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

                    ArrayAdapter<Classroom> classroomSpinnerAdapter =  new ArrayAdapter<Classroom>(ViewClassroomTimetableActivity.this, android.R.layout.simple_spinner_item, classroomList);
                    classroomSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    classroomSpinner.setAdapter(classroomSpinnerAdapter);

                    classroomSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            selectedClassroom = (Classroom) classroomSpinner.getSelectedItem();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
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

    private void requestSchedules(){
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonArrayRequest JSONArrayRequest = new JsonArrayRequest(Request.Method.GET, baseURL+ "api/schedules", null, new Response.Listener<JSONArray>() {

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
                    } catch (JSONException | ParseException e) {
                        e.printStackTrace();
                    }

                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    adapter = new TimetableAdapter(getApplicationContext(), scheduleList);
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
