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

import org.json.JSONException;
import org.json.JSONObject;

import static android.content.ContentValues.TAG;
import static com.example.timetableio.api.API_BASE_URL.baseURL;

public class ModuleAddEditActivity extends AppCompatActivity {
    private Button submitButton;
    private EditText inputModuleName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_module_add_edit);
        String selection = getIntent().getStringExtra("SELECTION");
        String moduleIDEdit = getIntent().getStringExtra("MODULE_ID");
        String moduleNameEdit = getIntent().getStringExtra("MODULE_NAME");

        RequestQueue queue = Volley.newRequestQueue(this);

        submitButton = (Button) findViewById(R.id.submitButton);
        inputModuleName = (EditText) findViewById(R.id.batchCode);

        if (selection.equals("Delete")) {
            JSONObject obj = new JSONObject();
            try {
                obj.put("id", Long.parseLong(moduleIDEdit));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            StringRequest stringRequest = new StringRequest(Request.Method.DELETE, baseURL+"api/modules/" + moduleIDEdit,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Toast.makeText(ModuleAddEditActivity.this, "Deleted", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getBaseContext(), ModuleListActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(ModuleAddEditActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getBaseContext(), ModuleListActivity.class);
                            startActivity(intent);
                            finish();
                            Log.d(TAG, error.toString());
                        }
                    });

            queue.add(stringRequest);
        }

        if (selection.equals("Edit")) {
            inputModuleName.setText(moduleNameEdit);
            submitButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String moduleName = inputModuleName.getText().toString();

                    JSONObject obj = new JSONObject();
                    try {
                        obj.put("moduleName", moduleName);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                            (Request.Method.PUT, baseURL+"api/modules/"+ moduleIDEdit, obj, new Response.Listener<JSONObject>() {

                                @Override
                                public void onResponse(JSONObject response) {
                                    Intent intent = new Intent(getBaseContext(), ModuleListActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }, new Response.ErrorListener() {

                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    // TODO: Handle error
                                    Toast.makeText(ModuleAddEditActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
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
                    String moduleName = inputModuleName.getText().toString();

                    JSONObject obj = new JSONObject();
                    try {
                        obj.put("moduleName", moduleName);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                            (Request.Method.POST, baseURL+ "api/modules", obj, new Response.Listener<JSONObject>() {

                                @Override
                                public void onResponse(JSONObject response) {
                                    Intent intent = new Intent(getBaseContext(), ModuleListActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }, new Response.ErrorListener() {

                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    // TODO: Handle error
                                    Toast.makeText(ModuleAddEditActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                                }
                            });
                    queue.add(jsonObjectRequest);
                }
            });
        }
    }
}
