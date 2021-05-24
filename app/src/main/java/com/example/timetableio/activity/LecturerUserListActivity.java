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
import com.example.timetableio.adapter.UserAdapter;
import com.example.timetableio.model.Role;
import com.example.timetableio.model.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LecturerUserListActivity extends AppCompatActivity {
    private Button addButton;
    private RecyclerView recyclerView;
    private List<User> userList;
    private UserAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecturer_list);
        recyclerView = findViewById(R.id.recyclerView);
        userList = new ArrayList<>();
        addButton = (Button) findViewById(R.id.createButton);

        requestUsers();


    }

    private void requestUsers() {
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonArrayRequest JSONArrayRequest = new JsonArrayRequest(Request.Method.GET, "http://192.168.8.104:8080/api/auth/users", null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
//                Gson gson = new Gson();
//                JsonParser parser = new JsonParser();
                for(int i=0; i<response.length(); i++){
                    try {
                        JSONObject userObject = response.getJSONObject(i);

                        User user = new User();
                        user.setId(userObject.getInt("id"));
                        user.setEmail(userObject.getString("email"));
                        user.setName(userObject.getString("name"));
                        Set<Role> roles = new Gson().fromJson(String.valueOf(userObject.getJSONArray("roles")), new TypeToken<HashSet<Role>>(){}.getType());
                        user.setRoles(roles);
                        userList.add(user);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    adapter = new UserAdapter(getApplicationContext(), userList);
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
