package com.example.timetableio.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.example.timetableio.api.API_BASE_URL;

import org.json.JSONException;
import org.json.JSONObject;


import static android.content.ContentValues.TAG;
import static com.example.timetableio.api.API_BASE_URL.baseURL;

public class BatchAddEditActivity extends AppCompatActivity {
    private Button submitButton;
    private EditText inputBatchCode, inputFaculty, inputSemester, inputYear;
    private API_BASE_URL apiBaseUrl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_batch_add_edit);
        String selection = getIntent().getStringExtra("SELECTION");
        String batchIDEdit = getIntent().getStringExtra("BATCH_ID");
        String batchBatchCodeEdit = getIntent().getStringExtra("BATCH_BATCH_CODE");
        String batchFacultyEdit = getIntent().getStringExtra("BATCH_FACULTY");
        String batchSemesterEdit = getIntent().getStringExtra("BATCH_SEMESTER");
        String batchYearEdit = getIntent().getStringExtra("BATCH_YEAR");

        RequestQueue queue = Volley.newRequestQueue(this);

        submitButton = (Button) findViewById(R.id.submitButton);
        inputBatchCode = (EditText) findViewById(R.id.batchCode);
        inputFaculty = (EditText) findViewById(R.id.faculty);
        inputSemester = (EditText) findViewById(R.id.semester);
        inputYear = (EditText) findViewById(R.id.year);

        if (selection.equals("Delete")) {
            JSONObject obj = new JSONObject();
            try {
                obj.put("id", Long.parseLong(batchIDEdit));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            StringRequest stringRequest = new StringRequest(Request.Method.DELETE, baseURL+"api/batches/" + batchIDEdit,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Toast.makeText(BatchAddEditActivity.this, "Deleted", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getBaseContext(), BatchListActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(BatchAddEditActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getBaseContext(), BatchListActivity.class);
                            startActivity(intent);
                            finish();
                            Log.d(TAG, error.toString());
                        }
                    });

            queue.add(stringRequest);
        }

        if (selection.equals("Edit")) {
            inputBatchCode.setText(batchBatchCodeEdit);
            inputFaculty.setText(batchFacultyEdit);
            inputSemester.setText(batchSemesterEdit);
            inputYear.setText(batchYearEdit);

            submitButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String batchCode = inputBatchCode.getText().toString();
                    String faculty = inputFaculty.getText().toString();
                    String semester = inputSemester.getText().toString();
                    String year = inputYear.getText().toString();

                    JSONObject obj = new JSONObject();
                    try {
                        obj.put("batchCode", batchCode);
                        obj.put("faculty", faculty);
                        obj.put("semester", semester);
                        obj.put("year", year);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                            (Request.Method.PUT, baseURL+"api/batches/"+ batchIDEdit, obj, new Response.Listener<JSONObject>() {

                                @Override
                                public void onResponse(JSONObject response) {
                                    Intent intent = new Intent(getBaseContext(), BatchListActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }, new Response.ErrorListener() {

                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    // TODO: Handle error
                                    Toast.makeText(BatchAddEditActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                                }
                            });
                    queue.add(jsonObjectRequest);
                }
            });
        }
        else {
            inputBatchCode.setText(batchBatchCodeEdit);
            inputFaculty.setText(batchFacultyEdit);
            inputSemester.setText(batchSemesterEdit);
            inputYear.setText(batchYearEdit);

            submitButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String batchCode = inputBatchCode.getText().toString();
                    String faculty = inputFaculty.getText().toString();
                    String semester = inputSemester.getText().toString();
                    String year = inputYear.getText().toString();

                    JSONObject obj = new JSONObject();
                    try {
                        obj.put("batchCode", batchCode);
                        obj.put("faculty", faculty);
                        obj.put("semester", semester);
                        obj.put("year", year);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                            (Request.Method.POST, baseURL+"api/batches", obj, new Response.Listener<JSONObject>() {

                                @Override
                                public void onResponse(JSONObject response) {
                                    Intent intent = new Intent(getBaseContext(), BatchListActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }, new Response.ErrorListener() {

                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    // TODO: Handle error
                                    Toast.makeText(BatchAddEditActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                                }
                            });
                    queue.add(jsonObjectRequest);
                }
            });
        }
    }
}
