package com.example.meditrack2.Network;

import com.example.meditrack2.Entity.CitaRequest;
import com.example.meditrack2.Entity.CitaResponse;
import com.example.meditrack2.Entity.EspecialidadResponse;
import com.example.meditrack2.Entity.MedicoResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface CitaApi {

    @GET("api/citas")
    Call<List<CitaResponse>> listarMisCitas();

    @GET("api/citas/especialidades")
    Call<List<EspecialidadResponse>> listarEspecialidades();

    @GET("api/citas/medicos/{idEspecialidad}")
    Call<List<MedicoResponse>> listarMedicos(
            @Path("idEspecialidad") int idEspecialidad);

    @POST("api/citas")
    Call<CitaResponse> registrarCita(@Body CitaRequest request);
}