package com.example.timetableio.api;

import com.example.timetableio.payload.request.LoginRequest;
import com.example.timetableio.payload.response.MessageResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface API {
    @POST("api/user/login")
    Call<MessageResponse> userLogin(@Body LoginRequest login);
}
