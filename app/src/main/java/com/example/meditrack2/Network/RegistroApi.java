package com.example.meditrack2.Network;

import com.example.meditrack2.Entity.RegistroPacienteRequest;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RegistroApi {

    @POST("api/auth/registro-paciente")
    Call<ResponseBody> registrarPaciente(
            @Body RegistroPacienteRequest request);
}