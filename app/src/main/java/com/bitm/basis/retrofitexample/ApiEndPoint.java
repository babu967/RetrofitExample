package com.bitm.basis.retrofitexample;


import com.bitm.basis.retrofitexample.api_response.UserResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiEndPoint {

    @GET("/")
    Call<UserResponse> getUser();
}
