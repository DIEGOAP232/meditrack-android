
package com.example.meditrack2.Network;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApiConnectionTest {

    public interface ConnectionCallback {
        void onSuccess(String mensaje);
        void onError(String error);
    }

    public static void comprobarConexion(ConnectionCallback callback) {

        retrofit2.Retrofit retrofit = ApiClient.getClient();

        TestApi api = retrofit.create(TestApi.class);

        api.comprobar().enqueue(new Callback<String>() {

            @Override
            public void onResponse(
                    Call<String> call,
                    Response<String> response) {

                if (response.isSuccessful()) {
                    callback.onSuccess(
                            response.body() != null
                                    ? response.body()
                                    : "Backend disponible"
                    );
                } else {
                    callback.onError(
                            "El servidor respondió: HTTP "
                                    + response.code()
                    );
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                callback.onError(
                        "No se pudo conectar: " + t.getMessage()
                );
            }
        });
    }
}
