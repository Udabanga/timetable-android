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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.example.timetableio.api.API_BASE_URL.baseURL;

public class LoginActivity extends AppCompatActivity {
    private Button cancelButton, loginButton;
    private EditText inputLoginEmail, inputLoginPassword;

    public static final String SHARED_PREFERENCES = "shared_preferences" ;
    public static final String EMAIL_KEY = "email";
    public static final String ID_KEY = "id";
    public SharedPreferences sharedPreferences;
    public String prefEmail, prefID;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        cancelButton = (Button) findViewById(R.id.cancelButton);
        loginButton = (Button) findViewById(R.id.loginButton);
        inputLoginEmail = (EditText) findViewById(R.id.loginEmail);
        inputLoginPassword = (EditText) findViewById(R.id.loginPassword);

        sharedPreferences = getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE);
        prefEmail = sharedPreferences.getString(EMAIL_KEY, null);
        prefID = sharedPreferences.getString(ID_KEY, null);

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
                        (Request.Method.POST, baseURL+ "api/auth/signin", obj, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    String message = response.getString("message");
                                    if(message == "null"){
                                        String username = response.getString("username");
                                        String email = response.getString("email");
                                        Long id = response.getLong("id");

                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putString(EMAIL_KEY, email);
                                        editor.putString(ID_KEY, Long.toString(id));
                                        editor.apply();

                                        JSONArray roles = response.getJSONArray("roles");
                                        if(roles.toString().contains("ROLE_ADMIN")){
                                            //Redirect Admin
                                            Intent intent = new Intent(getBaseContext(), AdminHomeActivity.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                                            startActivity(intent);
                                            finish();
                                        }
                                        else if(roles.toString().contains("ROLE_LECTURER")){
                                            //Redirect Lecturer
                                            Intent intent = new Intent(getBaseContext(), LecturerHomeActivity.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);;
                                            startActivity(intent);
                                            finish();
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
                            }
                        }, new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // TODO: Handle error
                                Toast.makeText(LoginActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                            }
                        });
                queue.add(jsonObjectRequest);

            }
        });

    }
}
