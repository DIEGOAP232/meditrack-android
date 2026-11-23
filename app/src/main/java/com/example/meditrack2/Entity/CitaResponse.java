package com.example.meditrack2.Entity;

public class CitaResponse {

    private Integer idCita;
    private Integer idPaciente;
    private Integer idTutor;
    private String tipoCita;
    private Integer idMedico;
    private Integer idEspecialidad;
    private Integer idServicio;
    private String fecha;
    private String hora;
    private String motivo;
    private String estado;
    private String fechaCreacion;

    public Integer getIdCita() { return idCita; }
    public Integer getIdPaciente() { return idPaciente; }
    public Integer getIdTutor() { return idTutor; }
    public String getTipoCita() { return tipoCita; }
    public Integer getIdMedico() { return idMedico; }
    public Integer getIdEspecialidad() { return idEspecialidad; }
    public Integer getIdServicio() { return idServicio; }
    public String getFecha() { return fecha; }
    public String getHora() { return hora; }
    public String getMotivo() { return motivo; }
    public String getEstado() { return estado; }
    public String getFechaCreacion() { return fechaCreacion; }
}