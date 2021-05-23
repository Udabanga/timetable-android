package com.example.timetableio.activity;

import android.content.Intent;
import android.os.Bundle;
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
import com.android.volley.toolbox.Volley;
import com.example.timetableio.R;
import com.example.timetableio.model.Module;
import com.example.timetableio.model.Schedule;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ModuleAddEditActivity extends AppCompatActivity {
    private Button submitButton;
    private EditText inputModuleName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_module_add_edit);
        String selection = getIntent().getStringExtra("SELECTION");
        String moduleID = getIntent().getStringExtra("MODULE_ID");
        String moduleName = getIntent().getStringExtra("MODULE_NAME");

        RequestQueue queue = Volley.newRequestQueue(this);

        submitButton = (Button) findViewById(R.id.moduleSubmitButton);
        inputModuleName = (EditText) findViewById(R.id.moduleName);

        if (selection.equals("Edit")) {
            Gson gson = new Gson();
            Module module;
//                JSONObject moduleObj = new JSONObject(editModule);
            inputModuleName.setText("BRUH");


        }

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
                        (Request.Method.POST, "http://192.168.8.104:8080/api/modules", obj, new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                Toast.makeText(ModuleAddEditActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
//                                try {
//                                    String message = response.getString("message");
//                                    if(message == "null"){
//                                        String username = response.getString("username");
//                                        String email = response.getString("email");
//                                        Long id = response.getLong("id");
//                                        JSONArray roles = response.getJSONArray("roles");
//                                        if(roles.toString().contains("ROLE_ADMIN")){
//                                            //Redirect Admin
//                                            Intent intent = new Intent(getBaseContext(), AdminHomeActivity.class);
//                                            startActivity(intent);
//                                        }
//                                        else if(roles.toString().contains("ROLE_MODERATOR")){
//                                            //Redirect Lecturer
//                                            Intent intent = new Intent(getBaseContext(), LecturerHomeActivity.class);
//                                            startActivity(intent);
//                                        }
//                                        else {
//                                            Toast.makeText(ModuleAddEditActivity.this, "Error: Role not identified", Toast.LENGTH_SHORT).show();
//                                        }
//                                    }
//                                    else{
//                                        Toast.makeText(ModuleAddEditActivity.this, message, Toast.LENGTH_SHORT).show();
//                                    }
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                }
//                                Toast.makeText(ModuleAddEditActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
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
