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

public class LecturerUserAddEditActivity extends AppCompatActivity {
    private Button submitButton;
    private EditText inputUserName, inputUserEmail, inputUserPassword;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_add_edit);
        String selection = getIntent().getStringExtra("SELECTION");
        String userIDEdit = getIntent().getStringExtra("USER_ID");
        String userEmailEdit = getIntent().getStringExtra("USER_EMAIL");
        String userNameEdit = getIntent().getStringExtra("USER_NAME");
        String userPasswordEdit = getIntent().getStringExtra("USER_PASSWORD");
        String userRolesEdit = getIntent().getStringExtra("USER_ROLES");


        RequestQueue queue = Volley.newRequestQueue(this);

        submitButton = (Button) findViewById(R.id.submitButton);
        inputUserName = (EditText) findViewById(R.id.name);
        inputUserEmail = (EditText) findViewById(R.id.email);
        inputUserPassword = (EditText) findViewById(R.id.password);

        if (selection.equals("Delete")) {
            JSONObject obj = new JSONObject();
            try {
                obj.put("id", Long.parseLong(userIDEdit));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            StringRequest stringRequest = new StringRequest(Request.Method.DELETE, baseURL+"api/auth/users/" + userIDEdit,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Toast.makeText(LecturerUserAddEditActivity.this, "Deleted", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getBaseContext(), LecturerUserListActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(LecturerUserAddEditActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getBaseContext(), LecturerUserListActivity.class);
                            startActivity(intent);
                            finish();
                            Log.d(TAG, error.toString());
                        }
                    });

            queue.add(stringRequest);
        }

        if (selection.equals("Edit")) {
            inputUserName.setText(userNameEdit);
            inputUserEmail.setText(userEmailEdit);
            inputUserPassword.setText(userPasswordEdit);
            submitButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String userName = inputUserName.getText().toString();
                    String userEmail = inputUserEmail.getText().toString();
                    String userPassword = inputUserPassword.getText().toString();

                    JSONObject obj = new JSONObject();
                    if(userPassword == null) {
                        try {
                            obj.put("userName", userName);
                            obj.put("userEmail", userEmail);
//                        obj.put("userPassword", userPassword);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    else{
                        try {
                            obj.put("userName", userName);
                            obj.put("userEmail", userEmail);
                            obj.put("userPassword", userPassword);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                            (Request.Method.PUT, baseURL+ "api/auth/users/"+ userIDEdit, obj, new Response.Listener<JSONObject>() {

                                @Override
                                public void onResponse(JSONObject response) {
                                    Intent intent = new Intent(getBaseContext(), LecturerUserListActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }, new Response.ErrorListener() {

                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    // TODO: Handle error
                                    Toast.makeText(LecturerUserAddEditActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
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
                    String userName = inputUserName.getText().toString();
                    String userEmail = inputUserEmail.getText().toString();
                    String userPassword = inputUserPassword.getText().toString();

                    JSONObject obj = new JSONObject();
                    try {
                        obj.put("userName", userName);
                        obj.put("userEmail", userEmail);
                        obj.put("userPassword", userPassword);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                            (Request.Method.POST, baseURL+ "api/auth/users", obj, new Response.Listener<JSONObject>() {

                                @Override
                                public void onResponse(JSONObject response) {
                                    Intent intent = new Intent(getBaseContext(), LecturerUserListActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }, new Response.ErrorListener() {

                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    // TODO: Handle error
                                    Toast.makeText(LecturerUserAddEditActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                                }
                            });
                    queue.add(jsonObjectRequest);
                }
            });
        }
    }
}
