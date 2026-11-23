
package com.example.meditrack2.Network;

import retrofit2.Call;
import retrofit2.http.GET;

public interface TestApi {

    @GET("db-test")
    Call<String> comprobar();
}
