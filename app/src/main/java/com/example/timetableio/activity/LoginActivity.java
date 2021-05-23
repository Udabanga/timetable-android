package com.example.timetableio.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.example.timetableio.payload.request.LoginRequest;
import com.example.timetableio.api.API_BASE_URL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
    private Button cancelButton, loginButton;
    private EditText inputLoginEmail, inputLoginPassword;
    public SharedPreferences sharedPreferences;

    API_BASE_URL base = new API_BASE_URL();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        cancelButton = (Button) findViewById(R.id.cancelButton);
        loginButton = (Button) findViewById(R.id.loginButton);
        inputLoginEmail = (EditText) findViewById(R.id.loginEmail);
        inputLoginPassword = (EditText) findViewById(R.id.loginPassword);

        sharedPreferences = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        SharedPreferences checkPreferences=getSharedPreferences("checkbox",MODE_PRIVATE);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        final String url = "192.168.8.104:8080";
        final RequestQueue queue = Volley.newRequestQueue(this);

        loginButton.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                String email = inputLoginEmail.getText().toString();
                String password = inputLoginPassword.getText().toString();

                LoginRequest user = new LoginRequest();
                user.setEmail(email);
                user.setPassword(password);

                JSONObject obj = new JSONObject();
                try {
                    obj.put("email", email);
                    obj.put("password", password);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                        (Request.Method.POST, "http://192.168.8.104:8080/api/auth/signin", obj, new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    String message = response.getString("message");
                                    if(message == "null"){
                                        String username = response.getString("username");
                                        String email = response.getString("email");
                                        Long id = response.getLong("id");
                                        JSONArray roles = response.getJSONArray("roles");
                                        if(roles.toString().contains("ROLE_ADMIN")){
                                            //Redirect Admin
                                            Intent intent = new Intent(getBaseContext(), AdminHomeActivity.class);
                                            startActivity(intent);
                                        }
                                        else if(roles.toString().contains("ROLE_MODERATOR")){
                                            //Redirect Lecturer
                                            Intent intent = new Intent(getBaseContext(), LecturerHomeActivity.class);
                                            startActivity(intent);
                                        }
                                        else {
                                            Toast.makeText(LoginActivity.this, "Error: Role not identified", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                    else{
                                        Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                Toast.makeText(LoginActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
                            }
                        }, new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // TODO: Handle error
                                Toast.makeText(LoginActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                            }
                        });
                queue.add(jsonObjectRequest);

//                if(email.equalsIgnoreCase("admin")){
//                    Intent intent = new Intent(getBaseContext(), AdminHomeActivity.class);
//                    startActivity(intent);
//                }
//                else if(email.equalsIgnoreCase("lecturer")){
//                    Intent intent = new Intent(getBaseContext(), LecturerHomeActivity.class);
//                    startActivity(intent);
//                }

            }
        });

    }
}
