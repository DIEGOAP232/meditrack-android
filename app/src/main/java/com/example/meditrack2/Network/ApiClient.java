
package com.example.meditrack2.Network;

import android.content.Context;

import com.example.meditrack2.MediTrackApp;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static final String BASE_URL = "http://localhost:8080/";

    private static Retrofit retrofit;

    private ApiClient() {
    }

    public static Retrofit getClient() {

        if (retrofit == null) {

            Context context = MediTrackApp.getAppContext();

            SessionManager sessionManager = new SessionManager(context);

            HttpLoggingInterceptor logging =
                    new HttpLoggingInterceptor();

            logging.setLevel(
                    HttpLoggingInterceptor.Level.BASIC
            );

            OkHttpClient client = new OkHttpClient.Builder()

                    // Interceptor de autenticación
                    .addInterceptor(chain -> {

                        okhttp3.Request original = chain.request();

                        okhttp3.Request.Builder requestBuilder =
                                original.newBuilder();

                        String path = original.url().encodedPath();

                        boolean esPublico =
                                path.endsWith("/api/auth/login")
                                        || path.endsWith("/api/auth/login-personal")
                                        || path.contains("/api/auth/registro")
                                        || path.contains("/api/usuarios/registro");

                        if (!esPublico) {

                            String token = sessionManager.obtenerToken();

                            if (token != null && !token.isBlank()) {

                                requestBuilder.header(
                                        "Authorization",
                                        "Bearer " + token
                                );
                            }
                        }

                        return chain.proceed(requestBuilder.build());
                    })

                    // Registro de solicitudes HTTP
                    .addInterceptor(logging)

                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(
                            GsonConverterFactory.create()
                    )
                    .build();
        }

        return retrofit;
    }
}
