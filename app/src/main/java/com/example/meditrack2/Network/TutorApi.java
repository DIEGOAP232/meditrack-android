package com.example.meditrack2.Network;

import com.example.meditrack2.Entity.PacienteVinculadoResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface TutorApi {

    @GET("api/tutores/mis-pacientes")
    Call<List<PacienteVinculadoResponse>> listarMisPacientes();
}