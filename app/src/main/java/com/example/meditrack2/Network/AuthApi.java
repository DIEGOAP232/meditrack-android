
package com.example.meditrack2.Network;

import com.example.meditrack2.Entity.LoginRequest;
import com.example.meditrack2.Entity.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthApi {

    @POST("api/auth/login")
    Call<LoginResponse> login(
            @Body LoginRequest request
    );

    @POST("api/auth/login-personal")
    Call<LoginResponse> loginPersonal(
            @Body LoginRequest request
    );
}

