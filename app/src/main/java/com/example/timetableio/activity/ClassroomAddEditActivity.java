package com.example.timetableio.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.timetableio.R;
import com.example.timetableio.model.Classroom;
import com.example.timetableio.model.Schedule;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static android.content.ContentValues.TAG;
import static com.example.timetableio.api.API_BASE_URL.baseURL;

public class ClassroomAddEditActivity extends AppCompatActivity {
    private Button submitButton;
    private EditText inputClassroomBuilding, inputClassroomFloor, inputClassroomRoomNumber;
    private Spinner inputClassroomType;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classroom_add_edit);
        String selection = getIntent().getStringExtra("SELECTION");
        String classroomIDEdit = getIntent().getStringExtra("CLASSROOM_ID");
        String classroomBulidingEdit = getIntent().getStringExtra("CLASSROOM_BUILDING");
        String classroomFloorEdit = getIntent().getStringExtra("CLASSROOM_FLOOR");
        String classroomRoomNumberEdit = getIntent().getStringExtra("CLASSROOM_ROOM_NUMBER");
        String classroomTypeEdit = getIntent().getStringExtra("CLASSROOM_TYPE");


        RequestQueue queue = Volley.newRequestQueue(this);

        submitButton = (Button) findViewById(R.id.classroomSubmitButton);
        inputClassroomBuilding = (EditText) findViewById(R.id.building);
        inputClassroomFloor = (EditText) findViewById(R.id.floor);
        inputClassroomRoomNumber = (EditText) findViewById(R.id.roomNumber);
        inputClassroomType = (Spinner) findViewById(R.id.type);

        if(selection.equals("Delete")){
            JSONObject obj = new JSONObject();
            try {
                obj.put("id", classroomIDEdit);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            StringRequest stringRequest = new StringRequest(Request.Method.DELETE, baseURL+"api/classrooms/" + classroomIDEdit,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Toast.makeText(ClassroomAddEditActivity.this, "Deleted", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getBaseContext(), ClassroomListActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(ClassroomAddEditActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getBaseContext(), ClassroomListActivity.class);
                            startActivity(intent);
                            finish();
                            Log.d(TAG, error.toString());
                        }
                    });
            queue.add(stringRequest);
        }

        if (selection.equals("Edit")) {
            inputClassroomBuilding.setText(classroomBulidingEdit);
            inputClassroomFloor.setText(classroomFloorEdit);
            inputClassroomRoomNumber.setText(classroomRoomNumberEdit);

            submitButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String classroomBuilding = inputClassroomBuilding.getText().toString();
                    String classroomFloor = inputClassroomFloor.getText().toString();
                    String classroomRoomNumber = inputClassroomRoomNumber.getText().toString();
                    String classroomType = inputClassroomType.getSelectedItem().toString();

                    JSONObject obj = new JSONObject();
                    try {
                        if (selection.equals("Edit")) {
                            obj.put("id", classroomIDEdit);
                        }
                        obj.put("building", classroomBuilding);
                        obj.put("floor", classroomFloor);
                        obj.put("roomNumber", classroomRoomNumber);
                        obj.put("type", classroomType);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                            (Request.Method.PUT, baseURL+ "api/classrooms/"+ classroomIDEdit, obj, new Response.Listener<JSONObject>() {

                                @Override
                                public void onResponse(JSONObject response) {

                                    Intent intent = new Intent(getBaseContext(), ClassroomListActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }, new Response.ErrorListener() {

                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    // TODO: Handle error
                                    Toast.makeText(ClassroomAddEditActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                                }
                            });
                    queue.add(jsonObjectRequest);
                }
            });
        }
        else {
            submitButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String classroomBuilding = inputClassroomBuilding.getText().toString();
                    String classroomFloor = inputClassroomFloor.getText().toString();
                    String classroomRoomNumber = inputClassroomRoomNumber.getText().toString();
                    String classroomType = inputClassroomType.getSelectedItem().toString();

                    JSONObject obj = new JSONObject();
                    try {
                        if (selection.equals("Edit")) {
                            obj.put("id", classroomIDEdit);
                        }
                        obj.put("building", classroomBuilding);
                        obj.put("floor", classroomFloor);
                        obj.put("roomNumber", classroomRoomNumber);
                        obj.put("type", classroomType);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                            (Request.Method.POST, baseURL+ "api/classrooms", obj, new Response.Listener<JSONObject>() {

                                @Override
                                public void onResponse(JSONObject response) {

                                    Intent intent = new Intent(getBaseContext(), ClassroomListActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }, new Response.ErrorListener() {

                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    // TODO: Handle error
                                    Toast.makeText(ClassroomAddEditActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                                }
                            });
                    queue.add(jsonObjectRequest);
                }
            });
        }
    }
}
